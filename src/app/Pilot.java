package app;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pilot {
    private String firstName;
    private String lastName;
    private List<FlightDiary> diary = new ArrayList<>();

    public Pilot(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            FileWriter myWriter = new FileWriter(firstName.toLowerCase(Locale.ROOT) + "_" + lastName.toLowerCase(Locale.ROOT) + ".txt");
            myWriter.write("\n" + firstName + ", " + lastName);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Pilot(String firstName, String lastName, File logbook) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + "_" + lastName;
    }

    public String getDiaries() {
        StringBuilder str = new StringBuilder();
        for (FlightDiary flightDiary : diary) {
            str.append("\n").append(flightDiary.getType());
        }
        return str.toString();
    }

    public void addDiary(FlightDiary flightDiary) {
        for (FlightDiary value : diary) {
            if (value.getType().equals(flightDiary.getType())) {
                break;
            }
        }
        diary.add(flightDiary);
    }

    //@Override
    //public String toString() {
    //    return
    //}

    public static void main(String[] args) {
        Pilot pilot = new Pilot("Tomas", "Kulhavy");
        FlightDiary diarySPL  = new FlightDiary(pilot, TypeOfLicence.SPL);
        FlightDiary diaryULL  = new FlightDiary(pilot, TypeOfLicence.ULL);
        Plane plane = new Plane("Bristell", TypeOfLicence.ULL, "OK-YAI-24");
        Plane plane2 = new Plane("HpH304CZ", TypeOfLicence.SPL, "OK-7304");

        LocalDate date = LocalDate.of(2022, 5, 9);
        LocalDateTime takeoffTime = date.atTime(16, 22);
        LocalDateTime landingTime = date.atTime(17, 22);
        int flightTimeMinutes = (int) ChronoUnit.MINUTES.between(takeoffTime, landingTime);

        //Flight flight = new Flight(plane, "LKLB", "LKVR", date, takeoffTime, landingTime, flightTimeMinutes, 8, "okruhy", pilot, diaryULL);
        //Flight flight2 = new Flight(plane2, "LKLB", "LKBR", date, takeoffTime, landingTime, flightTimeMinutes, 1, "přelet", pilot, diarySPL);
        //light flight3 = new Flight(plane2, "LKBR", "LKLB", date, takeoffTime, landingTime, flightTimeMinutes, 10, "přelet", pilot, diarySPL);
    }
}
