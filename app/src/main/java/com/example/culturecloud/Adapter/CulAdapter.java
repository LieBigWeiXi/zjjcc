package com.example.culturecloud.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.culturecloud.Bean.CulInfo;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/11.
 */

public class CulAdapter extends RecyclerView.Adapter<CulAdapter.ViewHolder>{
    public List<CulInfo.culData> culDataList =  new ArrayList<>();
    Context context;
    Handler handler;

    public CulAdapter(Context context,Handler handler){
        this.context = context;
        this.handler = handler;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.cul_person_item);
        }
    }
    @Override
    public void onBindViewHolder(final CulAdapter.ViewHolder holder, int position) {
        CulInfo.culData cul_person = culDataList.get(position);
        Log.w("tag",NetworkInfo.ip_address+cul_person.getCi_photo());
        Picasso.with(context)
                .load(NetworkInfo.ip_address+cul_person.getCi_photo())
                .into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Message msg = new Message();
                msg.what = 300;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cul_person_item,
                parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public int getItemCount(){
        return culDataList.size();
    }

    public void setCulDataList(List<CulInfo.culData> culDataList) {
        this.culDataList = culDataList;
    }

    public void addCulDataList(List<CulInfo.culData> culDataList) {
        this.culDataList.addAll(culDataList);
    }
}
