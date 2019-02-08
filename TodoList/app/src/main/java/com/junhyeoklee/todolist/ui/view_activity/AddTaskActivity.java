package com.junhyeoklee.todolist.ui.view_activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.todolist.R;
import com.junhyeoklee.todolist.AppExecutors;
import com.junhyeoklee.todolist.data.database.AppDatabase;
import com.junhyeoklee.todolist.data.model.TaskEntry;
import com.junhyeoklee.todolist.factory.AddTaskViewModelFactory;
import com.junhyeoklee.todolist.ui.view_model.AddTaskViewModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTaskActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    private static final String TAG = AddTaskActivity.class.getSimpleName();

    @BindView(R.id.editTextTaskDescription)
    TextView mEdiText;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.saveButton)
    Button mButton;
    @BindView(R.id.layout_add_task)
    LinearLayout mAddTaskLayout;

    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;
    private SharedPreferences appData;
    private int mColorSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID,DEFAULT_TASK_ID);
        }

        // SharePreference 배경설정 컬러 가져오기
        appData = getSharedPreferences("colorData", MODE_PRIVATE);
        int color = appData.getInt("colorData",mColorSet);
        if(color != 0) {
            if(color == R.color.white || color == R.color.gray) {
                mButton.setTextColor(getResources().getColor(R.color.black));
            }
            mButton.setBackgroundColor(getResources().getColor(color));
            mAddTaskLayout.setBackgroundColor(getResources().getColor(color));
        }

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_TASK_ID)){
            mButton.setText(R.string.update_button);


            if(mTaskId == DEFAULT_TASK_ID){

                mTaskId = intent.getIntExtra(EXTRA_TASK_ID,DEFAULT_TASK_ID);

                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb,mTaskId);
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this,factory).get(AddTaskViewModel.class);

                viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });

            }

        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID,mTaskId);
        super.onSaveInstanceState(outState);
    }

    private void populateUI(TaskEntry task){
        if(task == null){
            return;
        }
        mEdiText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
    }

    public void onSaveButtonClicked(){
        String description = mEdiText.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();

        final TaskEntry task = new TaskEntry(description,priority,date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mTaskId == DEFAULT_TASK_ID){
                    mDb.taskDao().insertTask(task);
                }
                else{
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();
            }
        });
    }

    public int getPriorityFromViews(){

        int priority = 1;
        int checkedId = mRadioGroup.getCheckedRadioButtonId();
        switch (checkedId){
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;

            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;

            case R.id.radButton3:
                priority = PRIORITY_LOW;
                break;
        }
        return priority;
    }

    public void setPriorityInViews(int priority){
        switch (priority){
            case PRIORITY_HIGH:
                mRadioGroup.check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                mRadioGroup.check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                mRadioGroup.check(R.id.radButton3);
        }
    }

}
