package app;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Flight {
    private Plane plane;
    private String takeoff;
    private String landing;
    private LocalDate date;
    private LocalDateTime takeoffTime;
    private LocalDateTime landingTime;
    private int flightTimeMinutes;
    private int takeoffNo;
    private String typeOfFlight;
    private Pilot pilot;
    private FlightDiary diary;

    @Override
    public String toString() {
        return "Flight{" +
                "planeType=" + plane.getName() +
                ", planeRegistration=" + plane.getRegistration() +
                ", takeoff='" + takeoff + '\'' +
                ", landing='" + landing + '\'' +
                ", date=" + date +
                ", takeoffTime=" + takeoffTime.getHour() + ":" + takeoffTime.getMinute() +
                ", landingTime=" + landingTime +
                ", flightTimeInMinutes=" + flightTimeMinutes +
                ", takeoffNo=" + takeoffNo +
                ", typeOfFlight='" + typeOfFlight + '\'' +
                ", pilot=" + pilot.getName() +
                ", diary=" + diary.getType() +
                '}';
    }

    public Flight(Plane plane, String takeoff, String landing, LocalDate date, LocalDateTime takeoffTime, LocalDateTime landingTime, int flightTimeMinutes, int takeoffNo, String typeOfFlight, Pilot pilot, FlightDiary diary) {
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
        this.diary = diary;
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

    public String getDiary() {
        return diary.getType();
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    public void setLanding(String landing) {
        this.landing = landing;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTakeoffTime(LocalDateTime takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    public void setLandingTime(LocalDateTime landingTime) {
        this.landingTime = landingTime;
    }

    public void setFlightTimeMinutes(int flightTimeMinutes) {
        this.flightTimeMinutes = flightTimeMinutes;
    }

    public void setTakeoffNo(int takeoffNo) {
        this.takeoffNo = takeoffNo;
    }

    public void setTypeOfFlight(String typeOfFlight) {
        this.typeOfFlight = typeOfFlight;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public void setDiary(FlightDiary diary) {
        this.diary = diary;
    }
}
