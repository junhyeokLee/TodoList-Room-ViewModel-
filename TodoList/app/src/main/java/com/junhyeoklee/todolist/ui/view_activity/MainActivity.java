package com.junhyeoklee.todolist.ui.view_activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.todolist.R;
import com.junhyeoklee.todolist.AppExecutors;
import com.junhyeoklee.todolist.data.database.AppDatabase;
import com.junhyeoklee.todolist.data.model.TaskColor;
import com.junhyeoklee.todolist.data.model.TaskEntry;
import com.junhyeoklee.todolist.ui.SettingManager;
import com.junhyeoklee.todolist.ui.SettingPreferenceActivity;
import com.junhyeoklee.todolist.ui.adapter.TaskAdapter;
import com.junhyeoklee.todolist.ui.view_model.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerViewTasks)
    RecyclerView mRecyclerView;
    BottomNavigationView bottomNavigationView;
    private TaskAdapter mAdapter;
    private AppDatabase mDb;
    private SharedPreferences appData;
    private int mColorSet;
    private Toast mToast;
    private AlertDialog mAlertDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        // SharePreference 배경설정 컬러 가져오기
        appData = getSharedPreferences("colorData", MODE_PRIVATE);
        int color = appData.getInt("colorData",mColorSet);
        if(color != 0) {
            mRecyclerView.setBackgroundColor(getResources().getColor(color));
        }

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuitem_refresh:
                        mAlertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("TodoList")
                                .setIcon(R.drawable.ic_info_black_24dp)
                                .setMessage("할 일 목록을 전부 삭제 하시겠습니까?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface alertDialog, int arg1) {
                                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                List<TaskEntry> tasks = mAdapter.getTask();
                                                for(int i = 0 ; i < tasks.size() ; i++) {
                                                    mDb.taskDao().deleteTask(tasks.get(i));
                                                }
                                            }
                                        });
                                     customToast("할 일 목록이 전체 삭제 되었습니다.");
                                    }
                                }).create();

                        mAlertDialog.show();
                        break;
                    case R.id.menuitem_add_circle:
                        Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                        startActivity(addTaskIntent);
                        break;
                    case R.id.menuitem_settings:
                        Intent settingIntent = new Intent(MainActivity.this, SettingPreferenceActivity.class);
                        startActivity(settingIntent);
                        break;
                }
                return true;
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<TaskEntry> tasks = mAdapter.getTask();
                        mDb.taskDao().deleteTask(tasks.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();

    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntry> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setTasks(taskEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

    private void customToast(String message){
        // toast 메세지 custom

        Context context = getApplicationContext();
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toastview_background, (ViewGroup) findViewById(R.id.containers));
        TextView txtView = view.findViewById(R.id.txtview);
        txtView.setText(message);
        mToast.setView(view);
        mToast.show();
    }
}
