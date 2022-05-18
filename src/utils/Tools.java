package utils;

import app.Plane;

import java.util.List;

public class Tools {

    public Tools() {
    }

    public static void printPlanes(List<Plane> planeList) {
        for (int i = 0; i < planeList.size(); i++) {
            System.out.println(i + 1 + "." + " - " + planeList.get(i).getRegistration());
        }
    }
}
