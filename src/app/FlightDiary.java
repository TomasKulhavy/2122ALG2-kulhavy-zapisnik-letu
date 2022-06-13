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
 * Třída, která zápisník letů
 *
 * @author Tomáš Kulhavý
 */
public class FlightDiary {
    boolean exist = false;
    private Pilot pilot;
    private List<Flight> flights = new ArrayList<>();
    private TypeOfLicence typeOfLicence;
    private int overallMinutes = 0;
    private int overallTakeoffs = 0;
    private IInputValid inputValid = new InputValid();

    /**
     * Konstruktor
     *
     * @param pilot         Pilot object
     * @param typeOfLicence Typ licence
     * @param exist         Existuje už?
     */
    public FlightDiary(Pilot pilot, TypeOfLicence typeOfLicence, boolean exist) throws IOException {
        this.pilot = pilot;
        this.typeOfLicence = typeOfLicence;
        if (!exist) saveToFile();
    }

    /**
     * Uloží typ licence to souboru pilota
     */
    public void saveToFile() throws IOException {
        List<TypeOfLicence> licences = new ArrayList<>();
        licences.add(TypeOfLicence.SPL);
        licences.add(TypeOfLicence.ULL);
        licences.add(TypeOfLicence.PPL);
        boolean existFile = false;
        for (int i = 0; i < licences.size(); i++) {
            File fileFind = new File("data/exported-data/" + pilot.getName().toLowerCase(Locale.ROOT) + "." + typeOfLicence);
            existFile = fileFind.exists();
        }
        if (!existFile) {
            FileWriter myWriter = new FileWriter("data/exported-data/" + pilot.getName().toLowerCase(Locale.ROOT) + ".profile", true);
            myWriter.write("\n" + typeOfLicence);
            myWriter.close();

            File file2 = new File("data/exported-data/" + pilot.getName().toLowerCase(Locale.ROOT) + "." + getType());
            exist = file2.exists();
            if (!exist) {
                FileWriter myWriterDiary = new FileWriter("data/exported-data/" + pilot.getName().toLowerCase(Locale.ROOT) + "." + getType());
                myWriterDiary.write(getOverallMinutes() + "," + getOverallTakeoffs() + "\n");
                myWriterDiary.close();
            }
        } else {
            System.out.println("Tuto licenci jiz mate.");
        }
    }

    /**
     * Vrátí typ licence
     *
     * @return Typ licence
     */
    public TypeOfLicence getType() {
        return typeOfLicence;
    }

    /**
     * Vrátí celkový nálet v minutách
     *
     * @return Celkový nálet v minutách
     */
    public int getOverallMinutes() {
        return overallMinutes;
    }

    /**
     * Vrátí celkový počet startů
     *
     * @return Celkový počet startů
     */
    public int getOverallTakeoffs() {
        return overallTakeoffs;
    }

    /**
     * Metoda aktualizuje součty celkového náletu
     *
     * @param overallTakeoffs Celkové vzlety
     * @param overallMinutes  Celkové minuty
     * @param typeOfLicence   Typ licence
     */
    public void setOverall(int overallTakeoffs, int overallMinutes, TypeOfLicence typeOfLicence) throws FileNotFoundException {
        File myObj = new File("data/exported-data/" + pilot.getName() + "." + typeOfLicence);
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        String[] line = data.split(",");
        int temp = Integer.parseInt(line[0]);
        temp += overallMinutes;
        line[0] = String.valueOf(temp);
        int temp2 = Integer.parseInt(line[1]);
        temp2 += overallTakeoffs;
        line[1] = String.valueOf(temp2);
        String data2 = line[0] + "," + line[1];
        myReader.close();
        Tools.replaceSelected(myObj, data, data2);
    }

    /**
     * Metoda, která vrátí celkový nálet hodin a minut ve Stringu
     *
     * @return String naformátovaný, který vrátí celkový nálet hodin a vzletů
     * @throws FileNotFoundException Soubor nenalezen
     */
    public String getFlightsAndMinutes() throws FileNotFoundException {
        System.out.println("Typ licence: " + typeOfLicence);
        File myObj = new File("data/exported-data/" + pilot.getName() + "." + typeOfLicence);
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        String[] line = data.split(",");
        overallMinutes = Integer.parseInt(line[0]);
        overallTakeoffs = Integer.parseInt(line[1]);
        return String.format("Celkem: %s hodin a %sx startu\n", Tools.getTotalTime(overallMinutes), overallTakeoffs);
    }

    /**
     * Metoda, která vrátí list letů
     *
     * @return List letů z daného zápisníku letů
     */
    public List<Flight> getFlights() throws IOException {
        File myObj = new File("data/exported-data/" + pilot.getName() + "." + typeOfLicence);
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        while (myReader.hasNextLine()) {
            String dataFlight = myReader.nextLine();
            String[] lineFlight = dataFlight.split(", ");
            Plane plane;
            plane = new Plane(lineFlight[0], typeOfLicence, lineFlight[1], true);
            LocalDate date = LocalDate.parse(lineFlight[4]);
            LocalDateTime takeoffTime = inputValid.parseTimeInput(lineFlight[5], date);
            LocalDateTime landingTime = inputValid.parseTimeInput(lineFlight[6], date);
            FlightDiary diary = new FlightDiary(pilot, typeOfLicence, true);
            Flight flight;
            if (Tools.isGlider(diary.getType())) {
                flight = new Flight(plane, lineFlight[2], lineFlight[3], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[8]), Integer.parseInt(lineFlight[9]), lineFlight[7], lineFlight[10], pilot, diary, true);
            } else {
                flight = new Flight(plane, lineFlight[2], lineFlight[3], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[7]), Integer.parseInt(lineFlight[8]), lineFlight[9], pilot, diary, true);
            }
            flights.add(flight);
        }
        myReader.close();

        return flights;
    }

    /**
     * Metoda, která vrátí list seřazený sestupně
     *
     * @return List seřazený sestupně
     */
    public ArrayList<Flight> sortByDateDesc() {
        ArrayList<Flight> list = new ArrayList<>(flights);
        ComparatorByDate flight = new ComparatorByDate();
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
        ComparatorByDate flight = new ComparatorByDate();
        list.sort(flight);
        Collections.reverse(list);
        return list;
    }
}
