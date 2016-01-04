package team3.freestyler;


import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


/**
 * Diese Activity ermöglicht des dem User, die aktiven Wortpakete auszuwählen.
 */
public class WordPackagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordpackages);

        WordPackageDataSource wds = new WordPackageDataSource(this);
        wds.open();
        WordPackage[] items = wds.getAllWordPackages();
        wds.close();
        final WordPackageAdapter adapter = new WordPackageAdapter(this, items);
        ListView listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(adapter);
    }
}
