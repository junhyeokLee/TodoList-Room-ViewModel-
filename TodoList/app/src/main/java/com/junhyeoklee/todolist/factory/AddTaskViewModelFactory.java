package com.junhyeoklee.todolist.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.junhyeoklee.todolist.data.database.AppDatabase;
import com.junhyeoklee.todolist.ui.view_model.AddTaskViewModel;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mTaskId;

    public AddTaskViewModelFactory(AppDatabase database,int TaskId){
        mDb = database;
        mTaskId = TaskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(mDb,mTaskId);
    }
}
