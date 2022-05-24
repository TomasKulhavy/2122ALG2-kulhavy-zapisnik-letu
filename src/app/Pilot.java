package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Pilot {
    private String firstName;
    private String lastName;
    private List<FlightDiary> diary = new ArrayList<>();
    boolean exist = false;

    public Pilot(String firstName, String lastName, boolean exist) {
        this.firstName = firstName;
        this.lastName = lastName;
        if(!exist) savePilotToFile();
    }

    public void savePilotToFile() {
        File file = new File(firstName.toLowerCase(Locale.ROOT) + "_" + lastName.toLowerCase(Locale.ROOT) + ".profile");
        exist = file.exists();

        if (!exist) {
            try {
                FileWriter myWriter = new FileWriter(firstName.toLowerCase(Locale.ROOT) + "_" + lastName.toLowerCase(Locale.ROOT) + ".profile");
                myWriter.write("\n" + firstName + ", " + lastName);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return firstName + "_" + lastName;
    }

    public void addDiary(FlightDiary flightDiary) {
        for (FlightDiary value : diary) {
            if (value.getType().equals(flightDiary.getType())) {
                break;
            }
        }
        diary.add(flightDiary);
    }

    public static void main(String[] args) {
        //Pilot pilot = new Pilot("Tomas", "Kulhavy");
        //FlightDiary diarySPL = new FlightDiary(pilot, TypeOfLicence.SPL);
        //FlightDiary diaryULL = new FlightDiary(pilot, TypeOfLicence.ULL);
        //Plane plane = new Plane("Bristell", TypeOfLicence.ULL, "OK-YAI-24");
        //Plane plane2 = new Plane("HpH304CZ", TypeOfLicence.SPL, "OK-7304");

        //LocalDate date = LocalDate.of(2022, 5, 9);
        //LocalDateTime takeoffTime = date.atTime(16, 22);
        //LocalDateTime landingTime = date.atTime(17, 22);
        //int flightTimeMinutes = (int) ChronoUnit.MINUTES.between(takeoffTime, landingTime);

        //Flight flight = new Flight(plane, "LKLB", "LKVR", date, takeoffTime, landingTime, flightTimeMinutes, 8, "okruhy", pilot, diaryULL);
        //Flight flight2 = new Flight(plane2, "LKLB", "LKBR", date, takeoffTime, landingTime, flightTimeMinutes, 1, "přelet", pilot, diarySPL);
        //light flight3 = new Flight(plane2, "LKBR", "LKLB", date, takeoffTime, landingTime, flightTimeMinutes, 10, "přelet", pilot, diarySPL);
    }
}
