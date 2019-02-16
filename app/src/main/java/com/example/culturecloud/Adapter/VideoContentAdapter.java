package com.example.culturecloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.culturecloud.Activity.BaseActivity;
import com.example.culturecloud.Bean.PicturesBean;
import com.example.culturecloud.Bean.VideoInfo;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/16.
 */

public class VideoContentAdapter extends BaseAdapter{
    private List<VideoInfo> videoInfoList;
    private LayoutInflater layoutInflater;
    private Context context;

    public VideoContentAdapter( Context context) {
        this.videoInfoList = new ArrayList<VideoInfo>();
        this.layoutInflater = layoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return videoInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoContentAdapter.Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = layoutInflater.inflate(R.layout.video_item, parent, false);
            holder.cover_image = (ImageView) convertView.findViewById(R.id.video_img);
            holder.textViewTitle=(TextView)convertView.findViewById(R.id.video_name);
            holder.textViewOrder=(TextView)convertView.findViewById(R.id.title_order);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        VideoInfo videoInfo = videoInfoList.get(position);
        Picasso.with(context)
                .load(NetworkInfo.static_address+videoInfo.vi_cover)
                .into(holder.cover_image);
        holder.textViewTitle.setText(videoInfo.vi_name);
        holder.textViewOrder.setText(String.valueOf(position+1));
        return  convertView;
    }

    class Holder{
        ImageView cover_image;
        TextView textViewTitle,textViewOrder;
    }
    public void setArrayList(List<VideoInfo> arrayList) {
        this.videoInfoList = arrayList;
    }


    public void addArrayList(List<VideoInfo> arrayList) {
        this.videoInfoList.addAll(arrayList);
    }

    public void clear() {
        videoInfoList.clear();
        notifyDataSetChanged();
    }
}
