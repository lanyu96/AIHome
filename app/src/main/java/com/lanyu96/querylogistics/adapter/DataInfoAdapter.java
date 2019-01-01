package com.lanyu96.querylogistics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanyu96.querylogistics.R;
import com.lanyu96.querylogistics.bean.LocAndTimeInfo;

import java.util.List;

public class DataInfoAdapter extends RecyclerView.Adapter<DataInfoAdapter.DataInfoViewHolder> {

    private Context mContext;
    private List mList;

    public DataInfoAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public DataInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //这里设置布局管理器
        return new DataInfoViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.layout_item_data_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataInfoViewHolder dataInfoViewHolder, int i) {
        LocAndTimeInfo lti = (LocAndTimeInfo) mList.get(i);
        dataInfoViewHolder.time_tv.setText(lti.getTime());
        dataInfoViewHolder.location_info_tv.setText(lti.getLoc());
    }


    @Override
    public int getItemCount() {
        //设置条目长度
        return mList.size();
    }

    //创建一个ViewHolder类,
    class DataInfoViewHolder extends RecyclerView.ViewHolder {

        private final TextView time_tv;
        private final TextView location_info_tv;

        public DataInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            //声明控件
            time_tv = itemView.findViewById(R.id.item_time_tv);
            location_info_tv = itemView.findViewById(R.id.item_location_info_tv);

        }
    }

}
