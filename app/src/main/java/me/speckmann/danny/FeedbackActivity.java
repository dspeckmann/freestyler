package me.speckmann.danny;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


/**
 * Diese Activity ermöglicht dem User das Absenden von Feedback in Form einer E-Mail oder Umfrage.
 */
public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void buttonSurveyOnClick(View v) {
        openSurvey("https://docs.google.com/forms/d/1OOHnDco1JXZ0ZX2pzCiNn-QGWxqOEW7tCVyhUwlFjMw/viewform?usp=send_form");
    }

    public void buttonSendFeedbackOnClick(View v) {
        sendBug("");
    }

    /**
     * Öffnet den Mail-Client.
     *
     * @param bugDescription Text, der im Körper der E-Mail stehen soll.
     */
    private void sendBug(String bugDescription) {
        Resources res = getResources();

        String[] TO = { "swefreestyler@gmail.com" };
        String subject = res.getString(R.string.mailSubject);
        Intent emailIntent = new Intent(Intent.ACTION_VIEW);

        emailIntent.setData(Uri.parse("mailto:?subject=" + "" + "&body=" + "" + "&to=" + ""));
        emailIntent.putExtra(Intent.EXTRA_TEXT, bugDescription);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        try {
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(emailIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            String s = res.getString(R.string.error_missing_email_client);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Öffnet einen Link (für eine Umfrage) im Browser.
     *
     * @param url URL, die geöffnet werden soll.
     */
    private void openSurvey(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        launchBrowser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(launchBrowser);
    }
}
