package com.junhyeoklee.todolist.ui.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.junhyeoklee.todolist.data.database.AppDatabase;
import com.junhyeoklee.todolist.data.model.TaskEntry;

public class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDatabase database,int taskId){
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {return task;}
}
