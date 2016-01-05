package me.speckmann.danny;


import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.List;


/**
 * Dieser Service realisiert die Audioaufnahme und -wiedergabe in der kompletten App.
 */
public class AudioService extends Service {

    private final IBinder binder = new InGameIoBinder();
    private Rap currentRap;
    private MediaRecorder mediaRecorder;
    private MediaPlayer beatPlayer;
    private MediaPlayer rapPlayer;

    private enum Status {
        OFF, ON, PAUSE
    }
    Status recordStatus;
    Status rapStatus;
    Status beatStatus;
    Word[] words;

    /**
     * Erstellt einen neuen AudioService-Service.
     */
    public AudioService() {
        recordStatus = Status.OFF;
        rapStatus = Status.OFF;
        beatStatus = Status.OFF;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("[FreeStyler]", "AudioService.onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        WordPackageDataSource wpds = new WordPackageDataSource(this);
        wpds.open();
        List<WordPackage> packages = wpds.getActiveWordPackages();
        wpds.close();
        WordDataSource wds = new WordDataSource(this);
        wds.open();
        List<Word> wordList = new ArrayList<>();
        for(WordPackage wp: packages) {
            wordList.addAll(wds.getWordsByPackage(wp));
        }
        wds.close();
        words = wordList.toArray(new Word[wordList.size()]);
    }

    /**
     * Beginnt oder pausiert die Aufnahme, je nach aktuellem Status.
     */
    public void startPauseRecord() {
        switch(recordStatus) {
            case OFF:
                recordStatus = Status.ON;
                //MediaRecorder für Aufnahmen.
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
                mediaRecorder.setOutputFile(currentRap.getPath());
                try {
                    mediaRecorder.prepare();
                } catch (IOException ex) {
                    String errorMessage = getResources().getString(R.string.error_media_recorder_preparation);
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                }
                mediaRecorder.start();
                // Beat abspielen
                this.playPauseBeat(true);
                break;
            case ON:
                break;
            case PAUSE:
                break;
        }
    }

    // TODO: Split start/stop functions

    /**
     * Stoppt die Aufnahme, wenn sie läuft.
     */
    public void stopRecord() {
        switch(recordStatus) {
            case OFF:
                break;
            case ON:
                recordStatus = Status.OFF;
                mediaRecorder.stop();
                this.stopBeat();
                mediaRecorder.release();
                mediaRecorder = null;
                break;
            case PAUSE:
                recordStatus = Status.OFF;
                mediaRecorder.stop();
                this.stopBeat();
                mediaRecorder.release();
                mediaRecorder = null;
                break;
        }
    }

    /**
     * Spielt den Beat ab oder pausiert ihn, je nach Status.
     *
     * @param onLoop Besimmt, ob der Beat in einer Endlosschleife abgespielt wird.
     */
    public void playPauseBeat(Boolean onLoop) {
        switch(beatStatus) {
            case OFF:
                beatPlayer = MediaPlayer.create(this, Uri.parse(currentRap.getBeat().getPath()));
                beatPlayer.setLooping(onLoop);
                beatPlayer.start();
                beatStatus = Status.ON;
                break;
            case ON:
                beatPlayer.pause();
                beatStatus = Status.PAUSE;
                break;
            case PAUSE:
                beatPlayer.start();
                beatStatus = Status.ON;
                break;
        }
    }

    /**
     * Stoppt den Beat, wenn er läuft.
     */
    public void stopBeat() {
        switch (beatStatus){
            case OFF:
                break;
            case ON:
                beatStatus = Status.OFF;
                beatPlayer.stop();
                beatPlayer.release();
                beatPlayer = null;
                break;
            case PAUSE:
                beatStatus = Status.OFF;
                beatPlayer.stop();
                beatPlayer.release();
                beatPlayer = null;
                break;
        }

    }

    /**
     * Spielt den Rap ab oder pausiert ihn, je nach aktuellem Status.
     */
    public void playPauseRap(){
        switch(rapStatus) {
            case OFF:
                rapPlayer = MediaPlayer.create(this, Uri.parse(currentRap.getPath()));
                rapPlayer.start();
                rapStatus = Status.ON;
                break;
            case ON:
                rapPlayer.pause();
                rapStatus = Status.PAUSE;
                break;
            case PAUSE:
                rapPlayer.start();
                rapStatus = Status.ON;
                break;
        }
    }

    /**
     * Stoppt den Rap, wenn er läuft.
     */
    public void stopRap() {
        switch (rapStatus) {
            case OFF:
                break;
            case ON:
                rapStatus = Status.OFF;
                rapPlayer.stop();
                rapPlayer.release();
                rapPlayer = null;
                break;
            case PAUSE:
                rapStatus = Status.OFF;
                rapPlayer.stop();
                rapPlayer.release();
                rapPlayer = null;
                break;
        }

    }

    /**
     * Gibt die Dauer der Audioaufnahme zurück.
     *
     * @return Dauer der Aufnahme in Millisekunden.
     */
    public int getRapDuration() {
        rapPlayer = MediaPlayer.create(this, Uri.parse(currentRap.getPath()));
        return rapPlayer.getDuration();
    }

    public Rap getRap() {
        return currentRap;
    }

    public void setRap(Rap newRap) {
        currentRap = newRap;
    }

    public Status getBeatStatus() {
        return beatStatus;
    }

    public void setBeatStatus(Status beatStatus) {
        this.beatStatus = beatStatus;
    }

    public Status getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Status recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Status getRapStatus() {
        return rapStatus;
    }

    public void setRapStatus(Status rapStatus) {
        this.rapStatus = rapStatus;
    }

    public class InGameIoBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }

}

