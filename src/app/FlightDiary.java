package app;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FlightDiary {
    private Pilot pilot;
    private List<Flight> flights = new ArrayList<>();
    private TypeOfLicence typeOfLicence;
    private int overallMinutes = 0;
    private int overallTakeoffs = 0;

    public FlightDiary(Pilot pilot, TypeOfLicence typeOfLicence) {
        this.pilot = pilot;
        this.typeOfLicence = typeOfLicence;
        try {
            FileWriter myWriter = new FileWriter(pilot.getName() + ".txt", true);
            myWriter.write("\n" + typeOfLicence);
            myWriter.close();
            FileWriter myWriterDiary = new FileWriter(pilot.getName() + "_" + getType() + ".txt");
            myWriterDiary.write("\n" + getOverallMinutes() + "," + getOverallTakeoffs());
            myWriterDiary.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getType() {
        return typeOfLicence.toString();
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

    public void setOverall(int overallTakeoffs, int overallMinutes) {
        try {
            File myObj = new File(pilot.getName() + "_" + getType() + ".txt");
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
            replaceSelected(data, data2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void replaceSelected(String replaceOld, String replaceWith) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(pilot.getName() + "_" + getType() + ".txt"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace(replaceOld, replaceWith);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(pilot.getName() + "_" + getType() + ".txt");
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
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
