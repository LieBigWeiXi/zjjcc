package com.example.culturecloud.Activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturecloud.BeiJingRes.GetRes;
import com.example.culturecloud.BeiJingRes.staticInfo.MagazineStaticAct;
import com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean.MagazineListParams;
import com.example.culturecloud.BeiJingRes.screenmagazine.responseRes.MagazineList;
import com.example.culturecloud.BeiJingRes.screenmagazine.responseRes.ResType;
import com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean.GetTypeParam;
import com.example.culturecloud.R;
import com.example.culturecloud.Views.XCFlowLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MagazineActivity extends AppCompatActivity {

   private XCFlowLayout mFlowLayout_thmemType,mFlowLayout_crowdType,mFlowLayout_letterType;
   View tv_theme,tv_crowd,tv_letter;
   Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           ResType resType = (ResType)msg.obj;
           switch (msg.what){
               case 200:
                   initChildViews(resType);
                   break;
               case 201:

                   break;
               case 202:

                   break;
           }
       }
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);
        mFlowLayout_thmemType = (XCFlowLayout) findViewById(R.id.theme_type);
        mFlowLayout_crowdType = (XCFlowLayout) findViewById(R.id.crowd_type);
        mFlowLayout_letterType = (XCFlowLayout) findViewById(R.id.letter_type);

        GetRes get_mgz_type,get_mgz_list,get_mgz_info,get_mgz_history,get_article_list,
                get_article_content,get_article_search,get_mgz_search;
        get_mgz_type = new GetRes();//获取期刊的分类
        get_mgz_list = new GetRes();//获取分类下资源列表
      /*  get_mgz_info = new GetRes();//获取期刊详情
        get_mgz_history = new GetRes();//获取过期期刊
        get_article_list = new GetRes();//获取期刊文章列表
        get_article_content = new GetRes();//获取文章内容
        get_article_search = new GetRes();//获取文章搜索结果
        get_mgz_search = new GetRes();//获取期刊搜索结果*/

        //输入参数实体类
        GetTypeParam mgz_type_param = new GetTypeParam();
        MagazineListParams mgz_list_param = new MagazineListParams();
        mgz_list_param.setPage_limit(1);
        mgz_list_param.setPage_num(1);

        get_mgz_type.get_res(MagazineStaticAct.GET_TYPE,mgz_type_param,new GetRes.CallBack() {
            @Override
            public void callback(String recData) {
                try {
                    JSONObject jsonObject = new JSONObject(recData);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONObject status_info = result.getJSONObject("status_info");
                    int status_code = status_info.getInt("status_code");
                    if(status_code==100){
                        ResType resType = new ResType();
                        String result_data = result.toString();
                        Gson gson = new Gson();
                        resType = gson.fromJson(result_data,ResType.class);
                        Message message = new Message();
                        message.what = 200;
                        message.obj = resType;
                        handler.sendMessage(message);
                    }else{
                        String error_msg = status_info.getString("status_message");
                        Toast.makeText(MagazineActivity.this,error_msg,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        get_mgz_list.get_res(MagazineStaticAct.MAGAZINE_LIST, mgz_list_param, new GetRes.CallBack() {
            @Override
            public void callback(String recData) {
                try {
                    JSONObject jsonObject = new JSONObject(recData);
                    JSONObject result,status_info;
                    result = jsonObject.getJSONObject("result");

                    Log.d("result----", "callback: "+recData);

                    status_info = jsonObject.getJSONObject("status_info");
                    if(status_info.getInt("status_code")==100){//请求数据成功
                        MagazineList magazineList = new MagazineList();
                        String result_data = result.toString();

                        Gson gson = new Gson();
                        magazineList = gson.fromJson(result_data,MagazineList.class);

                        Message msg = new Message();
                        msg.what = 201;
                        msg.obj = magazineList;
                        handler.sendMessage(msg);
                    }else{
                        Toast.makeText(MagazineActivity.this,"接口请求失败！",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void initChildViews(ResType resType) {
        // TODO Auto-generated method stub

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;


        final TextView theme_all_view,crowd_all_view,letter_all_view;
        theme_all_view = new TextView(this);
        crowd_all_view = new TextView(this);
        letter_all_view = new TextView(this);
        theme_all_view.setText("全部");
        theme_all_view.setTextSize(20);
        theme_all_view.setTextColor(Color.WHITE);
        crowd_all_view.setText("全部");
        crowd_all_view.setTextSize(20);
        crowd_all_view.setTextColor(Color.WHITE);
        letter_all_view.setText("全部");
        letter_all_view.setTextSize(20);
        letter_all_view.setTextColor(Color.WHITE);
        theme_all_view.setGravity(Gravity.CENTER_VERTICAL);
        crowd_all_view.setGravity(Gravity.CENTER_VERTICAL);
        letter_all_view.setGravity(Gravity.CENTER_VERTICAL);
        mFlowLayout_thmemType.addView(theme_all_view,lp);
        mFlowLayout_crowdType.addView(crowd_all_view,lp);
        mFlowLayout_letterType.addView(letter_all_view,lp);
        tv_theme = theme_all_view;
        tv_theme.setBackgroundResource(R.drawable.type_tag_shape);
        theme_all_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_theme.setBackgroundColor(Color.argb(0,0,0,0));
                theme_all_view.setBackgroundResource(R.drawable.type_tag_shape);
                tv_theme = theme_all_view;
            }
        });
        tv_crowd = crowd_all_view;
        tv_crowd.setBackgroundResource(R.drawable.type_tag_shape);
        crowd_all_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_crowd.setBackgroundColor(Color.argb(0,0,0,0));
                crowd_all_view.setBackgroundResource(R.drawable.type_tag_shape);
                tv_crowd = crowd_all_view;
            }
        });
        tv_letter = letter_all_view;
        tv_letter.setBackgroundResource(R.drawable.type_tag_shape);
        letter_all_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_letter.setBackgroundColor(Color.argb(0,0,0,0));
                letter_all_view.setBackgroundResource(R.drawable.type_tag_shape);
                tv_letter = letter_all_view;
                //显示全部的期刊

            }
        });
        for(final ResType.ThemeType typeName:resType.getTheme_class_array()){
            TextView view = new TextView(this);
            view.setText(typeName.getClass_name());
            view.setTextSize(20);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.FILL_VERTICAL);
            view.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            //view.setGravity(Gravity.CENTER_VERTICAL);
            mFlowLayout_thmemType.addView(view,lp);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_theme.setBackgroundColor(Color.argb(0,0,0,0));
                    view.setBackgroundResource(R.drawable.type_tag_shape);
                    tv_theme = view;
                }
            });
        }
        for(ResType.CrowdType typeName:resType.getCrowd_class_array()){
            TextView view = new TextView(this);
            view.setText(typeName.getClass_name());
            view.setTextSize(20);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER_VERTICAL);
            mFlowLayout_crowdType.addView(view,lp);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_crowd.setBackgroundColor(Color.argb(0,0,0,0));
                    view.setBackgroundResource(R.drawable.type_tag_shape);
                    tv_crowd = view;
                }
            });
        }
        for(ResType.LetterType typeName:resType.getLetter_class_array()){
            TextView view = new TextView(this);
            view.setText(typeName.getHeader_letter());
            view.setTextSize(20);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER_VERTICAL);
            mFlowLayout_letterType.addView(view,lp);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_letter.setBackgroundColor(Color.argb(0,0,0,0));
                    view.setBackgroundResource(R.drawable.type_tag_shape);
                    tv_letter = view;
                }
            });
        }


    }
}
