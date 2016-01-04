package team3.freestyler;


/**
 * Dient zum Speichern eines Wortpaketes. Diese Klasse stellt lediglich ein Modell dar und enthält keinerlei Logik.
 */
public class WordPackage {

    private int id;
    private String name;
    private boolean isActive;

    /**
     * Erstellt ein neues Wortpaket.
     *
     * @param id ID des Pakets.
     * @param name Name des Pakets.
     * @param isActive Bestimmt, ob die Wörter des Pakets im Spiel vorkommen.
     */
    public WordPackage(int id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
