package com.example.culturecloud.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.culturecloud.Bean.LoginBean;
import com.example.culturecloud.Bean.MechanismBean;
import com.example.culturecloud.HttpRequest.doRequest;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.LoginInfo;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.culturecloud.HttpRequest.doRequest.getInstance;

public class LoginActivity extends BaseActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    doRequest http_request;
    EditText et_code,et_key;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String recv = (String)msg.obj;
                    
                    Log.d("LoginActivity",recv);
                    try{
                        JSONObject jsonObject = new JSONObject(recv);
                        int code = jsonObject.getInt("code");
                        Log.d("LoginActivity","code is"+code);
                        if(code==0){//服务器请求正确
                            Gson gson = new Gson();
                            String data = jsonObject.getJSONObject("data").toString();
                            MechanismBean mechanism = gson.fromJson(data,MechanismBean.class);
                            LoginInfo.mechanismBean = mechanism;
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//当前时间格式
                            Date registerDate = df.parse(mechanism.register_time);
                            Date currentDate = new Date();

                            int days = (int)((currentDate.getTime()-registerDate.getTime())/(1000*3600*24));

                            if(mechanism.getYears()==0){
                                Toast.makeText(LoginActivity.this,"抱歉，该用户已被管理员暂停使用",Toast.LENGTH_LONG).show();
                            } else if(days>=365*mechanism.years){
                                Toast.makeText(LoginActivity.this,"抱歉，您的使用权限已到期",Toast.LENGTH_LONG).show();
                            } else{
                                editor = pref.edit();
                                if(rememberPass.isChecked()){//检查复选框是否被选中
                                    editor.putBoolean("remember_password",true);
                                    editor.putString("code",mechanism.getCode());
                                    editor.putString("key",mechanism.getKey());
                                }else {
                                    editor.clear();
                                }
                                editor.apply();
                                String pano_url = mechanism.pano_url;
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                i.putExtra("url",pano_url);
                                startActivity(i);
                            }
                        } else{//服务器请求错误----需要异常处理？？？
                            Toast.makeText(LoginActivity.this,"服务器请求错误，输入密码可能有误",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                    default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        et_code = (EditText)findViewById(R.id.et_code);
        et_key = (EditText)findViewById(R.id.et_key);
        rememberPass = (CheckBox)findViewById(R.id.is_remenber_password);
        http_request = doRequest.getInstance(getApplicationContext());
        findViewById(R.id.tv_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            //将账号和密码设置到文本框
            String code = pref.getString("code","");
            String key = pref.getString("key","");
            et_code.setText(code);
            et_key.setText(key);
            rememberPass.setChecked(true);
            LoginBean loginBean = new LoginBean();
            loginBean.setCode(code);
            loginBean.setKey(key);
            //发起网络请求
            http_request.doPost(NetworkInfo.ip_address+NetworkInfo.login_url,
                    loginBean,handler,0);
        }
        final Button go_button = (Button)findViewById(R.id.bt_go);
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//验证
                if(!"".equals(et_code.getText().toString())&&!"".equals(et_key.getText().toString())){

                    LoginBean loginBean = new LoginBean();
                    loginBean.setCode(et_code.getText().toString());
                    loginBean.setKey(et_key.getText().toString());
                    http_request.doPost(NetworkInfo.ip_address+NetworkInfo.login_url,
                            loginBean,handler,0);
                    //go_button.setBackgroundColor(Color.YELLOW);
                }else{
                    Toast.makeText(LoginActivity.this,"账号或验证码为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        BaseActivity.addActivity(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivity.removeActivity(this);
    }
}
