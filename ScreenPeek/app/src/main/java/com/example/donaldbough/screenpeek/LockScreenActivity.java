package com.example.donaldbough.screenpeek;

import android.app.Activity;
        import android.app.Application;
        import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
        import android.os.Bundle;
        import android.telephony.PhoneStateListener;
        import android.telephony.TelephonyManager;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.WindowManager;
        import android.view.WindowManager.LayoutParams;
        import android.widget.Button;
        import com.example.donaldbough.screenpeek.utils.LockscreenService;
        import com.example.donaldbough.screenpeek.utils.LockscreenUtils;

public class LockScreenActivity extends Activity implements
        LockscreenUtils.OnLockStatusChangedListener {
    // Member variables
    private LockscreenUtils mLockscreenUtils;
    public static LockScreenActivity instance = null;

    // Set appropriate flags to make the screen appear over the keyguard
    @Override
    public void onAttachedToWindow() {
        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        super.onAttachedToWindow();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);
        instance = this;
        mLockscreenUtils = new LockscreenUtils();

        // unlock screen in case of app get killed by system
        if (getIntent() != null && getIntent().hasExtra("kill")
                && getIntent().getExtras().getInt("kill") == 1) {
            enableKeyguard();
        } else {
            try {
                // disable keyguard
                disableKeyguard();
                // start service for observing intents
//                startService(new Intent(this, LockscreenService.class));

                // listen the events get fired during the call
//                StateListener phoneStateListener = new StateListener();
//                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//                telephonyManager.listen(phoneStateListener,
//                        PhoneStateListener.LISTEN_CALL_STATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceContentView() {
        Log.d("debug", "tried to replace content view");
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        instance = this;
    }

    @Override
    public void onPause()
    {
        super.onPause();
//        instance = null;
    }

    // Handle events of calls and unlock screen if necessary
//    private class StateListener extends PhoneStateListener {
//        @Override
//        public void onCallStateChanged(int state, String incomingNumber) {
//
//            super.onCallStateChanged(state, incomingNumber);
//            switch (state) {
//                case TelephonyManager.CALL_STATE_RINGING:
//                    unlockHomeButton();
//                    break;
//                case TelephonyManager.CALL_STATE_OFFHOOK:
//                    break;
//                case TelephonyManager.CALL_STATE_IDLE:
//                    break;
//            }
//        }
//    };

    // Don't finish Activity on Back press
    @Override
    public void onBackPressed() {
        Log.d("debug", "onBackPressed");
        return;
    }

    // Handle button clicks
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
//                || (keyCode == KeyEvent.KEYCODE_POWER)
//                || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
//                || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
//            return true;
//        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            Log.d("debug", "home key press");
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    // handle the key press events here itself
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
//                || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
//                || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
//            return false;
//        }
//        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {
//
//            return true;
//        }
//        return false;
//    }

    //Needed for implements
    @Override
    public void onLockStatusChanged(boolean isLocked) {
        Log.d("debug", "on lock status changed");
//        if (!isLocked) {
//            unlockDevice();
//        }
    }

    @SuppressWarnings("deprecation")
    private void disableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.disableKeyguard();
    }

    @SuppressWarnings("deprecation")
    private void enableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.reenableKeyguard();
    }

    public void recordVideo(View view) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }
}