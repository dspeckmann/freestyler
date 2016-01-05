package me.speckmann.danny;


import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


/**
 * Dieser ArrayAdapter bindet ein Array von Raps an die entsprechende ListView der Activity.
 */
public class RapAdapter extends ArrayAdapter <Rap>{
    Rap[] modelItems = null;
    Context context;
    public RapAdapter(Context context, Rap[] resource) {
        super(context,R.layout.row_rap,resource);
        this.context = context;
        this.modelItems = resource;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_rap, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        name.setText(modelItems[position].toString());
        Button delete = (Button) convertView.findViewById(R.id.button1);

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file = new File(modelItems[position].getPath());
                file.delete();
                Log.d("[FreeStyler]", "Rap wurde erfolgreich aus dem Dateisystem gelöscht.");
                RapDataSource rapDataSource = new RapDataSource(v.getContext());
                rapDataSource.open();
                rapDataSource.deleteRap(modelItems[position]);
                rapDataSource.close();
                Log.d("[FreeStyler]", "Rap wurde erfolgreich aus der Datenbank gelöscht.");
                ListView listView = (ListView)(v.getParent().getParent());
                listView.removeViewInLayout((View)v.getParent()); // Rap wird aus Liste entfernt, aber es entsteht eine Lücke.
                Toast.makeText(context, "Rap has been removed.", Toast.LENGTH_SHORT).show();
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nutzung von AudioService möglich?
                MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(modelItems[position].getPath()));
                mediaPlayer.start();
            }
        });

        return convertView;
    }
}

