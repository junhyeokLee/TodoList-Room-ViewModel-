package com.junhyeoklee.todolist.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.todolist.R;
import com.junhyeoklee.todolist.data.model.TaskColor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingColorActivity extends AppCompatActivity {
    private ArrayList<TaskColor> mColor;
    LinearLayoutManager mLayoutManager;
    private SettingColorSetAdapter mAdapter;
    private SharedPreferences appData;

    @BindView(R.id.team_color_set_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_set);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initData();
    }

    private void initData(){

        ArrayList<TaskColor> teamColors = new ArrayList();
        teamColors.add(new TaskColor("Main",R.color.colorPrimaryDark));
        teamColors.add(new TaskColor("Red",R.color.colorPrimary));
        teamColors.add(new TaskColor("Blue",R.color.colorAccent));
        teamColors.add(new TaskColor("Black",R.color.design_default_color_primary));
        teamColors.add(new TaskColor("Yellow",R.color.colorAccent));

        //LinearLayout 으로 설정
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Animation Defualt 설정
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        mAdapter = new SettingColorSetAdapter(teamColors);
        mRecyclerView.setAdapter(mAdapter);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
