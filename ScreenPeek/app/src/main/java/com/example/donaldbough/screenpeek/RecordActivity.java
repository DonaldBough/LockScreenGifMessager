package com.example.donaldbough.screenpeek;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.example.donaldbough.screenpeek.utils.LockscreenService;

import java.util.ArrayList;

import static android.transition.Fade.IN;
import static java.sql.Types.TIME;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

public class RecordActivity extends Activity {
    static final int REQUEST_VIDEO_CAPTURE = 1;
    VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_review);
        videoView = findViewById(R.id.videoView);

        disableKeyguard();
        // start service for observing intents
        startService(new Intent(this, LockscreenService.class));
        //Go to default video recording activity
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    //On video recording finish
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            videoView.setVideoURI(videoUri);
//            MediaMetadataRetriever mmRetriever = new MediaMetadataRetriever();
//            mmRetriever.setDataSource(this, videoUri);
//
//            // Array list to hold your frames
//            ArrayList<Bitmap> frames = new ArrayList<Bitmap>();
//
//            //Create a new Media Player
//            MediaPlayer mp = MediaPlayer.create(getBaseContext(), videoUri);
//
//            // Some kind of iteration to retrieve the frames and add it to Array list
//            Bitmap bitmap = mmRetriever.getFrameAtTime(TIME IN MICROSECONDS);
//            frames.add(bitmap);
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void disableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.disableKeyguard();
    }
}
