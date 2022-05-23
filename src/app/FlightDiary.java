package app;

import utils.Tools;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FlightDiary {
    private Pilot pilot;
    private List<Flight> flights = new ArrayList<>();
    private List<FlightDiary> diaries = new ArrayList<>();
    private TypeOfLicence typeOfLicence;
    private int overallMinutes = 0;
    private int overallTakeoffs = 0;
    boolean exist = false;

    public FlightDiary(Pilot pilot, TypeOfLicence typeOfLicence) {
        this.pilot = pilot;
        this.typeOfLicence = typeOfLicence;
        try {
            FileWriter myWriter = new FileWriter(pilot.getName().toLowerCase(Locale.ROOT) + ".profile", true);
            myWriter.write("\n" + typeOfLicence);
            myWriter.close();

            File file2 = new File(pilot.getName().toLowerCase(Locale.ROOT) + "." + getType());
            exist = file2.exists();
            if (!exist) {
                FileWriter myWriterDiary = new FileWriter(pilot.getName().toLowerCase(Locale.ROOT) + "." + getType());
                myWriterDiary.write("\n" + getOverallMinutes() + "," + getOverallTakeoffs());
                myWriterDiary.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public FlightDiary(Pilot pilot, TypeOfLicence typeOfLicence, boolean exist) {
        this.pilot = pilot;
        this.typeOfLicence = typeOfLicence;
    }

    public TypeOfLicence getType() {
        return typeOfLicence;
    }

    public int getOverallMinutes() {
        return overallMinutes;
    }

    public int getOverallTakeoffs() {
        return overallTakeoffs;
    }

    public List<FlightDiary> getDiaries() throws FileNotFoundException {
        diaries.removeAll(diaries);
        File myObj = new File(pilot.getName() + ".profile");
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        myReader.nextLine();
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            FlightDiary diary = new FlightDiary(pilot, TypeOfLicence.findByLicence(data), true);
            diaries.add(diary);
        }
        return diaries;
    }

    public void setOverall(int overallTakeoffs, int overallMinutes, TypeOfLicence typeOfLicence) {
        try {
            File myObj = new File(pilot.getName() + "." + typeOfLicence);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
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
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getFlightsAndMinutes() throws FileNotFoundException {
        System.out.println("Typ licence: " + typeOfLicence);
        File myObj = new File(pilot.getName() + "." + typeOfLicence);
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();
        String data = myReader.nextLine();
        String[] line = data.split(",");
        overallMinutes = Integer.parseInt(line[0]);
        overallTakeoffs = Integer.parseInt(line[1]);
        return String.format("Celkem: %s hodin a %sx startu\n", Tools.getTotalTime(overallMinutes), overallTakeoffs);
    }

    public List<Flight> getFlights() {
        try {
            File myObj = new File(pilot.getName() + "." + typeOfLicence);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String dataFlight = myReader.nextLine();
                String[] lineFlight = dataFlight.split(", ");
                Plane plane = new Plane(lineFlight[0], TypeOfLicence.findByLicence(lineFlight[11]), lineFlight[1], true);
                LocalDate date = LocalDate.parse(lineFlight[4]);
                LocalDateTime takeoffTime = Tools.parseTime(lineFlight[5], date);
                LocalDateTime landingTime = Tools.parseTime(lineFlight[6], date);
                FlightDiary diary = new FlightDiary(pilot, typeOfLicence, true);
                Flight flight = new Flight(plane, lineFlight[2], lineFlight[3], date, takeoffTime, landingTime, Integer.parseInt(lineFlight[7]), Integer.parseInt(lineFlight[8]), lineFlight[9], pilot, diary, true);
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
