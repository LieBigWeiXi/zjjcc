package com.example.culturecloud.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.culturecloud.Bean.TypeBean;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL on 2018/7/13.
 */

public class ScenAdapter extends ArrayAdapter<TypeBean> {
    private int resourceId;
    private  int selected_id = 0;
    public ScenAdapter(@NonNull Context context, int resource, List<TypeBean> objects) {
        super(context, resource,objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TypeBean sceneryTypeBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView scenery_img = (ImageView)view.findViewById(R.id.scenery_img);
        TextView scenery_text = (TextView)view.findViewById(R.id.scenery_text);
        if(sceneryTypeBean.getId()==0){//id=0,全部
            scenery_img.setImageResource(R.drawable.ic_all_scenery);
            scenery_text.setText(sceneryTypeBean.getDt_name());
        }else{
            scenery_text.setText(sceneryTypeBean.getDt_name());
            if(NetworkInfo.network_status == 0){//请求成功
                Picasso.with(getContext())//加载图标
                        .load(NetworkInfo.ip_address+sceneryTypeBean.getDt_icon())
                        .into(scenery_img);
            }else {
                //sceneryTypeBean
            }

        }
        if(sceneryTypeBean.getId()==selected_id){
            //view.setBackgroundResource(R.drawable.chg_bkg);
        }
        //scenery_img.setImageResource(sceneryTypeBean.getDt_icon());

        return view;
    }

    public void setSelected_id(int selected_id) {
        this.selected_id = selected_id;
    }
}
