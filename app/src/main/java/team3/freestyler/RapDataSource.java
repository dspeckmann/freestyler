package team3.freestyler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;


/**
 * Ermöglicht das Speichern und Abrufen von Raps in die bzw. aus der SQLite-Datenbank.
 */
public class RapDataSource {

    private final String tableName = "rap";
    private final String[] columns = { "id", "date", "path", "beatid" };

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;

    public RapDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context; // Gut?
    }

    /**
     * Öffnet die Datenbankverbindung.
     */
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Schließt die Datenbankverbindung.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Erstellt einen neuen Rap in der Datenbank und gibt das entsprechende Rap-Objekt zurück.
     *
     * @param path Pfad zur Audioaufnahme.
     * @param beat Beat, der im Hintergrund läuft.
     * @return Rap-Objekt, das den neuen Datenbankeintrag repräsentiert.
     */
    public Rap createRap(String path, Beat beat) {
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        Log.d("[FreeStyler]", "Raw date: " + new Date().toString());
        values.put("path", path);
        values.put("beatid", beat.getId());

        long insertId = database.insert(tableName, null, values);

        Cursor cursor = database.query(tableName, columns, "id = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Rap rap = cursorToRap(cursor);
        cursor.close();

        return rap;
    }

    /**
     * Löscht einen Rap aus der Datenbank.
     *
     * @param rap Rap-Objekt, das den zu löschenden Datenbankeintrag repräsentiert.
     */
    public void deleteRap(Rap rap) {
        int id = rap.getId();
        database.delete("rap", "id = " + id, null);
    }

    /**
     * Erstellt ein Rap-Objekt aus einem Cursor.
     *
     * @param cursor Cursor, der auf einen Datenbankeintrag zeigt.
     * @return Rap-Objekt, das den durch den Cursor beschriebenen Datenbankeintrag repräsentiert.
     */
    private Rap cursorToRap(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int dateIndex = cursor.getColumnIndex("date");
        int pathIndex = cursor.getColumnIndex("path");
        int beatIndex = cursor.getColumnIndex("beatid");

        int id = cursor.getInt(idIndex);
        String path = cursor.getString(pathIndex);
        Date date = new Date(cursor.getLong(dateIndex));

        // Test-Beat anstatt Join
        Beat beat = Beat.getTestBeat(context);

        return new Rap(id, date, path, beat);
    }

    /**
     * Gibt einen Rap aus der Datenbank anhand eines (möglicherweise unvollständigen) Rap-Objekts zurück.
     *
     * @param rap Rap-Objekt, das mindestens eine ID enthalten muss.
     * @return Vollständiges, aktuelles Rap-Objekt aus der Datenbank.
     */
    public Rap getRap(Rap rap) {
        return getRapById(rap.getId());
    }

    /**
     * Gibt einen Rap aus der Datenbank anhand einer ID zurück.
     *
     * @param id ID des Raps in der Datenbank.
     * @return Rap-Objekt, das den Datenbankeintrag repräsentiert.
     */
    public Rap getRapById(int id) {
        Cursor cursor = database.query(tableName, columns, "id = " + id, null, null, null, null);
        cursor.moveToFirst();
        return cursorToRap(cursor);
    }

    /**
     * Gibt alle Raps zurück, die in der Datenbank vorhanden sind.
     *
     * @return Array von Rap-Objekten, das alle Datenbankeinträge repräsentiert.
     */
    public Rap[] getAllRaps() {
        Cursor cursor = database.query(tableName, columns, null, null, null, null, "date DESC");
        cursor.moveToFirst();
        Rap[] raps = new Rap[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            raps[i] = cursorToRap(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return raps;
    }
}