package team3.freestyler;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Sorgt für das Erstellen und Aktualisieren der Datenbank und ermöglicht weiterhin den DataSources das Öffnen dieser.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "FreeStyler", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
        insertWords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 6) {
            if(oldVersion < 5) {
                upgradeToVersion5(db);
                Log.d("[FreeStyler]", "DB-Upgrade von Version " + oldVersion + " auf 5.");
            }
            insertWords(db);
            Log.d("[FreeStyler]", "DB-Upgrade von Version " + oldVersion + " auf 6.");
        }
    }

    private void createDatabase(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE wordpackage ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, isactive INTEGER NOT NULL DEFAULT 0 );");
            db.execSQL("CREATE TABLE word ( id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT NOT NULL, packageid INTEGER NOT NULL, FOREIGN KEY(packageid) REFERENCES wordpackage(id) );");
            db.execSQL("CREATE TABLE beat ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, path TEXT NOT NULL );");
            db.execSQL("CREATE TABLE rap ( id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT NOT NULL, date INTEGER NOT NULL, beatid INTEGER NOT NULL, FOREIGN KEY(beatid) REFERENCES beat(id) );");
        } catch (Exception ex) {
            Log.e("[FreeStyler]", "Fehler beim Erstellen der Datenbank: " + ex.getMessage());
        }
    }

    private void upgradeToVersion5(SQLiteDatabase db) {
        try {
            db.execSQL("ALTER TABLE wordpackage ADD COLUMN isactive INTEGER NOT NULL DEFAULT 0;");
        } catch(Exception ex) {
            Log.e("[FreeStyler]", "Fehler beim Upgraden der Datenbank auf Version 5: " + ex.getMessage());
        }
    }

    // Wörter sind ab Version 6 vorhanden
    // Workaround, Wörter müssen in Zukunft schöner eingefügt werden!
    private void insertWords(SQLiteDatabase db) {
        try {
            // Wort-Pakete:
            db.execSQL("INSERT INTO wordpackage ( id, name, isactive ) VALUES ( 0, 'FreeStyler', 1 );");
            db.execSQL("INSERT INTO wordpackage ( id, name, isactive ) VALUES ( 1, 'Herausforderungen', 0 );");
            db.execSQL("INSERT INTO wordpackage ( id, name, isactive ) VALUES ( 2, 'Jugendwörter', 0 );");
            db.execSQL("INSERT INTO wordpackage ( id, name, isactive ) VALUES ( 3, 'Gangsterrap', 0 );");
            db.execSQL("INSERT INTO wordpackage ( id, name, isactive ) VALUES ( 4, 'Software Engineering', 0 );");
            // FreeStyler:
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'FreeStyler', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Hertstein', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Salatgurken-José', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Yucca-Palme', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Fichte', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Glühwein', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Facepalm', 0 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Pepperoni', 0 )");
            Log.d("[FreeStyler]", "FreeStyler-Wortpaket hinzugefügt.");
            // Herausforderungen:
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Orange', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Pfirsich', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Mensch', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Orgel', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Amsel', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Echo', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Fenchel', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Falsch', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Stöpsel', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Kiosk', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Honig', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Onkel', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Gemälde', 1 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Gehölz', 1 )");
            Log.d("[FreeStyler]", "Herausforderungs-Wortpaket hinzugefügt.");
            // Jugendwörter:
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Läuft bei dir', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Smombie', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Rumoxidieren', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Gönnen', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Fame', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Hater', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'YOLO', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Swag', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Fail', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Niveaulimbo', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Arschfax', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Hartzen', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Gammelfleischparty', 2 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Alpha-Kevin', 2 )");
            Log.d("[FreeStyler]", "Jugendwörter-Wortpaket hinzugefügt.");
            // Gangsterrap:
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Mula', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Cash', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'AK', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Mercedes', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'BMW', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Ehre', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Babo', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Bling', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Hood', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Ghetto', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Gangster', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'G', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Hoe', 3 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Bitch', 3 )");
            Log.d("[FreeStyler]", "Gangsterrap-Wortpaket hinzugefügt.");
            // Software Engineering:
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Spiralmodell', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Kanban', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Scrum', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'GitHub', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Planung', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Design', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Dokumentation', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Implementierung', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Projekt', 4 )");
            db.execSQL("INSERT INTO word ( word, packageid ) VALUES ( 'Wieland', 4 )");
            Log.d("[FreeStyler]", "SWE-Wortpaket hinzugefügt.");
        } catch(Exception ex) {
            Log.e("[FreeStyler]", "Fehler beim Upgraden der Datenbank auf Version 6: " + ex.getMessage());
        }
    }
}
