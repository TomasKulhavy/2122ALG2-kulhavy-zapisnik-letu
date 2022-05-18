package app;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Plane {
    private String name;
    private TypeOfLicence typeOfLicence;
    private String registration;
    private int takeoffNo;
    private int flightTimeMinutes;
    private List<Plane> planes = new ArrayList<>();

    public Plane(String registration) {
        this.registration = registration;
    }

    public Plane(String name, TypeOfLicence typeOfLicence, String registration) {
        this.name = name;
        this.typeOfLicence = typeOfLicence;
        this.registration = registration;
        this.flightTimeMinutes = 0;
        this.takeoffNo = 0;
        try {
            FileWriter myWriter = new FileWriter(registration + ".plane");
            myWriter.write("\n" + name + ", " + typeOfLicence + ", " + registration + ", " + flightTimeMinutes + ", " + takeoffNo);
            myWriter.close();
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
    public void addPlane(Plane plane) {
        planes.add(plane);
    }
    public List<Plane> getPlanes() {
        return planes;
    }
    public List<Plane> loadAllPlanes() throws FileNotFoundException {

        File dir = new File(".");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".plane");
            }
        });

        for (File xmlfile : files) {
            File file = new File(xmlfile.getName());
            Scanner sc = new Scanner(file);
            sc.nextLine();
            String data = sc.nextLine();
            String[] list = data.split(", ");
            String name = list[0];
            TypeOfLicence licence = TypeOfLicence.findByLicence(list[1]);
            String registration = list[2];
            Plane plane = new Plane(name, licence, registration);
            addPlane(plane);
        }

        return getPlanes();
    }
}
