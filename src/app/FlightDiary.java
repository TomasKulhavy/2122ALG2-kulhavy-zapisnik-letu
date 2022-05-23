package app;

import utils.Tools;

import java.io.*;
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
            if(!exist) {
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

    public String getName() {
        return pilot.getName();
    }

    public int getOverallMinutes() {
        return overallMinutes;
    }

    public int getOverallTakeoffs() {
        return overallTakeoffs;
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

    public String getFlights() {
        StringBuilder str = new StringBuilder();
        for (Flight flight : flights) {
            str.append("\n")
                    .append(flight.getTakeoff())
                    .append(flight.getLanding())
                    .append(flight.getTakeoffTime())
                    .append(flight.getLandingTime())
                    .append(flight.getTakeoffNo())
                    .append(flight.getPilot())
                    .append(flight.getPlane());
        }
        return str.toString();
    }
}
