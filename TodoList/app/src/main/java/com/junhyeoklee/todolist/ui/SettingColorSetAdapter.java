package com.junhyeoklee.todolist.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.todolist.R;
import com.junhyeoklee.todolist.data.model.TaskColor;

import java.util.ArrayList;
import java.util.List;

public class SettingColorSetAdapter extends RecyclerView.Adapter<SettingColorSetAdapter.ColorSetViewHolder> {
    private List<TaskColor> mColor;
    Context mContext;
    private SharedPreferences appData;

    public SettingColorSetAdapter(ArrayList items){
        this.mColor = items;
    }


    @Override
    public SettingColorSetAdapter.ColorSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_color_set_recyclerview, parent, false);
        mContext = parent.getContext();
        ColorSetViewHolder holder = new ColorSetViewHolder(v);
        return holder;
    }

    // 필수 오버라이드 : 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final ColorSetViewHolder holder, final int position) {

//        if(position%2 == 1)//홀수일 때만 색상변경
//        {
//            holder.mlayout_team_color_recyclerview.setBackgroundResource(R.drawable.team_color_recycler_click);
//        }
        // 해당 position 에 해당하는 데이터 결합
       final String ColorName = mColor.get(position).getColorName();
       final int ImgColor = mColor.get(position).getColorBackGround();
        holder.mColorName.setText(ColorName);
        holder.mImgColor.setBackgroundResource(ImgColor);
        final int itemPosition = holder.getAdapterPosition();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingManager.get().setColorBackGround(mColor.get(position).getColorBackGround());

                Intent lIntent = new Intent(v.getContext(),SettingPreferenceActivity.class);
                lIntent.setFlags(lIntent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(lIntent);

            }

        });

    }

    @Override
    public int getItemCount() {
        return mColor.size();
    }

    public static class ColorSetViewHolder extends RecyclerView.ViewHolder {
        public TextView mColorName;
        public LinearLayout mImgColor;
        private LinearLayout mlayout_team_color_recyclerview;

        public ColorSetViewHolder(View itemView) {
            super(itemView);
            mColorName = (TextView)itemView.findViewById(R.id.tv_team_color_set_recyclerview);
            mImgColor = (LinearLayout)itemView.findViewById(R.id.img_team_color_set_recyclerview);
            mlayout_team_color_recyclerview = (LinearLayout)itemView.findViewById(R.id.layout_team_color_recyclerview);

        }
    }

    /**
     * Friends RecyclerView 간격 조정하기 위해 사용
     */
    public static class RecyclerViewDecoration extends RecyclerView.ItemDecoration{
        private final int divHeight;

        public RecyclerViewDecoration(int divHeight){
            this.divHeight = divHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = divHeight;
        }
    }

}
