package team3.freestyler;


import android.content.ContentResolver;
import android.content.Context;


/**
 * Dient zum Speichern eines Beats. Diese Klasse stellt lediglich ein Modell dar und enthält keinerlei Logik.
 */
public class Beat {

    private int id;
    private String name;
    private String path;

    /**
     * Erstellt einen neuen Beat.
     *
     * @param id ID des Beats.
     * @param name Name des Beats.
     * @param path Pfad zur Audiodatei.
     */
    public Beat(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    /**
     * Gibt für die Beta-Version einen Beat mit festen Eigenschaften zurück.
     * @param context Context, der für die Auflösung der Ressourcen-URI benötigt wird.
     * @return Beat mit der ID 0 und dem Namen Testbeat.
     */
    public static Beat getTestBeat(Context context) {
        int resId = R.raw.beat01;
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resId) + '/' +
                context.getResources().getResourceTypeName(resId) + '/' +
                context.getResources().getResourceEntryName(resId);
        return new Beat(0, "Testbeat", path);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
