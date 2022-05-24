package app;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Locale;

public class Flight implements Comparator<Flight> {
    private Plane plane;
    private String takeoff;
    private String landing;
    private LocalDate date;
    private LocalDateTime takeoffTime;
    private LocalDateTime landingTime;
    private int flightTimeMinutes;
    private int takeoffNo;
    private String typeOfFlight;
    private String typeOfTakeOff;
    private Pilot pilot;
    private FlightDiary flightDiary;

    public Flight() {
    }

    public Flight(Plane plane, String takeoff, String landing, LocalDate date, LocalDateTime takeoffTime, LocalDateTime landingTime, int flightTimeMinutes, int takeoffNo, String typeOfFlight, String typeOfTakeOff, Pilot pilot, FlightDiary flightDiary, boolean exist) {
        this.plane = plane;
        this.takeoff = takeoff;
        this.landing = landing;
        this.date = date;
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.flightTimeMinutes = flightTimeMinutes;
        this.takeoffNo = takeoffNo;
        this.typeOfFlight = typeOfFlight;
        this.typeOfTakeOff = typeOfTakeOff;
        this.pilot = pilot;
        this.flightDiary = flightDiary;
        if(!exist) saveNewFlight();
    }

    // TODO - Dokumentace a javadocs
    public Flight(Plane plane, String takeoff, String landing, LocalDate date, LocalDateTime takeoffTime, LocalDateTime landingTime, int flightTimeMinutes, int takeoffNo, String typeOfFlight, Pilot pilot, FlightDiary flightDiary, boolean exist) {
        this.plane = plane;
        this.takeoff = takeoff;
        this.landing = landing;
        this.date = date;
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.flightTimeMinutes = flightTimeMinutes;
        this.takeoffNo = takeoffNo;
        this.typeOfFlight = typeOfFlight;
        this.pilot = pilot;
        this.flightDiary = flightDiary;
        if(!exist) saveNewFlight();
    }

    public void saveNewFlight() {
        try {
            FileWriter myWriter = new FileWriter(pilot.getName().toLowerCase(Locale.ROOT) + "." + plane.getTypeOfLicence(), true);
            myWriter.write("\n" + plane.getName() +
                    ", " + plane.getRegistration() +
                    ", " + takeoff +
                    ", " + landing +
                    ", " + date +
                    ", " + takeoffTime.getHour() + ":" + takeoffTime.getMinute() +
                    ", " + landingTime.getHour() + ":" + landingTime.getMinute() +
                    ", " + flightTimeMinutes +
                    ", " + takeoffNo +
                    ", " + typeOfFlight +
                    ", " + pilot.getName() +
                    ", " + plane.getTypeOfLicence());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriterPlane = new FileWriter(plane.getRegistration() + ".plane", true);
            myWriterPlane.write("\n" +
                    takeoff +
                    ", " + landing +
                    ", " + date +
                    ", " + takeoffTime.getHour() + ":" + takeoffTime.getMinute() +
                    ", " + landingTime.getHour() + ":" + landingTime.getMinute() +
                    ", " + flightTimeMinutes +
                    ", " + takeoffNo +
                    ", " + typeOfFlight +
                    ", " + pilot.getName());
            myWriterPlane.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        flightDiary.setOverall(getTakeoffNo(), getFlightTimeMinutes(), plane.getTypeOfLicence());
        plane.setFlightTimeMinutes(getFlightTimeMinutes());
        plane.setTakeoffNo(getTakeoffNo());
        plane.setOverallPlane();
    }

    public String getTakeoff() {
        return takeoff;
    }

    public String getLanding() {
        return landing;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getTakeoffTime() {
        return takeoffTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public String getTypeOfTakeOff() { return typeOfTakeOff; }

    public int getFlightTimeMinutes() {
        return flightTimeMinutes;
    }

    public int getTakeoffNo() {
        return takeoffNo;
    }

    public String getTypeOfFlight() {
        return typeOfFlight;
    }

    public String getPilot() {
        return pilot.getName();
    }

    public Plane getPlane() {
        return plane;
    }

    @Override
    public int compare(Flight o1, Flight o2) {
        int result = o1.getDate().compareTo(o2.getDate());
        result = ((-1) * result);
        if (0 == result) {
            result = o1.getTakeoffTime().compareTo(o2.getTakeoffTime());
        }
        return result;
    }
}
