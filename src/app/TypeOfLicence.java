package app;

import java.util.HashMap;
import java.util.Map;

public enum TypeOfLicence {
    SPL(0),
    ULL(1),
    PPL(2);

    private int value;
    private static Map map = new HashMap<>();

    private TypeOfLicence(int value) {
        this.value = value;
    }

    static {
        for (TypeOfLicence licence : TypeOfLicence.values()) {
            map.put(licence.value, licence);
        }
    }

    public static TypeOfLicence valueOf(int typeOfLicence) {
        return (TypeOfLicence) map.get(typeOfLicence);
    }

    public int getValue() {
        return value;
    }
}
