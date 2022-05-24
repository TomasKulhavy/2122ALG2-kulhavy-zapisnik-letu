package app;

import utils.Tools;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Plane {
    private String name;
    private TypeOfLicence typeOfLicence;
    private String registration;
    private int takeoffNo;
    private int flightTimeMinutes;
    private List<Plane> planes = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();

    public Plane(String registration) {
        this.registration = registration;
    }

    public Plane(String name, TypeOfLicence typeOfLicence, String registration, boolean exist) {
        this.name = name;
        this.typeOfLicence = typeOfLicence;
        this.registration = registration;
        this.flightTimeMinutes = 0;
        this.takeoffNo = 0;
        if(!exist) saveFlightToFile();
    }

    public void saveFlightToFile() {
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

    public TypeOfLicence getTypeOfLicence() {
        return typeOfLicence;
    }

    public String getRegistration() {
        return registration;
    }

    public void setFlightTimeMinutes(int flightTimeMinutes) {
        this.flightTimeMinutes = flightTimeMinutes;
    }

    public void setTakeoffNo(int takeoffNo) {
        this.takeoffNo = takeoffNo;
    }

    public void setOverallPlane() {
        try {
            File myObj = new File(getRegistration() + ".plane");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            String data = myReader.nextLine();
            String[] line = data.split(", ");
            int temp = Integer.parseInt(line[3]);
            temp += flightTimeMinutes;
            line[3] = String.valueOf(temp);
            int temp2 = Integer.parseInt(line[4]);
            temp2 += takeoffNo;
            line[4] = String.valueOf(temp2);
            String data2 = line[0] + ", " + line[1] + ", " + line[2] + ", " + line[3] + ", " + line[4];
            myReader.close();
            Tools.replaceSelected(myObj, data, data2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getFlightsAndMinutes() throws FileNotFoundException {
        System.out.format("%-15s%-15s%n", "Letadlo: ", getName());
        System.out.format("%-15s%-15s%n", "Registrace: ", getRegistration());
        System.out.format("%-15s%-15s%n", "Typ letadla: ", getTypeOfLicence());

        File myObj = new File(getRegistration() + ".plane");
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        String data = myReader.nextLine();
        String[] line = data.split(", ");
        flightTimeMinutes = Integer.parseInt(line[3]);
        takeoffNo = Integer.parseInt(line[4]);
        return String.format("Celkem: %s hodin a %sx startu\n", Tools.getTotalTime(flightTimeMinutes), takeoffNo);
    }

    public List<Flight> getFlights() {
        try {
            File myObj = new File(getRegistration() + ".plane");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String dataFlight = myReader.nextLine();
                String[] lineFlight = dataFlight.split(", ");
                Plane planeSelect = new Plane(name, typeOfLicence, registration, true);
                LocalDate date = LocalDate.parse(lineFlight[2]);
                LocalDateTime takeoffTime = Tools.parseTime(lineFlight[3], date);
                LocalDateTime landingTime = Tools.parseTime(lineFlight[4], date);
                String[] tempName;
                if(Tools.isGlider(typeOfLicence)) {
                    tempName = lineFlight[9].split("_");
                } else {
                    tempName = lineFlight[8].split("_");
                }
                Pilot pilot = new Pilot(tempName[0], tempName[1], true);
                FlightDiary diary = new FlightDiary(pilot, planeSelect.getTypeOfLicence(), true);
                Flight flight;
                if(Tools.isGlider(planeSelect.getTypeOfLicence())) {
                    flight = new Flight(planeSelect, lineFlight[0], lineFlight[1], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[5]), Integer.parseInt(lineFlight[6]),lineFlight[7], lineFlight[8], pilot, diary, true);
                } else {
                    flight = new Flight(planeSelect, lineFlight[0], lineFlight[1], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[5]), Integer.parseInt(lineFlight[6]), lineFlight[7], pilot, diary, true);
                }
                flights.add(flight);
                if (myReader.hasNextLine()) myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return flights;
    }

    public void addPlane(Plane plane) {
        planes.add(plane);
    }

    public List<Plane> getPlanes() {
        return planes;
    }

    public List<Plane> loadAllPlanes() throws FileNotFoundException {
        planes.removeAll(planes);
        File dir = new File(".");
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".plane"));

        for (File xmlfile : Objects.requireNonNull(files)) {
            File file = new File(xmlfile.getName());
            Scanner sc = new Scanner(file);
            sc.nextLine();
            String data = sc.nextLine();
            String[] list = data.split(", ");
            String name = list[0];
            TypeOfLicence licence = TypeOfLicence.findByLicence(list[1]);
            String registration = list[2];
            Plane plane = new Plane(name, licence, registration, true);
            addPlane(plane);
        }
        return getPlanes();
    }

    public ArrayList<Flight> sortByDateDesc() {
        ArrayList<Flight> list = new ArrayList<>(flights);
        Comparator<Flight> flight = new Flight();
        list.sort(flight);
        return list;
    }

    public ArrayList<Flight> sortByDateAsc() {
        ArrayList<Flight> list = new ArrayList<>(flights);
        Comparator<Flight> flight = new Flight();
        list.sort(flight);
        Collections.reverse(list);
        return list;
    }
}
