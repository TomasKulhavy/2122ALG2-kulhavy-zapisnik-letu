package app;

import utils.IInputValid;
import utils.InputValid;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Třída, která uchovává letadlo
 *
 * @author Tomáš Kulhavý
 */
public class Plane extends Flight {
    private String name;
    private TypeOfLicence typeOfLicence;
    private String registration;
    private int takeoffNo;
    private int flightTimeMinutes;
    private List<Plane> planes = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private IInputValid inputValid = new InputValid();


    /**
     * Kontruktor pouze pro inicializaci na startu programu
     *
     * @param registration registrace letadla
     */
    public Plane(String registration) {
        this.registration = registration;
    }

    /**
     * Kontruktor pro vytvoření letadla
     *
     * @param name          Jméno letadla
     * @param typeOfLicence Typ licence letadla
     * @param registration  Registrace letadla
     * @param exist         Existuje již toto letadlo?
     */
    public Plane(String name, TypeOfLicence typeOfLicence, String registration, boolean exist) {
        this.name = name;
        this.typeOfLicence = typeOfLicence;
        this.registration = registration;
        this.flightTimeMinutes = 0;
        this.takeoffNo = 0;
        if (!exist) savePlaneToFile();
    }


    /**
     * Metoda, která uloží let do zápisníku letadla
     */
    public void savePlaneToFile() {
        try {
            FileWriter myWriter = new FileWriter("data/exported-data/" + registration + ".plane");
            myWriter.write(name + ", " + typeOfLicence + ", " + registration + ", " + flightTimeMinutes + ", " + takeoffNo);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Vrátí název letadla
     *
     * @return Název letadla
     */
    public String getName() {
        return name;
    }

    /**
     * Vrátí typ licence letadla
     *
     * @return Typ licence letadla
     */
    public TypeOfLicence getTypeOfLicence() {
        return typeOfLicence;
    }

    /**
     * Vrátí registraci letadla
     *
     * @return Registrace letadla
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Nastaví dobu letu v minutách
     *
     * @param flightTimeMinutes Doba letu v minutách
     */
    public void setFlightTimeMinutes(int flightTimeMinutes) {
        this.flightTimeMinutes = flightTimeMinutes;
    }

    /**
     * Nastaví počet vzletů
     *
     * @param takeoffNo Počet vzletů
     */
    public void setTakeoffNo(int takeoffNo) {
        this.takeoffNo = takeoffNo;
    }


    /**
     * Metoda, která aktualizuje celkový nálet letadla v textovém souboru
     */
    public void setOverallPlane() {
        try {
            File myObj = new File("data/exported-data/" + getRegistration() + ".plane");
            Scanner myReader = new Scanner(myObj);
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

    /**
     * Metoda, která vrátí celkový nálet hodin a minut ve Stringu
     *
     * @return String naformátovaný, který vrátí celkový nálet hodin a vzletů
     * @throws FileNotFoundException Soubor nenalezen
     */
    public String getFlightsAndMinutes() throws FileNotFoundException {
        System.out.format("%-15s%-15s%n", "Letadlo: ", getName());
        System.out.format("%-15s%-15s%n", "Registrace: ", getRegistration());
        System.out.format("%-15s%-15s%n", "Typ letadla: ", getTypeOfLicence());
        File myObj = new File("data/exported-data/" + getRegistration() + ".plane");
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        String[] line = data.split(", ");
        flightTimeMinutes = Integer.parseInt(line[3]);
        takeoffNo = Integer.parseInt(line[4]);
        return String.format("Celkem: %s hodin a %sx startu\n", Tools.getTotalTime(flightTimeMinutes), takeoffNo);
    }

    /**
     * Metoda, která vrátí list letů
     *
     * @return List letů z daného zápisníku letadla
     */
    public List<Flight> getFlights() {
        try {
            File myObj = new File("data/exported-data/" + getRegistration() + ".plane");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String dataFlight = myReader.nextLine();
                String[] lineFlight = dataFlight.split(", ");
                Plane planeSelect = new Plane(name, typeOfLicence, registration, true);
                LocalDate date = LocalDate.parse(lineFlight[2]);
                LocalDateTime takeoffTime = inputValid.parseTimeInput(lineFlight[3], date);
                LocalDateTime landingTime = inputValid.parseTimeInput(lineFlight[4], date);
                String[] tempName;
                if (Tools.isGlider(typeOfLicence)) {
                    tempName = lineFlight[9].split("_");
                } else {
                    tempName = lineFlight[8].split("_");
                }
                Pilot pilot = new Pilot(tempName[0], tempName[1], true);
                FlightDiary diary = new FlightDiary(pilot, planeSelect.getTypeOfLicence(), true);
                Flight flight;
                if (Tools.isGlider(planeSelect.getTypeOfLicence())) {
                    flight = new Flight(planeSelect, lineFlight[0], lineFlight[1], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[6]), Integer.parseInt(lineFlight[7]), lineFlight[5], lineFlight[8], pilot, diary, true);
                } else {
                    flight = new Flight(planeSelect, lineFlight[0], lineFlight[1], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[5]), Integer.parseInt(lineFlight[6]), lineFlight[7], pilot, diary, true);
                }
                flights.add(flight);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return flights;
    }


    /**
     * Metoda, která přidá letadlo
     *
     * @param plane Objekt letadla
     */
    public void addPlane(Plane plane) {
        planes.add(plane);
    }

    /**
     * Vrátí list letadel
     *
     * @return List letadel
     */
    public List<Plane> getPlanes() {
        return planes;
    }

    /**
     * Metoda, která načte všechna letadla a jejich hodnoty ze souboru
     *
     * @return List letadel s jejich hodnotami ze souborů
     * @throws FileNotFoundException Soubor nenalezen
     */
    public List<Plane> loadAllPlanes() throws FileNotFoundException {
        planes.removeAll(planes);
        File dir = new File("data/exported-data/");
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".plane"));

        for (File xmlfile : Objects.requireNonNull(files)) {
            File file = new File("data/exported-data/" + xmlfile.getName());
            Scanner sc = new Scanner(file);
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

    /**
     * Metoda, která vrátí list seřazený sestupně
     *
     * @return List seřazený sestupně
     */
    public ArrayList<Flight> sortByDateDesc() {
        ArrayList<Flight> list = new ArrayList<>(flights);
        Comparator<Flight> flight = new Flight();
        list.sort(flight);
        return list;
    }

    /**
     * Metoda, která vrátí list seřazený vzestupně
     *
     * @return List seřazený vzestupně
     */
    public ArrayList<Flight> sortByDateAsc() {
        ArrayList<Flight> list = new ArrayList<>(flights);
        Comparator<Flight> flight = new Flight();
        list.sort(flight);
        Collections.reverse(list);
        return list;
    }
}
