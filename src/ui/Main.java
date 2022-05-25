package ui;

import app.*;
import utils.InputValid;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        boolean end = false;
        boolean login;
        boolean exitSort = false;
        Pilot pilot;
        Plane plane;
        plane = new Plane("OK-INIT");
        FlightDiary flightDiary;
        do {
            System.out.println("---- Vitejte v zapisniku svych letu ----");
            System.out.println("Zadejte sve jmeno [bez diakritiky] - pro vypnuti aplikace stiskni [0]: ");
            String temp = sc.nextLine();
            if (temp.equals("0")) {
                break;
            }
            String[] tempData = temp.split(" ");

            File logbook = new File(tempData[0] + "_" + tempData[1] + ".txt");
            boolean exist = logbook.exists();
            if (!exist) {
                pilot = new Pilot(tempData[0], tempData[1], false);
            } else {
                pilot = new Pilot(tempData[0], tempData[1], true);
            }
            login = true;
            System.out.println();
            System.out.println("----- Vitejte " + pilot.getName() + " -----");
            System.out.println();
            do {
                List<Plane> planes = plane.loadAllPlanes();
                Tools.showMenu();
                String typeOfTakeoff = "";
                String tempLoginInput = sc.nextLine();
                if (InputValid.parseToNumber(tempLoginInput)) {
                    int tempLogin = Integer.parseInt(tempLoginInput);
                    if (tempLogin == 0) {
                        sc.nextLine();
                        break;
                    } else if (tempLogin == 1) {
                        System.out.println("Zadej pozadovany typ licence ze seznamu: ");
                        Tools.generateTypesOfLicence();
                        int tempLicence = sc.nextInt() - 1;
                        FlightDiary diary = new FlightDiary(pilot, TypeOfLicence.valueOf(tempLicence), false);
                        pilot.addDiary(diary);
                    } else if (tempLogin == 2) {
                        System.out.println("--Zadejte informace o letu--");

                        System.out.println("Vyber letadlo: ");
                        Tools.printPlanes(planes);
                        int tempPlane = -1;
                        LocalDate date = null;
                        LocalDateTime takeoffTime = null;
                        LocalDateTime landingTime = null;
                        int takeoffNo = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempPlaneInput)) {
                                tempPlane = Integer.parseInt(tempPlaneInput);
                                if (InputValid.parseRangeInput(planes.size(), tempPlane)) {
                                    break;
                                } else if (!InputValid.parseRangeInput(planes.size(), tempPlane)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyber letadlo: ");
                                    break;
                                }
                            }
                        } while (InputValid.parseRangeInput(planes.size(), tempPlane));

                        String dateInput = "";
                        do {
                            System.out.println("Datum letu [DD.MM.YYYY]: ");
                            dateInput = sc.nextLine();
                            //if (InputValid.parseDateInput(dateInput)) {
                                date = InputValid.parseDate(dateInput);
                                break;
                            //}
                            //sc.nextLine();
                        } while (InputValid.parseDateInput(dateInput));

                        System.out.println("Zadej letiste odletu: ");
                        String takeoff = sc.nextLine();
                        System.out.println("Zadej letiste priletu: ");
                        String landing = sc.nextLine();
                        if (Tools.isGlider(planes.get(tempPlane).getTypeOfLicence())) {
                            System.out.println("Zadejte způsob vzletu: ");
                            typeOfTakeoff = sc.nextLine();
                        }


                        String takeoffTimeInput = "";
                        do {
                            System.out.println("Zadej cas odletu [HH:MM]: ");
                            takeoffTimeInput = sc.nextLine();
                            //if (InputValid.parseTimeInput(takeoffTimeInput)) {
                                takeoffTime = InputValid.parseTime(takeoffTimeInput, date);
                                break;
                            //}
                        } while (InputValid.parseTimeInput(takeoffTimeInput));


                        String landingTimeInput = "";
                        do {
                            System.out.println("Zadej cas priletu [HH:MM]: ");
                            landingTimeInput = sc.nextLine();
                            //if (InputValid.parseTimeInput(landingTimeInput)) {
                                landingTime = InputValid.parseTime(landingTimeInput, date);
                                break;
                            //}
                        } while (InputValid.parseTimeInput(landingTimeInput));


                        String tempTakeoffInput = "";
                        do {
                            System.out.println("Zadej pocet letu: ");
                            tempTakeoffInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempTakeoffInput)) {
                                tempPlane = Integer.parseInt(tempTakeoffInput);
                                break;
                            }
                            sc.nextLine();
                        } while (InputValid.parseToNumber(tempTakeoffInput));


                        System.out.println("Zadej typ letu: ");
                        String typeOfFlight = sc.nextLine();

                        Plane selectedPlane = planes.get(tempPlane - 1);

                        int flightTimeInMinutes = (int) ChronoUnit.MINUTES.between(takeoffTime, landingTime);
                        flightDiary = new FlightDiary(pilot, plane.getTypeOfLicence(), true);

                        if (Tools.isGlider(plane.getTypeOfLicence())) {
                            Flight flightGlider = new Flight(selectedPlane, takeoff, landing, date, takeoffTime, landingTime, flightTimeInMinutes, takeoffNo, typeOfTakeoff, typeOfFlight, pilot, flightDiary, false);
                        } else {
                            Flight flight = new Flight(selectedPlane, takeoff, landing, date, takeoffTime, landingTime, flightTimeInMinutes, takeoffNo, typeOfFlight, pilot, flightDiary, false);
                        }
                    } else if (tempLogin == 3) {
                        System.out.println("--Zadejte informace o letadle--");
                        System.out.println("Nazev letadla: ");
                        String name = sc.next();
                        System.out.println("Vyberte typ letadla: ");
                        Tools.generateTypesOfLicence();
                        int tempLicence = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput) - 1;
                                if (InputValid.parseRangeInput(planes.size(), tempLicence)) {
                                    break;
                                } else if (!InputValid.parseRangeInput(planes.size(), tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                }
                            }
                        } while (InputValid.parseRangeInput(planes.size(), tempLicence));
                        
                        System.out.println("Zadejte registraci letadla: ");
                        String reg = sc.next();
                        plane = new Plane(name, TypeOfLicence.valueOf(tempLicence), reg, false);

                    } else if (tempLogin == 4) {
                        System.out.println("--Vas vypis zapisniku--");
                        flightDiary = new FlightDiary(pilot, TypeOfLicence.findByLicence("ULL"), true);
                        List<FlightDiary> diaries = flightDiary.getDiaries();
                        Tools.printLicences(flightDiary.getDiaries());
                        System.out.println("Vyber podle typu licence: ");
                        int tempLicence = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput);
                                if (InputValid.parseRangeInput(planes.size(), tempLicence)) {
                                    break;
                                } else if (!InputValid.parseRangeInput(planes.size(), tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                }
                            }
                        } while (InputValid.parseRangeInput(diaries.size(), tempLicence));
                        flightDiary = diaries.get(tempLicence - 1);
                        System.out.println(flightDiary.getFlightsAndMinutes());
                        Tools.printFlight(flightDiary.getFlights(), Tools.isGlider(flightDiary.getType()));
                        do {
                            Tools.showSortMenu();
                            String tempSortInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempSortInput)) {
                                int tempSort = Integer.parseInt(tempSortInput);
                                if (tempSort == 1)
                                    Tools.printFlight(flightDiary.sortByDateDesc(), Tools.isGlider(flightDiary.getType()));
                                else if (tempSort == 2)
                                    Tools.printFlight(flightDiary.sortByDateAsc(), Tools.isGlider(flightDiary.getType()));
                                if (tempSort == 0) exitSort = true;
                            }
                        } while (!exitSort);
                    } else if (tempLogin == 5) {
                        System.out.println("--Zapisnik letadla--");
                        System.out.println("Vyber letadlo: ");
                        Tools.printPlanes(planes);
                        int tempPlaneDiary = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempPlaneInput)) {
                                tempPlaneDiary = Integer.parseInt(tempPlaneInput);
                                if (InputValid.parseRangeInput(planes.size(), tempPlaneDiary)) {
                                    break;
                                } else if (!InputValid.parseRangeInput(planes.size(), tempPlaneDiary)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                    break;
                                }
                            }
                        } while (InputValid.parseRangeInput(planes.size(), tempPlaneDiary));
                        plane = planes.get(tempPlaneDiary - 1);
                        System.out.println(plane.getFlightsAndMinutes());
                        Tools.printPlaneFlight(plane.getFlights(), Tools.isGlider(plane.getTypeOfLicence()));
                        do {
                            Tools.showSortMenu();
                            String tempSortInput = sc.nextLine();
                            if (InputValid.parseToNumber(tempSortInput)) {
                                int tempSort = Integer.parseInt(tempSortInput);
                                if (tempSort == 1)
                                    Tools.printPlaneFlight(plane.sortByDateDesc(), Tools.isGlider(plane.getTypeOfLicence()));
                                else if (tempSort == 2)
                                    Tools.printPlaneFlight(plane.sortByDateAsc(), Tools.isGlider(plane.getTypeOfLicence()));
                                if (tempSort == 0) exitSort = true;
                            }
                        } while (!exitSort);
                    }
                }
            } while (login);
        } while (!end);
    }
}
