package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Třída, která uchovává pilota
 *
 * @author Tomáš Kulhavý
 */
public class Pilot {
    boolean exist = false;
    private String firstName;
    private String lastName;
    private List<FlightDiary> diary = new ArrayList<>();

    /**
     * Konstuktor
     *
     * @param firstName Křestní jméno pilota
     * @param lastName  Příjmení pilota
     * @param exist     Existuje daný pilot?
     */
    public Pilot(String firstName, String lastName, boolean exist) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (!exist) savePilotToFile();
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

    /**
     * Metoda, která uloží pilotův profil
     */
    public void savePilotToFile() {
        File file = new File("data/exported-data/" + firstName.toLowerCase(Locale.ROOT) + "_" + lastName.toLowerCase(Locale.ROOT) + ".profile");
        exist = file.exists();

        if (!exist) {
            try {
                FileWriter myWriter = new FileWriter("data/exported-data/" + firstName.toLowerCase(Locale.ROOT) + "_" + lastName.toLowerCase(Locale.ROOT) + ".profile");
                myWriter.write(firstName + ", " + lastName);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Vrátí celé jméno pilota
     *
     * @return Pilotovo celé jméno
     */
    public String getName() {
        return firstName + "_" + lastName;
    }

    /**
     * Metoda, která přidá zápisník do profilu pilota
     *
     * @param flightDiary Letový zápisník
     */
    public void addDiary(FlightDiary flightDiary) {
        for (FlightDiary value : diary) {
            if (value.getType().equals(flightDiary.getType())) {
                break;
            }
        }
        diary.add(flightDiary);
    }
}
