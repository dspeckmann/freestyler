package me.speckmann.danny;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Diese Activity wird nach einer erfolgreichen Audioaufnahme aufgerufen und ermöglicht dem User, seinen Rap noch einmal anzuhören,
 * ihn zu speichern und schließlich zurück ins Hauptmenü zu gelangen.
 */
public class EndActivity extends AppCompatActivity {

    private boolean saved = false;
    private AudioService audioService;
    private ServiceConnection ioConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AudioService.InGameIoBinder binder = (AudioService.InGameIoBinder)service;
            audioService = binder.getService();

            TextView txtTime = (TextView)findViewById(R.id.time);
            Date duration = new Date(audioService.getRapDuration());
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
            txtTime.setText(dateFormat.format(duration));
        }

        @Override
        public void onServiceDisconnected(ComponentName className) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, ioConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Intent i = new Intent(this, AudioService.class);
        unbindService(ioConnection);
        stopService(i);
    }

    public void buttonListenToRapOnClick(View v) {
        audioService.playPauseRap();
    }

    public void buttonSaveRapOnClick(View v) {
        RapDataSource rapDataSource = new RapDataSource(this);
        rapDataSource.open();
        rapDataSource.createRap(audioService.getRap().getPath(), audioService.getRap().getBeat());
        rapDataSource.close();
        Button btnSave = (Button)findViewById(R.id.saverap);
        btnSave.setEnabled(false);
        Toast.makeText(this, getString(R.string.rap_saved_message), Toast.LENGTH_SHORT).show();
        saved = true;
    }

    public void buttonMainMenuOnClick(View v) {
        deleteIfNotSavedAndExit();
    }

    @Override
    public void onBackPressed() {
        deleteIfNotSavedAndExit();
    }

    private void deleteIfNotSavedAndExit() {
        if(!saved) {
            File file = new File(audioService.getRap().getPath());
            file.delete();
            Log.d("[FreeStyler]", "Rap wurde erfolgreich aus dem Dateisystem gelöscht.");
        }
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
