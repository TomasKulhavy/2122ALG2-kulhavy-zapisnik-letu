package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Plane {
    private String name;
    private TypeOfLicence typeOfLicence;
    private String registration;
    private int takeoffNo;
    private int flightTimeMinutes;
    private List<Plane> planes = new ArrayList<>();

    public Plane(String name, TypeOfLicence typeOfLicence, String registration) {
        this.name = name;
        this.typeOfLicence = typeOfLicence;
        this.registration = registration;
        this.flightTimeMinutes = 0;
        this.takeoffNo = 0;
        try {
            FileWriter myWriter = new FileWriter("plane_" + registration + ".txt");
            myWriter.write("\n" + name + ", " + typeOfLicence + ", " + registration + ", " + flightTimeMinutes + ", " + takeoffNo);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfLicence getTypeOfLicence() {
        return typeOfLicence;
    }

    public void setTypeOfLicence(TypeOfLicence typeOfLicence) {
        this.typeOfLicence = typeOfLicence;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getFlightTimeMinutes() {
        return flightTimeMinutes;
    }

    public void setFlightTimeMinutes(int flightTimeMinutes) {
        this.flightTimeMinutes = flightTimeMinutes;
    }

    public int getTakeoffNo() {
        return takeoffNo;
    }

    public void setTakeoffNo(int takeoffNo) {
        this.takeoffNo = takeoffNo;
    }
}
