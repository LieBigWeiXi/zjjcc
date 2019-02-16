package com.example.culturecloud.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.service.carrier.CarrierService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturecloud.Bean.AddressBean;
import com.example.culturecloud.Bean.CastsBean;
import com.example.culturecloud.Bean.ForecastsBean;
import com.example.culturecloud.Bean.WeatherBean;
import com.example.culturecloud.HttpRequest.doRequest;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.example.culturecloud.db.City;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    doRequest http_request;
    String pano_url;
    List<CastsBean> castslist = new ArrayList<>();
    WeatherBean weatherBean;
    public static String city;
    TextView location_text,today_weather_tv,today_temp_tv,tomorrow_weather_tv,tomorrow_temp_tv;
    ImageView today_weather_ic,tomorrow_weather_ic;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 200://获取天气数据
                    String data = (String)msg.obj;
                    AddressBean address = gson.fromJson(data,AddressBean.class);
                    if(address.getStatus()==1){
                        city = address.getCity();
                        String url = "https://restapi.amap.com/v3/weather/weatherInfo?key="+NetworkInfo.amap_api_key+
                                "&city="+city+"&extensions=all";
                        http_request.doGet(url,201,handler);
                        //地点 长沙市
                        location_text = (TextView) findViewById(R.id.location_txt);
                        location_text.setText(city);

                    }else{
                        Toast.makeText(MainActivity.this,"获取位置信息发生错误！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 201://解析天气信息
                    String weather_data = (String)msg.obj;
                    weatherBean = gson.fromJson(weather_data, WeatherBean.class);
                    if(weatherBean.getStatus()==1){//返回状态成功
                        if(weatherBean.getCount()>0){//返回的结果大于0
                            for(ForecastsBean forecastsBean : weatherBean.getForecasts()){
                                if(forecastsBean.getCity().equals(city)){
                                    castslist.addAll(forecastsBean.getCasts());
                                    break;
                                }
                            }
                            showSimpleWeather(castslist);
                        }else{
                            Toast.makeText(MainActivity.this,"获取天气数据异常！",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"获取天气数据失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        pano_url = intent.getStringExtra("url");
        TextView date_txt = (TextView)findViewById(R.id.date_txt);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
        date_txt.setText(df.format(date));

        findViewById(R.id.weather_simple_layout).setOnClickListener(this);
        findViewById(R.id.culture_img).setOnClickListener(this);
        findViewById(R.id.scenery_img).setOnClickListener(this);
        findViewById(R.id.iv_pano).setOnClickListener(this);
        findViewById(R.id.iv_vod).setOnClickListener(this);
        findViewById(R.id.iv_prop).setOnClickListener(this);
        findViewById(R.id.book).setOnClickListener(this);
        findViewById(R.id.magazine).setOnClickListener(this);
        findViewById(R.id.newspaper).setOnClickListener(this);
        findViewById(R.id.tv_people_newspaper).setOnClickListener(this);
        findViewById(R.id.tv_jfj_newspaper).setOnClickListener(this);
        findViewById(R.id.tv_zjj_newspaper).setOnClickListener(this);
        findViewById(R.id.iv_ydcc).setOnClickListener(this);
        TextView website = (TextView) findViewById(R.id.tv_enterwebsite);
        website.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        website.setOnClickListener(this);
        http_request = doRequest.getInstance(getApplicationContext());
        //"长沙市","adcode":"430100" "长沙县","adcode":"430121"
        http_request.doGet("https://restapi.amap.com/v3/ip?key="+ NetworkInfo.amap_api_key,200,handler);

        BaseActivity.addActivity(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        PackageManager packageManager = this.getPackageManager();
        switch (view.getId()){
            case R.id.weather_simple_layout://天气
                intent = new Intent(this,WeatherInfoActivity.class);
                intent.putExtra("weather_data", (Serializable) castslist);
                startActivity(intent);
                break;
            case R.id.culture_img://名人
                intent = new Intent(this,CulPersonActivity.class);
                startActivity(intent);
                break;
            case R.id.scenery_img://古迹
                intent = new Intent(this,SceneryActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_pano://全景
                //url= http://222.240.228.76:8002/tour/ba80e9e39a50a5c8
                intent = new Intent(this,WebActivity.class);
                Log.d("MainAcitivity", "onClick:url= "+pano_url);
                intent.putExtra("url",pano_url);
                startActivity(intent);
                break;
            case R.id.iv_vod://视频
                intent = new Intent(this,VideoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_enterwebsite://官网
                intent = new Intent(this,WebActivity.class);
                intent.putExtra("url","http://110.53.52.89:8004/");
                startActivity(intent);
                break;
            case R.id.tv_people_newspaper://人民日报
                intent = new Intent(this,WebActivity.class);
                intent.putExtra("url","http://www.people.com.cn/");
                startActivity(intent);
                break;
            case R.id.tv_zjj_newspaper://张家界日报
                intent = new Intent(this,WebActivity.class);
                intent.putExtra("url","http://www.cnepaper.com/zjjrb/html/2018-08/07/node_1.htm");
                startActivity(intent);
                break;
            case R.id.tv_jfj_newspaper://解放军报
                intent = new Intent(this,WebActivity.class);
                intent.putExtra("url","http://www.81.cn/xue-xi/102726.htm ");
                startActivity(intent);
                break;
            case R.id.iv_prop://社区新闻
                intent = new Intent(this,NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.newspaper://报纸
                intent = new Intent();
                intent.setAction("yougu");
                //type 1.期刊 2.书籍 3.报纸
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type", 3);
                this.startActivity(intent);
                break;
            case R.id.magazine://杂志
                intent = new Intent();
                intent.setAction("yougu");
                //type 1.期刊 2.书籍 3.报纸
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type", 1);
                this.startActivity(intent);
                break;
            case R.id.book://图书
                intent = new Intent();
                intent.setAction("yougu");
                //type 1.期刊 2.书籍 3.报纸
                intent.putExtra("type", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            case R.id.iv_ydcc://设置退出系统
                final EditText input_password = new EditText(this);
                new AlertDialog.Builder(this).setTitle("password")
                        .setIcon(R.drawable.exit_icon)
                        .setView(input_password)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //按下确定键后的事件
                                /*Toast.makeText(getApplicationContext(), input_password.getText().toString(),Toast.LENGTH_LONG).show();*/
                                String input_word = input_password.getText().toString();
                                if("ydcc123".equals(input_word)){
                                   BaseActivity.finishAll();
                                }else{
                                    Toast.makeText(getApplicationContext(), "password error",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("取消",null).show();
                break;
            default:
                break;
        }
    }

    public void showSimpleWeather(List<CastsBean> castsBeanList){
        today_weather_tv = (TextView)findViewById(R.id.today_info);
        today_temp_tv = (TextView)findViewById(R.id.today_temp);
        tomorrow_weather_tv = (TextView)findViewById(R.id.tomorrow_info);
        tomorrow_temp_tv = (TextView)findViewById(R.id.tomorrow_temp);
        String today_temp,tomorrow_temp;
        today_temp = castsBeanList.get(0).getDaytemp()+"/"+ castsBeanList.get(0).getNighttemp()+"℃";
        tomorrow_temp = castsBeanList.get(1).getDaytemp()+"/"+ castsBeanList.get(1).getNighttemp()+"℃";
        today_weather_tv.setText(castsBeanList.get(0).getDayweather());
        today_temp_tv.setText(today_temp);
        tomorrow_weather_tv.setText(castsBeanList.get(1).getDayweather());
        tomorrow_temp_tv.setText(tomorrow_temp);

        //利用天气描述匹配天气图标
        today_weather_ic = (ImageView)findViewById(R.id.today_weatherIcon);
        tomorrow_weather_ic = (ImageView)findViewById(R.id.tomorrow_weatherIcon);
        String today_weatherInfo = castsBeanList.get(0).getDayweather();
        String tomorrow_weatherInfo = castsBeanList.get(1).getDayweather();
        setWeatherIcon(today_weatherInfo,today_weather_ic);
        setWeatherIcon(tomorrow_weatherInfo,tomorrow_weather_ic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivity.removeActivity(this);
    }

    public static void setWeatherIcon(String weatherInfo, ImageView imageView){
        if(weatherInfo.contains("晴")&&weatherInfo.contains("云")){
            imageView.setImageResource(R.drawable.sun_cloud);
        }else if(weatherInfo.contains("雨")&&weatherInfo.contains("雪")){
            imageView.setImageResource(R.drawable.rain_snow);
        }else if(weatherInfo.contains("阵雨")){
            imageView.setImageResource(R.drawable.rains);
        }else if(weatherInfo.contains("云")||weatherInfo.contains("阴")){
            imageView.setImageResource(R.drawable.overcast);
        }else if(weatherInfo.contains("尘")){
            imageView.setImageResource(R.drawable.storm);
        }else if(weatherInfo.contains("晴")) {
            imageView.setImageResource(R.drawable.sun);
        } else if(weatherInfo.contains("雨")) {
            imageView.setImageResource(R.drawable.rain);
        }else if(weatherInfo.contains("雾")) {
            imageView.setImageResource(R.drawable.mist);
        } else if(weatherInfo.contains("雪")) {
            imageView.setImageResource(R.drawable.snow);
        }


    }
}
