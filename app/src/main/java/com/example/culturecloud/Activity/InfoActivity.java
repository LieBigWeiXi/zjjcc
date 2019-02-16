package com.example.culturecloud.Activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.culturecloud.R;

import java.util.UUID;
//显示机器编号
public class InfoActivity extends AppCompatActivity {
    //存储经纬度
    public SharedPreferences location_pref;
    private SharedPreferences.Editor editor;

    EditText jd_edit,wd_edit;
    Button confirm_btn;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final ActivityManager activityManager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo=activityManager.getDeviceConfigurationInfo();
        String strResult = Integer.toString(configurationInfo.reqGlEsVersion, 16);
        String code = getCode();
        ((TextView)findViewById(R.id.mech_id)).setText(code);
        ((TextView)findViewById(R.id.openGL_code)).setText(strResult);

        jd_edit = (EditText)findViewById(R.id.jd_edit);
        wd_edit = (EditText)findViewById(R.id.wd_edit);

        confirm_btn = (Button)findViewById(R.id.confirm_btn);
        tv = (TextView)findViewById(R.id.tv_test);
        location_pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = location_pref.edit();

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("jd_info",jd_edit.getText().toString());
                editor.putString("wd_info",wd_edit.getText().toString());
                editor.apply();
                String text = location_pref.getString("jd_info","")+location_pref.getString("wd_info","");
                tv.setText(text);
            }
        });
    }
    private String getCode(){
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return  uniqueId;
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
}
