package com.example.donaldbough.screenpeek.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.donaldbough.screenpeek.HomeActivity;
import com.example.donaldbough.screenpeek.LockScreenActivity;

public class LockscreenIntentReceiver extends BroadcastReceiver {
    // Handle actions and display Lockscreen
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)
                || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("debug", "got on or boot");
            start_lockscreen(context);
        }
        else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.d("debug", "got present");
            if (LockScreenActivity.instance != null) {
                LockScreenActivity.instance.replaceContentView("home");
            }
        }
    }

    // Display lock screen
    private void start_lockscreen(Context context) {
        Intent mIntent = new Intent(context, LockScreenActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("debug", "restarted lockscreen");
        context.startActivity(mIntent);
        if (LockScreenActivity.instance != null) {
            LockScreenActivity.instance.replaceContentView("lock");
        }
    }
}
