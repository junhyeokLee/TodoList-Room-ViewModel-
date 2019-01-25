package com.junhyeoklee.todolist.ui.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.todolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public View mView;

    @BindView(R.id.taskDescription)
    public TextView taskDescriptionView;
    @BindView(R.id.taskUpdatedAt)
    public TextView updatedAtView;
    @BindView(R.id.priorityTextView)
    public TextView priorityView;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this,itemView);
    }
}
