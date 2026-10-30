package carworkshop;

import java.time.LocalDate;

public class Vehicle {
    private String regNo;
    private String ownerName;
    private String model;
    private int mileage;
    private LocalDate lastServiceDate;
    private static int totalVehicles = 0;

    public Vehicle(String regNo, String ownerName, String model, int mileage, LocalDate lastServiceDate) {
        this.regNo = regNo;
        this.ownerName = ownerName;
        this.model = model;
        this.mileage = mileage;
        this.lastServiceDate = lastServiceDate;
        totalVehicles++;
    }

    public String getRegNo() { return regNo; }

    public String getOwnerName() { return ownerName; }

    public String getModel() { return model; }

    public int getMileage() { return mileage; }

    public LocalDate getLastServiceDate() { return lastServiceDate; }

    public static int getTotalVehicles() { return totalVehicles; }

    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public void setMileage(int mileage) {
        if (mileage >= this.mileage) {
            this.mileage = mileage;
        } else {
            System.out.println("Invalid mileage: odometer cannot go backwards.");
        }
    }

    public void setLastServiceDate(LocalDate lastServiceDate) { this.lastServiceDate = lastServiceDate; }

    public long daysSinceLastService(LocalDate today) {
        return java.time.temporal.ChronoUnit.DAYS.between(lastServiceDate, today);
    }

    public void display() {
        System.out.println("Vehicle: " + regNo + " | " + model + " | Owner: " + ownerName
                + " | Mileage: " + mileage + " km | Last Service: " + lastServiceDate);
    }

    @Override
    public String toString() {
        return regNo + " (" + model + ")";
    }
}
