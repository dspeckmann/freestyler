package me.speckmann.danny;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;


/**
 * Diese Activity enthält das Hauptmenü, von wo aus der User sämtliche Funktionen der App erreichen kann.
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void buttonRapOnClick(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void buttonFeedbackOnClick(View v) {
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public void buttonListOfRapsOnClick(View v) {
        Intent intent = new Intent(this, ListOfRapsActivity.class);
        startActivity(intent);
    }

    public void buttonWordPackagesOnClick(View v) {
        Intent intent = new Intent(this, WordPackagesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}

