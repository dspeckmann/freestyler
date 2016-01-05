package me.speckmann.danny;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Dient zum Speichern und Abrufen von Beats in die bzw. aus der SQLite-Datenbank. Wird aktuell noch nicht genutzt.
 */
public class BeatDataSource {

    private final String tableName = "beat";
    private final String[] columns = { "id", "name", "path" };
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BeatDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
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
}