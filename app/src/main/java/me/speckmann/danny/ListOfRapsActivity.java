package me.speckmann.danny;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


/**
 * Diese Activity ermöglicht dem User das Einsehen, Anhören und Löschen von aufgenommenen Raps.
 */
public class ListOfRapsActivity extends AppCompatActivity {

    private AudioService audioService;
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
        setContentView(R.layout.activity_listofraps);

        RapDataSource rds = new RapDataSource(this);
        rds.open();
        Rap[] items = rds.getAllRaps();
        rds.close();
        RapAdapter adapter = new RapAdapter(this, items);

        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = new Intent(this, AudioService.class);
        startService(i);
        bindService(i, audioServiceConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Intent i = new Intent(this, AudioService.class);
        unbindService(audioServiceConnection);
        stopService(i);
    }
}
