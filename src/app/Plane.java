package app;

public class Plane {
    private String name;
    private TypeOfLicence typeOfLicence;
    private String registration;
    private int takeoffNo;
    private int flightTimeMinutes;

    public Plane(String name, TypeOfLicence typeOfLicence, String registration) {
        this.name = name;
        this.typeOfLicence = typeOfLicence;
        this.registration = registration;
        this.flightTimeMinutes = 0;
        this.takeoffNo = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOfLicence getTypeOfLicence() {
        return typeOfLicence;
    }

    public void setTypeOfLicence(TypeOfLicence typeOfLicence) {
        this.typeOfLicence = typeOfLicence;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getFlightTimeMinutes() {
        return flightTimeMinutes;
    }

    public void setFlightTimeMinutes(int flightTimeMinutes) {
        this.flightTimeMinutes = flightTimeMinutes;
    }

    public int getTakeoffNo() {
        return takeoffNo;
    }

    public void setTakeoffNo(int takeoffNo) {
        this.takeoffNo = takeoffNo;
    }
}
