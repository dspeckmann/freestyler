package team3.freestyler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Ermöglicht das Abrufen und Manipulieren von Wortpaketen in der SQLite-Datenbank.
 */
public class WordPackageDataSource {

    private final String tableName = "wordpackage";
    private final String[] columns = { "id", "name", "isactive" };
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public WordPackageDataSource(Context context) {
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

    /**
     * Gibt ein Wortpaket aus der Datenbank anhand seiner ID zurück.
     *
     * @param id ID des Datenbankeintrags.
     * @return WordPackage-Objekt, dass den Datenbankeintrag repräsentiert.
     */
    public WordPackage getWordPackage(int id) {
        Cursor cursor = database.query(tableName, columns, "id = " + id, null, null, null, null);
        cursor.moveToFirst();
        return cursorToWordPackage(cursor);
    }

    /**
     * Erstellt ein WordPackage-Objekt aus einem Cursor.
     *
     * @param cursor Cursor, der auf einen Datenbankeintrag zeigt.
     * @return WordPackage-Objekt, das den durch den Cursor beschriebenen Datenbankeintrag repräsentiert.
     */
    private WordPackage cursorToWordPackage(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex("name");
        int isActiveIndex = cursor.getColumnIndex("isactive");

        int id = cursor.getInt(idIndex);
        String name = cursor.getString(nameIndex);
        boolean isActive = cursor.getInt(isActiveIndex) != 0; // int in boolean konvertieren

        return new WordPackage(id, name, isActive);
    }

    /**
     * Gibt alle Wortpakete aus der Datenbank zurück.
     *
     * @return Array von WordPackage-Objekten, das alle Datenbankeinträge repräsentiert.
     */
    public WordPackage[] getAllWordPackages() {
        Cursor cursor = database.query(tableName, columns, null, null, null, null, null);
        cursor.moveToFirst();
        WordPackage[] packages = new WordPackage[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            packages[i] = cursorToWordPackage(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return packages;
    }

    /**
     * Gibt alle aktiven Wortpakete aus der Datenbank zurück.
     *
     * @return Liste von WordPackage-Objekten, die alle Datenbankeinträge repräsentiert, die aktiv sind.
     */
    public List<WordPackage> getActiveWordPackages() {
        List<WordPackage> wordPackageList = new ArrayList<>();

        Cursor cursor = database.query(tableName, columns, "isactive = 1", null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            WordPackage wordPackage = cursorToWordPackage(cursor);
            wordPackageList.add(wordPackage);
            cursor.moveToNext();
        }
        cursor.close();

        return wordPackageList;
    }

    /**
     * Ändert die Aktivität eines Wortpakets in der Datenbank.
     *
     * @param wordPackage WortPackage-Objekt, das den Datenbankeintrag repräsentiert, der verändert werden soll.
     */
    public void toggleWordPackageIsActive(WordPackage wordPackage) {
        ContentValues values = new ContentValues();
        values.put("isactive", (wordPackage.getIsActive() ? 0 : 1));

        database.update(tableName, values, "id = " + wordPackage.getId(), null);
    }
}