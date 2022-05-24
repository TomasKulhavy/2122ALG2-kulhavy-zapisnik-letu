package utils;

import app.Flight;
import app.FlightDiary;
import app.Plane;
import app.TypeOfLicence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Tools {

    public Tools() {
    }

    public static void printPlanes(List<Plane> planeList) {
        for (int i = 0; i < planeList.size(); i++) {
            System.out.println(i + 1 + "." + " - " + planeList.get(i).getRegistration());
        }
    }

    public static void printFlight(List<Flight> flights, boolean isGlider) {
        if (isGlider) {
            System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", "Datum letu", "Cas odletu", "Cas priletu", "Letiste odletu", "Letiste priletu", "Zpusob vzletu", "Cas letu", "Pocet startu", "Letadlo", "Registrace", "Poznamka");
            for (Flight flight : flights) {
                Plane plane = flight.getPlane();
                System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), flight.getTakeoff(), flight.getLanding(), flight.getTypeOfTakeOff(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), plane.getName(), plane.getRegistration(), flight.getTypeOfFlight());
            }
            System.out.println();
        } else {
            System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", "Datum letu", "Cas odletu", "Cas priletu", "Letiste odletu", "Letiste priletu", "Cas letu", "Pocet startu", "Letadlo", "Registrace", "Poznamka");
            for (Flight flight : flights) {
                Plane plane = flight.getPlane();
                System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), flight.getTakeoff(), flight.getLanding(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), plane.getName(), plane.getRegistration(), flight.getTypeOfFlight());
            }
            System.out.println();
        }
    }

    public static void printPlaneFlight(List<Flight> flights, boolean isGlider) {
        if(isGlider) {
            System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Letiste odletu", "Letiste priletu", "Datum letu", "Cas odletu", "Cas priletu", "Cas letu", "Pocet startu", "Zpusob vzletu", "Poznamka", "Pilot");
            for (Flight flight : flights) {
                System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flight.getTakeoff(), flight.getLanding(), flight.getTypeOfTakeOff(), flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), flight.getTypeOfFlight(), flight.getPilot());
            }
            System.out.println();
        } else {
            System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Letiste odletu", "Letiste priletu", "Datum letu", "Cas odletu", "Cas priletu", "Cas letu", "Pocet startu", "Poznamka", "Pilot");
            for (Flight flight : flights) {
                System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flight.getTakeoff(), flight.getLanding(), flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), flight.getTypeOfFlight(), flight.getPilot());
            }
            System.out.println();
        }
    }

    public static String getTotalTime(int minutesInput) {
        int hours = minutesInput / 60;
        int minutes = minutesInput % 60;
        return String.format("%d:%02d", hours, minutes);
    }

    public static LocalDateTime parseTime(String timeInput, LocalDate dateCal) {
        String[] timeArr = timeInput.split(":");
        return dateCal.atTime(Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]));
    }

    public static LocalDate parseDate(String dateInput) {
        String[] dateArr = dateInput.split("\\.");
        return LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
    }

    public static void generateTypesOfLicence() {
        for (int i = 0; i < TypeOfLicence.values().length; i++) {
            System.out.println(i + 1 + "." + " - " + TypeOfLicence.valueOf(i));
        }
    }

    public static void printLicences(List<FlightDiary> diaries) {
        for (int i = 0; i < diaries.size(); i++) {
            System.out.println(i + 1 + "." + " - " + diaries.get(i).getType());
        }
    }

    public static void replaceSelected(File fileInput, String replaceOld, String replaceWith) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(fileInput));
            StringBuilder inputBuffer = new StringBuilder();
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
            FileOutputStream fileOut = new FileOutputStream(fileInput);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    public static void showMenu() {
        String menu = """
                        1. Pridat typ zapisniku letu
                        2. Pridat let
                        3. Pridat letadlo
                        4. Zobrazit zapisnik
                        5. Zobrazit zapisnik letadla
                        0. Odhlasit se""";
        System.out.println(menu);
    }

    public static void showSortMenu() {
        String menu = """
                        1. Seradit sestupne podle datumu
                        2. Seradit vzestupne podle datumu
                        0. Zpet""";
        System.out.println(menu);
    }

    public static boolean isGlider(TypeOfLicence licence) {
        return licence == TypeOfLicence.SPL;
    }
}