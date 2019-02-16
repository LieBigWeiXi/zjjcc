package com.example.culturecloud.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.culturecloud.Bean.TypeBean;
import com.example.culturecloud.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/15.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolders>{
    public List<TypeBean> TypeList = new ArrayList<>();
    private int positon = 0;
    Handler handler;
    private OnClickCallBack callBack;
    public interface OnClickCallBack{
        public void Onclick(int position);
    }
    static class ViewHolders extends RecyclerView.ViewHolder{
        TextView tv_video_type;
        ImageView iv_press_ic;
        View view;
        public ViewHolders(View view){
            super(view);
            this.view =view;
            tv_video_type = (TextView)view.findViewById(R.id.tv_video_type);
            iv_press_ic = (ImageView)view.findViewById(R.id.iv_press_ic);
        }
    }

    public VideoAdapter(List<TypeBean> TypeList, Handler handler, OnClickCallBack callBack){
        this.handler=handler;
        this.TypeList = TypeList;
        this.callBack =callBack;
    }

    @Override
    public void onBindViewHolder(ViewHolders holder, final int position) {
        TypeBean video_type = TypeList.get(position);
        if(this.positon==position){
            holder.iv_press_ic.setVisibility(View.VISIBLE);
            holder.tv_video_type.setTextColor(Color.rgb(13,150,255));
        }else {
            holder.tv_video_type.setTextColor(Color.WHITE);
            holder.iv_press_ic.setVisibility(View.INVISIBLE);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.Onclick(position);
            }
        });
        holder.tv_video_type.setText(video_type.getDt_name());
    }

    @Override
    public ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_type_item,
                parent,false);

        ViewHolders holder = new ViewHolders(view);
        return holder;
    }

    public int getItemCount(){
        return TypeList.size();
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }
}