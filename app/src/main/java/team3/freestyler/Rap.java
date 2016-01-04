package team3.freestyler;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Dient zum Speichern eines Raps. Diese Klasse stellt lediglich ein Modell dar und enthält keinerlei Logik.
 */
public class Rap {

    private int id;
    private String path;
    private Date date;
    private Beat beat;

    /**
     * Erstellt einen neuen Rap.
     *
     * @param id ID des Raps.
     * @param date Datum, an dem der Rap aufgenommen wurde.
     * @param path Pfad zur Audiodatei.
     * @param beat Beat, der im Hintergrund zu Hören ist.
     */
    public Rap(int id, Date date, String path, Beat beat){
        this.id = id;
        this.date = date;
        this.path = path;
        this.beat = beat;
    }

    /**
     * Erstellt einen neuen lokalen Rap (ohne ID und Datum).
     *
     * @param path Pfad zur Audiodatei.
     * @param beat Beat, der im Hintergrund zu Hören ist.
     */
    public Rap(String path, Beat beat) {
        this.id = 0;
        this.date = null;
        this.path = path;
        this.beat = beat;
    }

    /**
     * Gibt einen String zurück, der den Rap mit Hilfe des Aufnahmedatums repräsentiert.
     *
     * @return Das Datum der Aufnahme im Format "01. Januar 00:00"
     */
    public String toString()
    {
        SimpleDateFormat format = new SimpleDateFormat("dd. MMMM HH:mm");
        return format.format(date);
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Beat getBeat() {
        return beat;
    }

    public void setBeat(Beat beat) {
        this.beat = beat;
    }
}
