package com.example.culturecloud.Activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.culturecloud.BeiJingRes.GetRes;
import com.example.culturecloud.BeiJingRes.screenpaper.paperBean.PaperType;
import com.example.culturecloud.BeiJingRes.screenpaper.paperBean.responseBean.PaperTypeResult;
import com.example.culturecloud.BeiJingRes.staticInfo.PaperStaticAct;
import com.example.culturecloud.R;
import com.example.culturecloud.Views.XCFlowLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PaperActivity extends AppCompatActivity {

    private XCFlowLayout circle_fl,region_fl,time_fl,bussiness_fl;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 200:
                    PaperTypeResult paper_type = (PaperTypeResult)msg.obj;
                    initPaperTypeView(paper_type);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        PaperType paperTypeBean = new PaperType();

        GetRes getPaperRes = new GetRes();
        getPaperRes.get_res(PaperStaticAct.PAPER_TYPE, paperTypeBean, new GetRes.CallBack() {
            @Override
            public void callback(String recData) {
                PaperTypeResult type_result = new PaperTypeResult();
                try {
                    JSONObject jsonObject = new JSONObject(recData);
                    JSONObject result_data = jsonObject.getJSONObject("result");

                    JSONObject status_info = jsonObject.getJSONObject("status_info");
                    if(status_info.getInt("status_code")==100){//接口请求数据成功
                        Log.d("paper_response", "callback: "+result_data);
                        PaperTypeResult typeResult = new PaperTypeResult();
                        Gson gson = new Gson();
                        String result = result_data.toString();
                        typeResult = gson.fromJson(result,PaperTypeResult.class);
                        Message msg = new Message();
                        msg.what = 200;
                        msg.obj = typeResult;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void initPaperTypeView(PaperTypeResult type_result){
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;

        TextView tv_circle,tv_region,tv_time,tv_bussiness;
        tv_bussiness = new TextView(this);
        tv_circle = new TextView(this);
        tv_time = new TextView(this);
        tv_region = new TextView(this);

        tv_bussiness.setTextSize(20);
        tv_circle.setTextSize(20);
        tv_region.setTextSize(20);
        tv_time.setTextSize(20);

        tv_bussiness.setTextColor(Color.WHITE);
        tv_circle.setTextColor(Color.WHITE);
        tv_region.setTextColor(Color.WHITE);
        tv_time.setTextColor(Color.WHITE);

        for(PaperTypeResult.CycleClass cycleClass:type_result.cycle_class_array){
            TextView view = new TextView(this);
            view.setText(cycleClass.class_name);
            view.setTextColor(Color.WHITE);
            view.setTextSize(20);
            circle_fl.addView(view,lp);
        }
    }
}
