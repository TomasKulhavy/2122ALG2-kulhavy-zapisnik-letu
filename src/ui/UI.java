package ui;

import app.*;
import org.w3c.dom.ranges.RangeException;
import utils.IInputValid;
import utils.PDFGenerator;
import utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class UI {
    private static Scanner sc = new Scanner(System.in);
    IInputValid inputValid;

    public UI(IInputValid inputValid) {
        this.inputValid = inputValid;
    }

    public void Menu() throws FileNotFoundException, RangeException, DateTimeException {
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
                if (inputValid.parseToNumber(tempLoginInput)) {
                    int tempLogin = Integer.parseInt(tempLoginInput);
                    if (tempLogin == 0) {
                        break;
                    } else if (tempLogin == 1) {
                        System.out.println("Zadej pozadovany typ licence ze seznamu: ");
                        Tools.generateTypesOfLicence();
                        int tempLicence = -1;
                        do {
                            String tempPlaneInput = sc.nextLine();
                            if (inputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput) - 1;
                                if (inputValid.parseRangeInput(2, tempLicence)) {
                                    break;
                                } else if (!inputValid.parseRangeInput(2, tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ licence: ");
                                }
                            }
                        } while (!inputValid.parseRangeInput(2, tempLicence));
                        FlightDiary diary = new FlightDiary(pilot, TypeOfLicence.valueOf(tempLicence), false);
                        pilot.addDiary(diary);
                    } else if (tempLogin == 2) {
                        System.out.println("--Zadejte informace o letu--");

                        System.out.println("Vyber letadlo: ");
                        Tools.printPlanes(planes);
                        if (planes.size() == 0) {
                            System.out.println("Neni zde zadne letadlo, musis ho pridat.");
                            break;
                        }
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
                            if (inputValid.parseToNumber(tempPlaneInput)) {
                                tempPlane = Integer.parseInt(tempPlaneInput) - 1;
                                if (inputValid.parseRangeInput(planes.size(), tempPlane)) {
                                    break;
                                } else if (!inputValid.parseRangeInput(planes.size(), tempPlane)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyber letadlo: ");
                                }
                            }
                        } while (!inputValid.parseRangeInput(planes.size(), tempPlane));

                        flightDiary = new FlightDiary(pilot, TypeOfLicence.findByLicence("ULL"), true);
                        List<FlightDiary> diaries = flightDiary.getDiaries();
                        boolean licenceExist = false;
                        for (int i = 0; i < diaries.size(); i++) {
                            if (planes.get(tempPlane).getTypeOfLicence() == diaries.get(i).getType()) {
                                licenceExist = true;
                            }
                        }
                        if (!licenceExist) {
                            System.out.println("Nemate pozadovanou licenci, musite si ji pridat");
                            break;
                        }
                        String yearFlightInput;
                        do {
                            System.out.println("Zadej rok letu: ");
                            yearFlightInput = sc.nextLine();
                            if (inputValid.parseToNumber(yearFlightInput)) {
                                yearFlight = Integer.parseInt(yearFlightInput);
                                break;
                            } else if (!inputValid.parseToNumber(yearFlightInput)) {
                                System.out.println("Zadej platny rok [cislo]: ");
                            }
                        } while (!inputValid.parseToNumber(yearFlightInput));

                        String monthFlightInput;
                        do {
                            System.out.println("Zadej mesic letu: ");
                            monthFlightInput = sc.nextLine();
                            if (inputValid.parseToNumber(monthFlightInput)) {
                                monthFlight = Integer.parseInt(monthFlightInput);
                                if (inputValid.parseMonth(monthFlight)) {
                                    break;
                                } else if (!inputValid.parseMonth(monthFlight)) {
                                    System.out.println("Zadej platny mesic: [1-12]");
                                }
                            }
                        } while (!inputValid.parseMonth(monthFlight));

                        String dayFlightInput;
                        do {
                            System.out.println("Zadej den letu: ");
                            dayFlightInput = sc.nextLine();
                            if (inputValid.parseToNumber(dayFlightInput)) {
                                dayFlight = Integer.parseInt(dayFlightInput);
                                if (inputValid.parseDay(dayFlight, monthFlight, yearFlight)) {
                                    break;
                                } else if (!inputValid.parseDay(dayFlight, monthFlight, yearFlight)) {
                                    System.out.println("Zadej platne cislo dne: [1-31]");
                                }
                            }
                        } while (!inputValid.parseDay(dayFlight, monthFlight, yearFlight));

                        date = inputValid.parseDate(dayFlight, monthFlight, yearFlight);

                        System.out.println("Zadej letiste odletu: ");
                        String takeoff = sc.nextLine();
                        System.out.println("Zadej letiste priletu: ");
                        String landing = sc.nextLine();

                        if (Tools.isGlider(planes.get(tempPlane).getTypeOfLicence())) {
                            System.out.println("Zadejte zpusob vzletu: [aerovlek, navijak]");
                            typeOfTakeoff = sc.nextLine();
                        }

                        String takeoffTimeInput;
                        do {
                            System.out.println("Zadej cas odletu [HH:MM]: ");
                            takeoffTimeInput = sc.nextLine();
                            if (inputValid.parseTime(takeoffTimeInput, date)) {
                                takeoffTime = inputValid.parseTimeInput(takeoffTimeInput, date);
                                break;
                            } else if (!inputValid.parseTime(takeoffTimeInput, date)) {
                                System.out.println("Zadej platny cas: ");
                            }
                        } while (!inputValid.parseTime(takeoffTimeInput, date));


                        String landingTimeInput;
                        do {
                            System.out.println("Zadej cas priletu [HH:MM]: ");
                            landingTimeInput = sc.nextLine();
                            if (inputValid.parseTime(landingTimeInput, date)) {
                                landingTime = inputValid.parseTimeInput(landingTimeInput, date);
                                break;
                            } else if (!inputValid.parseTime(landingTimeInput, date)) {
                                System.out.println("Zadej platny cas: ");
                            }
                        } while (!inputValid.parseTime(landingTimeInput, date));

                        String tempTakeoffInput;
                        do {
                            System.out.println("Zadej pocet letu: ");
                            tempTakeoffInput = sc.nextLine();
                            if (inputValid.parseToNumber(tempTakeoffInput)) {
                                takeoffNo = Integer.parseInt(tempTakeoffInput);
                                break;
                            } else if (!inputValid.parseToNumber(tempTakeoffInput)) {
                                System.out.println("Zadej cislo: ");
                            }
                        } while (!inputValid.parseToNumber(tempTakeoffInput));


                        System.out.println("Zadej typ letu: [uloha letu, atd.]");
                        String typeOfFlight = sc.nextLine();

                        Plane selectedPlane = planes.get(tempPlane);

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
                        sc.nextLine();
                        String tempPlaneInput = sc.nextLine();
                        do {
                            if (inputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput) - 1;
                                if (inputValid.parseRangeInput(TypeOfLicence.values().length - 1, tempLicence)) {
                                    break;
                                } else if (!inputValid.parseRangeInput(TypeOfLicence.values().length - 1, tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                    tempPlaneInput = sc.nextLine();
                                }
                            }
                        } while (!inputValid.parseRangeInput(TypeOfLicence.values().length - 1, tempLicence));

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
                            if (inputValid.parseToNumber(tempPlaneInput)) {
                                tempLicence = Integer.parseInt(tempPlaneInput);
                                if (inputValid.parseRangeInput(diaries.size(), tempLicence)) {
                                    break;
                                } else if (!inputValid.parseRangeInput(diaries.size(), tempLicence)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyberte typ letadla: ");
                                }
                            }
                        } while (!inputValid.parseRangeInput(diaries.size(), tempLicence));

                        flightDiary = diaries.get(tempLicence - 1);
                        System.out.println(flightDiary.getFlightsAndMinutes());
                        Tools.printFlight(flightDiary.getFlights(), Tools.isGlider(flightDiary.getType()));

                        do {
                            Tools.showSortMenu();
                            String tempSortInput = sc.nextLine();
                            if (inputValid.parseToNumber(tempSortInput)) {
                                int tempSort = Integer.parseInt(tempSortInput);
                                if (tempSort == 1)
                                    Tools.printFlight(flightDiary.sortByDateDesc(), Tools.isGlider(flightDiary.getType()));
                                else if (tempSort == 2)
                                    Tools.printFlight(flightDiary.sortByDateAsc(), Tools.isGlider(flightDiary.getType()));
                                else if (tempSort == 3)
                                    PDFGenerator.saveToPdf(flightDiary.getFlights(), pilot, flightDiary.getType(), Tools.isGlider(flightDiary.getType()));
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
                            if (inputValid.parseToNumber(tempPlaneInput)) {
                                tempPlaneDiary = Integer.parseInt(tempPlaneInput);
                                if (inputValid.parseRangeInput(planes.size(), tempPlaneDiary)) {
                                    break;
                                } else if (!inputValid.parseRangeInput(planes.size(), tempPlaneDiary)) {
                                    System.out.println("Zadavate ve spatnem rozsahu");
                                    System.out.println("Vyber letadlo: ");
                                }
                            }
                        } while (!inputValid.parseRangeInput(planes.size(), tempPlaneDiary));

                        plane = planes.get(tempPlaneDiary - 1);
                        System.out.println(plane.getFlightsAndMinutes());
                        Tools.printPlaneFlight(plane.getFlights(), Tools.isGlider(plane.getTypeOfLicence()));
                        do {
                            Tools.showSortMenu();
                            String tempSortInput = sc.nextLine();
                            if (inputValid.parseToNumber(tempSortInput)) {
                                int tempSort = Integer.parseInt(tempSortInput);
                                if (tempSort == 1)
                                    Tools.printPlaneFlight(plane.sortByDateDesc(), Tools.isGlider(plane.getTypeOfLicence()));
                                else if (tempSort == 2)
                                    Tools.printPlaneFlight(plane.sortByDateAsc(), Tools.isGlider(plane.getTypeOfLicence()));
                                else if (tempSort == 3)
                                    PDFGenerator.saveToPdfPlane(plane.getFlights(), plane, Tools.isGlider(plane.getTypeOfLicence()));
                                if (tempSort == 0) exitSort = true;
                            }
                        } while (!exitSort);
                    }
                }
            } while (login);
        } while (!end);
    }
}
