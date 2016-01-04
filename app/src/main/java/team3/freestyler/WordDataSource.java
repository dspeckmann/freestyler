package team3.freestyler;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Ermöglicht das Abrufen von Stichwörtern aus der SQLite-Datenbank.
 */
public class WordDataSource {

    private final String tableName = "word";
    private final String[] columns = { "id", "word", "packageid" };
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public WordDataSource(Context context) {
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
     * Erstellt ein Word-Objekt aus einem Cursor.
     *
     * @param cursor Cursor, der auf einen Datenbankeintrag zeigt.
     * @return Word-Objekt, das den durch den Cursor beschriebenen Datenbankeintrag repräsentiert.
     */
    private Word cursorToWord(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex("word");
        int packageidIndex = cursor.getColumnIndex("packageid");

        int id = cursor.getInt(idIndex);
        int packageid = cursor.getInt(packageidIndex);
        String name = cursor.getString(nameIndex);

        return new Word(id, packageid, name);
    }

    /**
     * Gibt ein Wort aus der Datenbank anhand seiner ID zurück.
     *
     * @param id ID des Datenbankeintrags.
     * @return Word-Objekt, das den Datenbankeintrag repräsentiert.
     */
    public Word getWordById(int id) {
        Cursor cursor = database.query(tableName, columns, "id = " + id, null, null, null, null);
        cursor.moveToFirst();
        return cursorToWord(cursor);
    }

    /**
     * Gibt ein zufälliges Wort aus der Datenbank zurück.
     *
     * @return Word-Objekt, das einen zufälligen Datenbankeintrag repräsentiert.
     */
    public Word getRandomWord() {
        Cursor cursor = database.query(tableName, columns, null, null, null, null, "RANDOM()", "1");
        cursor.moveToFirst();
        return cursorToWord(cursor);
    }

    /**
     * Gibt ein zufälliges Wort aus der Datenbank zurück, das einem aktiven Wortpaket angehört.
     *
     * @return Word-Objekt, das den Datenbankeintrag repräsentiert.
     */
    public Word getRandomWordFromActivePackages() {
        Cursor cursor = database.query(tableName, columns, "packageid IN (SELECT id FROM wordpackage WHERE isactive = 1)", null, null, null, "RANDOM()", "1");
        cursor.moveToFirst();
        return cursorToWord(cursor);
    }

    /**
     * Gibt alle Wörter aus der Datenbank zurück.
     *
     * @return Liste von Word-Objekten, die alle Datenbankeinträge repräsentiert.
     */
    public List<Word> getAllWords() {
        List<Word> wordList = new ArrayList<>();

        Cursor cursor = database.query(tableName, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            wordList.add(word);
            cursor.moveToNext();
        }
        cursor.close();

        return wordList;
    }

    /**
     * Gibt alle Wörter aus der Datenbank zurück, die einem bestimmten Wortpaket angehören.
     *
     * @param wordPackage Wordpaket, auf das gefiltert werden soll.
     * @return Liste von Word-Objekten, die alle Datenbankeinträge des entsprechenden Wortpakets repräsentiert.
     */
    public List<Word> getWordsByPackage(WordPackage wordPackage) {
        List<Word> wordList = new ArrayList<>();

        Cursor cursor = database.query(tableName, columns, "packageid = " + wordPackage.getId(), null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            wordList.add(word);
            cursor.moveToNext();
        }
        cursor.close();

        return wordList;
    }
}