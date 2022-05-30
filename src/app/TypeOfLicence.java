package app;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum licencí
 *
 * @author Tomáš Kulhavý
 */
public enum TypeOfLicence {
    SPL(0),
    ULL(1),
    PPL(2);

    private static Map map = new HashMap<>();

    static {
        for (TypeOfLicence licence : TypeOfLicence.values()) {
            map.put(licence.value, licence);
        }
    }

    private int value;

    TypeOfLicence(int value) {
        this.value = value;
    }

    /**
     * Metodá, která nám vrátí TypeOfLicence
     *
     * @param typeOfLicence int hodnota licence
     * @return TypeOfLicence
     */
    public static TypeOfLicence valueOf(int typeOfLicence) {
        return (TypeOfLicence) map.get(typeOfLicence);
    }

    /**
     * Metoda, která nám vrátí licenci podle názvu
     *
     * @param name Název licence
     * @return Název licence podle názvu
     */
    public static TypeOfLicence findByLicence(String name) {
        for (TypeOfLicence licence : values()) {
            if (licence.name().equals(name)) {
                return licence;
            }
        }
        return null;
    }
}
