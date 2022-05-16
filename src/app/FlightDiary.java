package app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightDiary {
    private Pilot pilot;
    private List<Flight> flights = new ArrayList<>();
    private TypeOfLicence typeOfLicence;
    private int overallHours;
    private int overallMinutes;
    private int overallTakeoffs;

    public FlightDiary(Pilot pilot, TypeOfLicence typeOfLicence) {
        this.pilot = pilot;
        this.typeOfLicence = typeOfLicence;
        try {
            FileWriter myWriter = new FileWriter(pilot.getName() + ".txt", true);
            myWriter.write("\n" + typeOfLicence);
            myWriter.close();
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

    public int getOverallHours() {
        return overallHours;
    }

    public int getOverallTakeoffs() {
        return overallTakeoffs;
    }

    public void setOverallTakeoffs(int overallTakeoffs) {
        this.overallTakeoffs = overallTakeoffs;
    }

    public void setOverallMinutes(int overallMinutes) {
        this.overallMinutes = overallMinutes;
    }

    public void setOverallHours(int overallHours) {
        this.overallHours = overallHours;
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
