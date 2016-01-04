package team3.freestyler;


/**
 * Dient zum Speichern eines Wortes. Diese Klasse stellt lediglich ein Modell dar und enthält keinerlei Logik.
 */
public class Word {

    private int id;
    private int packageId;
    private String word;

    /**
     * Erstellt ein neues Wort.
     *
     * @param id ID des Wortes.
     * @param packageId Paket, dem das Wort zugeordnet ist.
     * @param word Das eigentliche Wort, das angezeigt wird.
     */
    public Word(int id, int packageId, String word) {
        this.id = id;
        this.packageId = packageId;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
