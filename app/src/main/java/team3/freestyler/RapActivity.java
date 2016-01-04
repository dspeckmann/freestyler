package team3.freestyler;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Diese Activity ermöglicht es dem User, das Spiel zu starten und einen Rap aufzunehmen.
 */
public class RapActivity extends AppCompatActivity {

    private AudioService audioService;
    private WordDataSource wordDataSouce;
    private Timer timer;
    private ServiceConnection audioServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AudioService.InGameIoBinder binder = (AudioService.InGameIoBinder)service;
            audioService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rap);
        wordDataSouce = new WordDataSource(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, AudioService.class);
        startService(intent);
        bindService(intent, audioServiceConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(audioServiceConnection);
    }

    public void buttonPlayOnClick(View v) {
        Button btnPlay = (Button) findViewById(R.id.play);
        Drawable drawPlay = getDrawable(R.drawable.buttonplay);
        Bitmap bitmapPlay = ((BitmapDrawable)drawPlay).getBitmap();
        Bitmap bitmapButton = ((BitmapDrawable)btnPlay.getBackground()).getBitmap();
        if (bitmapButton == bitmapPlay) {
            btnPlay.setBackgroundResource(R.drawable.buttonstop);
            String recordPath = getFilesDir().getAbsolutePath() + File.separator + "rap" +
                    GregorianCalendar.getInstance().getTimeInMillis() + ".3gp";
            Beat beat = Beat.getTestBeat(this);
            audioService.setRap(new Rap(0, new Date(), recordPath, beat));
            audioService.startPauseRecord();

            if(timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            MyTimerTask myTimerTask = new MyTimerTask();
            timer.schedule(myTimerTask, 1000, 8000);
        }
        else {
            audioService.stopRecord();
            if (timer != null) {
                timer.cancel();
            }
            Intent intent = new Intent(this, EndActivity.class);
            startActivity(intent);
            finish();
        }

    }

    class MyTimerTask extends TimerTask {
        public void run(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView text = (TextView) findViewById(R.id.word);
                    wordDataSouce.open();
                    text.setText(wordDataSouce.getRandomWordFromActivePackages().getWord());
                    wordDataSouce.close();
                }
            });
        }
    }
}
