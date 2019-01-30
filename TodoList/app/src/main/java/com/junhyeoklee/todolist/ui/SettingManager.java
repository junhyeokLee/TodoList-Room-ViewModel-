package com.junhyeoklee.todolist.ui;

import com.junhyeoklee.todolist.data.model.TaskColor;

public class SettingManager {

    private TaskColor taskColor;

    public TaskColor getTaskColor() {
        return taskColor;
    }

    public void setTaskColor(TaskColor taskColor) {
        this.taskColor = taskColor;
    }

    // -------------------------------------------------------------
    // start singleton

    private static TaskColor gHpGameManager;

    public static TaskColor get() {
        if (null == gHpGameManager) {
            gHpGameManager = new TaskColor();
        }
        return gHpGameManager;
    }

    private SettingManager() {
    }



}
