package ui;

import app.*;
import org.w3c.dom.ranges.RangeException;
import utils.IInputValid;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

// TODO - dokumentace
// TODO - prezentace
// TODO - diagram
// TODO - export do PDF

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, RangeException, DateTimeException {
        boolean end = false;
        boolean login;
        boolean exitSort = false;
        Pilot pilot;
        Plane plane;
        plane = new Plane("OK-INIT");
        FlightDiary flightDiary;
        do {
            System.out.println("---- Vitejte v zapisniku svych letu ----");
            System.out.println("Zadejte sve jmeno a prijmeni [jan novak] - pro vypnuti aplikace stiskni [0]: ");
            String temp = sc.nextLine();
            if (temp.equals("0")) {
                break;
            }
            String[] tempData = temp.split(" ");
            String surname;
            if (tempData.length < 2) {
                surname = " ";
            } else {
                surname = tempData[1];
            }
            File logbook = new File(tempData[0] + "_" + surname + ".txt");
            boolean exist = logbook.exists();

            if (!exist) {
                pilot = new Pilot(tempData[0], surname, false);
            } else {
                pilot = new Pilot(tempData[0], surname, true);
            }

            login = true;
            System.out.println();
            System.out.println("----- Vitejte " + pilot.getName() + " -----");
            System.out.println();
            do {
                List<Plane> planes = plane.loadAllPlanes();
                Tools.showMenu();
                String typeOfTakeoff = null;
                String tempLoginInput = sc.nextLine();
                if (IInputValid.parseToNumber(tempLoginInput)) {
                    int tempLogin = Integer.parseInt(tempLoginInput);
                    if (tempLogin == 0) {
                        break;
                    } else if (tempLogin == 1) {
                        System.out.println("Zadej pozadovany typ licence ze seznamu: ");
                        Tools.generateTypesOfLicence();
                        int tempLicence = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (IInputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput) - 1;
                                if (IInputValid.parseRangeInput(2, tempLicence)) {
                                    break;
                                } else if (!IInputValid.parseRangeInput(2, tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ licence: ");
                                }
                            }
                        } while (!IInputValid.parseRangeInput(2, tempLicence));
                        FlightDiary diary = new FlightDiary(pilot, TypeOfLicence.valueOf(tempLicence), false);
                        pilot.addDiary(diary);
                    } else if (tempLogin == 2) {
                        System.out.println("--Zadejte informace o letu--");

                        System.out.println("Vyber letadlo: ");
                        Tools.printPlanes(planes);
                        int tempPlane = -1;
                        LocalDate date;
                        LocalDateTime takeoffTime = null;
                        LocalDateTime landingTime = null;
                        int yearFlight = 0;
                        int dayFlight = 0;
                        int monthFlight = 0;
                        int takeoffNo = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (IInputValid.parseToNumber(tempPlaneInput)) {
                                tempPlane = Integer.parseInt(tempPlaneInput);
                                if (IInputValid.parseRangeInput(planes.size(), tempPlane)) {
                                    break;
                                } else if (!IInputValid.parseRangeInput(planes.size(), tempPlane)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyber letadlo: ");
                                }
                            }
                        } while (!IInputValid.parseRangeInput(planes.size(), tempPlane));

                        String yearFlightInput;
                        do {
                            System.out.println("Zadej rok letu: ");
                            yearFlightInput = sc.nextLine();
                            if (IInputValid.parseToNumber(yearFlightInput)) {
                                yearFlight = Integer.parseInt(yearFlightInput);
                                break;
                            } else if (!IInputValid.parseToNumber(yearFlightInput)) {
                                System.out.println("Zadej platny rok [cislo]: ");
                            }
                        } while (!IInputValid.parseToNumber(yearFlightInput));

                        String monthFlightInput;
                        do {
                            System.out.println("Zadej mesic letu: ");
                            monthFlightInput = sc.nextLine();
                            if (IInputValid.parseToNumber(monthFlightInput)) {
                                monthFlight = Integer.parseInt(monthFlightInput);
                                if (IInputValid.parseMonth(monthFlight)) {
                                    break;
                                } else if (!IInputValid.parseMonth(monthFlight)) {
                                    System.out.println("Zadej platny mesic: [1-12]");
                                }
                            }
                        } while (!IInputValid.parseMonth(monthFlight));

                        String dayFlightInput;
                        do {
                            System.out.println("Zadej den letu: ");
                            dayFlightInput = sc.nextLine();
                            if (IInputValid.parseToNumber(dayFlightInput)) {
                                dayFlight = Integer.parseInt(dayFlightInput);
                                if (IInputValid.parseDay(dayFlight, monthFlight, yearFlight)) {
                                    break;
                                } else if (!IInputValid.parseDay(dayFlight, monthFlight, yearFlight)) {
                                    System.out.println("Zadej platne cislo dne: [1-31]");
                                }
                            }
                        } while (!IInputValid.parseDay(dayFlight, monthFlight, yearFlight));

                        date = IInputValid.parseDate(dayFlight, monthFlight, yearFlight);

                        System.out.println("Zadej letiste odletu: ");
                        String takeoff = sc.nextLine();
                        System.out.println("Zadej letiste priletu: ");
                        String landing = sc.nextLine();

                        if (Tools.isGlider(planes.get(tempPlane - 1).getTypeOfLicence())) {
                            System.out.println("Zadejte zpusob vzletu: [aerovlek, navijak]");
                            typeOfTakeoff = sc.nextLine();
                        }

                        String takeoffTimeInput;
                        do {
                            System.out.println("Zadej cas odletu [HH:MM]: ");
                            takeoffTimeInput = sc.nextLine();
                            if (IInputValid.parseTime(takeoffTimeInput, date)) {
                                takeoffTime = IInputValid.parseTimeInput(takeoffTimeInput, date);
                                break;
                            } else if (!IInputValid.parseTime(takeoffTimeInput, date)) {
                                System.out.println("Zadej platny cas: ");
                            }
                        } while (!IInputValid.parseTime(takeoffTimeInput, date));


                        String landingTimeInput;
                        do {
                            System.out.println("Zadej cas priletu [HH:MM]: ");
                            landingTimeInput = sc.nextLine();
                            if (IInputValid.parseTime(landingTimeInput, date)) {
                                landingTime = IInputValid.parseTimeInput(landingTimeInput, date);
                                break;
                            } else if (!IInputValid.parseTime(landingTimeInput, date)) {
                                System.out.println("Zadej platny cas: ");
                            }
                        } while (!IInputValid.parseTime(landingTimeInput, date));

                        String tempTakeoffInput;
                        do {
                            System.out.println("Zadej pocet letu: ");
                            tempTakeoffInput = sc.nextLine();
                            if (IInputValid.parseToNumber(tempTakeoffInput)) {
                                takeoffNo = Integer.parseInt(tempTakeoffInput);
                                break;
                            } else if (!IInputValid.parseToNumber(tempTakeoffInput)) {
                                System.out.println("Zadej cislo: ");
                            }
                        } while (!IInputValid.parseToNumber(tempTakeoffInput));


                        System.out.println("Zadej typ letu: [uloha letu, atd.]");
                        String typeOfFlight = sc.nextLine();

                        Plane selectedPlane = planes.get(tempPlane - 1);

                        int flightTimeInMinutes = (int) ChronoUnit.MINUTES.between(takeoffTime, landingTime);
                        flightDiary = new FlightDiary(pilot, plane.getTypeOfLicence(), true);

                        if (Tools.isGlider(selectedPlane.getTypeOfLicence())) {
                            Flight flightGlider = new Flight(selectedPlane, takeoff, landing, date, takeoffTime, landingTime, flightTimeInMinutes, takeoffNo, typeOfFlight, typeOfTakeoff, pilot, flightDiary, false);
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
                            if (IInputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput) - 1;
                                if (IInputValid.parseRangeInput(planes.size(), tempLicence)) {
                                    break;
                                } else if (!IInputValid.parseRangeInput(planes.size(), tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                }
                            }
                        } while (!IInputValid.parseRangeInput(planes.size(), tempLicence));

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
                            if (IInputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput);
                                if (IInputValid.parseRangeInput(diaries.size(), tempLicence)) {
                                    break;
                                } else if (!IInputValid.parseRangeInput(diaries.size(), tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                }
                            }
                        } while (!IInputValid.parseRangeInput(diaries.size(), tempLicence));

                        flightDiary = diaries.get(tempLicence - 1);
                        System.out.println(flightDiary.getFlightsAndMinutes());
                        Tools.printFlight(flightDiary.getFlights(), Tools.isGlider(flightDiary.getType()));
                        do {
                            Tools.showSortMenu();
                            String tempSortInput = sc.nextLine();
                            if (IInputValid.parseToNumber(tempSortInput)) {
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
                            if (IInputValid.parseToNumber(tempPlaneInput)) {
                                tempPlaneDiary = Integer.parseInt(tempPlaneInput);
                                if (IInputValid.parseRangeInput(planes.size(), tempPlaneDiary)) {
                                    break;
                                } else if (!IInputValid.parseRangeInput(planes.size(), tempPlaneDiary)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyber letadlo: ");
                                }
                            }
                        } while (!IInputValid.parseRangeInput(planes.size(), tempPlaneDiary));

                        plane = planes.get(tempPlaneDiary - 1);
                        System.out.println(plane.getFlightsAndMinutes());
                        Tools.printPlaneFlight(plane.getFlights(), Tools.isGlider(plane.getTypeOfLicence()));
                        do {
                            Tools.showSortMenu();
                            String tempSortInput = sc.nextLine();
                            if (IInputValid.parseToNumber(tempSortInput)) {
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
