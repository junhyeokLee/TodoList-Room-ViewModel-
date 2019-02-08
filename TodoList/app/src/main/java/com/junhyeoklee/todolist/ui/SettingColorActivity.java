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
        teamColors.add(new TaskColor("#455A64",R.color.colorPrimaryDark));
        teamColors.add(new TaskColor("#607D8B",R.color.colorPrimary));
        teamColors.add(new TaskColor("#ffffff",R.color.white));
        teamColors.add(new TaskColor("#e3e3e3",R.color.gray));
        teamColors.add(new TaskColor("#000000",R.color.black));
        teamColors.add(new TaskColor("#cb3737",R.color.red));
        teamColors.add(new TaskColor("#ee6f57",R.color.orange));
        teamColors.add(new TaskColor("#df0e62",R.color.dahong4));
        teamColors.add(new TaskColor("#ff6e6e",R.color.dahong));
        teamColors.add(new TaskColor("#ff8c8c",R.color.dahong2));
        teamColors.add(new TaskColor("#f5aaaa",R.color.dahong3));
        teamColors.add(new TaskColor("#ffd3de",R.color.pink));
        teamColors.add(new TaskColor("#f6b8d1",R.color.pink2));
        teamColors.add(new TaskColor("#3d1860",R.color.pastelPuble4));
        teamColors.add(new TaskColor("#643579",R.color.pastelPuble5));
        teamColors.add(new TaskColor("#bb99cd",R.color.pastelPuble6));
        teamColors.add(new TaskColor("#ad9aee",R.color.pastelPuple));
        teamColors.add(new TaskColor("#c1aeee",R.color.pastelPuple2));
        teamColors.add(new TaskColor("#d5c2ee",R.color.pastelPuple3));
        teamColors.add(new TaskColor("#ffa01e",R.color.pastelYellow));
        teamColors.add(new TaskColor("#ffb400",R.color.pastelYellow2));
        teamColors.add(new TaskColor("#ffcd28",R.color.pastelYellow3));
        teamColors.add(new TaskColor("#5e8b6f",R.color.green5));
        teamColors.add(new TaskColor("#436e4f",R.color.green4));
        teamColors.add(new TaskColor("#65c6c4",R.color.green3));
        teamColors.add(new TaskColor("#5dc0a6",R.color.green));
        teamColors.add(new TaskColor("#3f8f8d",R.color.green2));
        teamColors.add(new TaskColor("#113f67",R.color.blue));
        teamColors.add(new TaskColor("#34699a",R.color.blue2));
        teamColors.add(new TaskColor("#408ab4",R.color.blue3));
        teamColors.add(new TaskColor("#233142",R.color.navi));
        teamColors.add(new TaskColor("#1a3263",R.color.navi2));
        teamColors.add(new TaskColor("#6927ff",R.color.navilight));
        teamColors.add(new TaskColor("#837dff",R.color.navilight2));




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
