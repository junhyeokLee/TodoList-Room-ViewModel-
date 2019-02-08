package com.junhyeoklee.todolist.ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.todolist.R;
import com.junhyeoklee.todolist.data.model.TaskEntry;
import com.junhyeoklee.todolist.ui.holder.TaskViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TaskAdapter";
    private static final String DATE_FORMAT = "yyy/MM/dd";

    private Context mContext;
    private List<TaskEntry> mTaskEntries;
    final private ItemClickListener mItemClickListener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT,Locale.getDefault());

    public TaskAdapter(Context context,ItemClickListener listener){
        mContext = context;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_task,viewGroup,false);
        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        TaskViewHolder taskViewHolder = (TaskViewHolder) viewHolder;
        TaskEntry taskEntry = mTaskEntries.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdateAt());

        taskViewHolder.taskDescriptionView.setText(description);
        taskViewHolder.updatedAtView.setText(updatedAt);

        String priorityString = "" + priority;
        taskViewHolder.priorityView.setText(priorityString);

        GradientDrawable priorityCircl = (GradientDrawable) taskViewHolder.priorityView.getBackground();
        int prioritycolor = getPriorityColor(priority);
        priorityCircl.setColor(prioritycolor);
        taskViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int elementId = mTaskEntries.get(position).getId();
                mItemClickListener.onItemClickListener(elementId);
            }
        });
    }

    private int getPriorityColor(int priority){
        int priorityColor =0;
        switch (priority){
            case 1:
                priorityColor = ContextCompat.getColor(mContext,R.color.HighColor);
                break;

            case 2:
                priorityColor = ContextCompat.getColor(mContext,R.color.MediumColor);
                break;

            case 3:
                priorityColor = ContextCompat.getColor(mContext,R.color.LowColor);
                break;

            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if(mTaskEntries == null){
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTask() {return mTaskEntries;}

    public void setTasks(List<TaskEntry> taskEntries){
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
}
