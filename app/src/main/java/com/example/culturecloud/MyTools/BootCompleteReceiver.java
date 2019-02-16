package com.example.culturecloud.MyTools;

//开机启动 广播接收器
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.culturecloud.Activity.LoginActivity;

public class BootCompleteReceiver extends BroadcastReceiver {
    static final String action_boot = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals(action_boot)){
            Intent startIntent = new Intent(context, LoginActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startIntent);
        }
    }
}
