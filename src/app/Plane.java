package app;

public class Plane {
    private String name;
    private TypeOfLicence typeOfLicence;
    private String registration;

    public Plane(String name, TypeOfLicence typeOfLicence, String registration) {
        this.name = name;
        this.typeOfLicence = typeOfLicence;
        this.registration = registration;
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
}
