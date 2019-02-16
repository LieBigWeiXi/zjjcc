package com.example.culturecloud.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.culturecloud.Bean.NewsBean;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/22.
 */

public class NewsAdapter extends ArrayAdapter<NewsBean> {
    private int resourceId;
    private int selectedId;
    private Context mContext;
    private List<NewsBean> mNewsBeanList = new ArrayList<>();

    public NewsAdapter(@NonNull Context context, int resource, List<NewsBean> newsBeanList) {
        super(context, resource,newsBeanList);
        this.resourceId = resource;
        this.mContext = context;
    }

    /*
    * */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsBean news = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.news_title = (TextView)view.findViewById(R.id.news_title);
//            viewHolder.news_date = (TextView)view.findViewById(R.id.news_date);
//            viewHolder.news_type = (TextView)view.findViewById(R.id.news_type);
            viewHolder.news_cover = (ImageView)view.findViewById(R.id.news_cover);
            viewHolder.news_bkg = (LinearLayout)view.findViewById(R.id.news_bkg);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.news_title.setText(news.getNi_name());

        Picasso.with(mContext).load(NetworkInfo.ip_address+news.getNi_cover()).
                into(viewHolder.news_cover);

        if(position==selectedId){
            viewHolder.news_title.setTextColor(Color.WHITE);
            viewHolder.news_title.setTextSize(12);
        }else{
            viewHolder.news_title.setTextColor(mContext.getResources().getColor(R.color.newsTitleColor));
            viewHolder.news_title.setTextSize(10);
        }
        return view;
    }

    public class ViewHolder{
        TextView     news_title;
        ImageView    news_cover;
//        TextView     news_date;
//        TextView     news_type;
        LinearLayout news_bkg;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public void addNewsBean(List<NewsBean> mNewsBeans){
        this.mNewsBeanList.addAll(mNewsBeans);
    }
}
