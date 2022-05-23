package ui;

import app.*;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        boolean end = false;
        boolean login = false;
        Pilot pilot = null;
        Plane plane = null;
        plane = new Plane("OK-INIT");
        FlightDiary flightDiary = null;
        do {
            System.out.println("---- Vitejte v zapisniku svych letu ----");
            System.out.println("Zadejte sve jmeno [bez diakritiky] - pro vypnuti aplikace stiskni [0]: ");
            String temp = sc.nextLine();
            if (temp.equals("0")) {
                end = true;
                break;
            }
            String[] tempData = temp.split(" ");

            File logbook = new File(tempData[0] + "_" + tempData[1] + ".txt");
            boolean exist = logbook.exists();
            if (!exist) {
                pilot = new Pilot(tempData[0], tempData[1]);
            } else {
                pilot = new Pilot(tempData[0], tempData[1], logbook);
            }
            login = true;

            do {
                List<Plane> planes = plane.loadAllPlanes();
                String menu = """
                        1. Pridat typ zapisniku letu
                        2. Pridat let
                        3. Pridat letadlo
                        4. Zobrazit zapisnik
                        0. Odhlasit se""";
                System.out.println(menu);
                int tempLogin = sc.nextInt();
                if (tempLogin == 0) {
                    login = false;
                    sc.nextLine();
                    break;
                } else if (tempLogin == 1) {
                    System.out.println("Zadej pozadovany typ licence ze seznamu: ");
                    Tools.generateTypesOfLicence();
                    int tempLicence = sc.nextInt() - 1;
                    FlightDiary diary = new FlightDiary(pilot, TypeOfLicence.valueOf(tempLicence));
                    pilot.addDiary(diary);
                } else if (tempLogin == 2) {
                    System.out.println("--Zadejte informace o letu--");
                    System.out.println("Vyber letadlo: ");
                    Tools.printPlanes(planes);
                    int tempPlane = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Datum letu [DD.MM.YYYY]: ");
                    String date = sc.nextLine();
                    System.out.println("Zadej letiste odletu: ");
                    String takeoff = sc.nextLine();
                    System.out.println("Zadej letiste priletu: ");
                    String landing = sc.nextLine();
                    System.out.println("Zadej cas odletu [HH:MM]: ");
                    String takeoffTime = sc.nextLine();
                    System.out.println("Zadej cas priletu [HH:MM]: ");
                    String landingTime = sc.nextLine();
                    System.out.println("Zadej pocet letu: ");
                    int takeoffNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Zadej typ letu: ");
                    String typeOfFlight = sc.nextLine();
                    System.out.println(planes.get(tempPlane - 1).getRegistration());
                    Plane selectedPlane = planes.get(tempPlane - 1);

                    LocalDate dateCal = Tools.parseDate(date);
                    LocalDateTime takeoffTimeCal = Tools.parseTime(takeoffTime, Tools.parseDate(date));
                    LocalDateTime landingTimeCal = Tools.parseTime(landingTime, Tools.parseDate(date));

                    int flightTimeInMinutes = (int) ChronoUnit.MINUTES.between(takeoffTimeCal, landingTimeCal);
                    flightDiary = new FlightDiary(pilot, plane.getTypeOfLicence(), true);
                    Flight flight = new Flight(selectedPlane, takeoff, landing, dateCal, takeoffTimeCal, landingTimeCal, flightTimeInMinutes, takeoffNo, typeOfFlight, pilot, flightDiary);
                } else if (tempLogin == 3) {
                    System.out.println("--Zadejte informace o letadle--");
                    System.out.println("Nazev letadla: ");
                    String name = sc.next();
                    System.out.println("Vyberte typ letadla: ");
                    Tools.generateTypesOfLicence();
                    int tempLicence = sc.nextInt() - 1;
                    System.out.println("Zadejte registraci letadla: ");
                    String reg = sc.next();
                    plane = new Plane(name, TypeOfLicence.valueOf(tempLicence), reg);

                } else if (tempLogin == 4) {
                    System.out.println("--Vas vypis zapisniku--");
                    flightDiary = new FlightDiary(pilot, TypeOfLicence.findByLicence("ULL"), true);
                    List<FlightDiary> diaries = flightDiary.getDiaries();
                    Tools.printLicences(flightDiary.getDiaries());
                    System.out.println("Vyber podle typu licence: ");
                    int tempLicence = sc.nextInt();
                    flightDiary = diaries.get(tempLicence - 1);
                    System.out.println(flightDiary.getFlightsAndMinutes());
                    Tools.printFlight(flightDiary.getFlights(flightDiary));
                }
            } while (login);
        } while (!end);
    }
}
