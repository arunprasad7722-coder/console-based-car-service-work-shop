package carworkshop;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServiceJob {
    private int jobId;
    private Vehicle vehicle;
    private String serviceType;
    private Map<String, Double> partsUsed;
    private JobStatus status;          // private: changed only through updateStatus()
    private Mechanic mechanic;
    private double hoursWorked;
    private static int jobCounter = 1000;

    public ServiceJob(Vehicle vehicle, String serviceType) {
        this.jobId = ++jobCounter;
        this.vehicle = vehicle;
        this.serviceType = serviceType;
        this.partsUsed = new LinkedHashMap<>();
        this.status = JobStatus.OPEN;
        this.hoursWorked = 0;
    }

    // ---------- Getters / setters ----------
    public int getJobId() { return jobId; }

    public Vehicle getVehicle() { return vehicle; }

    public String getServiceType() { return serviceType; }

    public JobStatus getStatus() { return status; }

    public Mechanic getMechanic() { return mechanic; }

    public double getHoursWorked() { return hoursWorked; }

    public Map<String, Double> getPartsUsed() { return partsUsed; }

    public void setMechanic(Mechanic mechanic) { this.mechanic = mechanic; }

    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }

    // ---------- Behaviour ----------
    public void addPart(String partName, double price) {
        if (status == JobStatus.READY || status == JobStatus.DELIVERED) {
            System.out.println("Cannot add parts: job " + jobId + " is already " + status);
            return;
        }
        partsUsed.put(partName, price);
    }

    // Encapsulated status control: OPEN -> IN_PROGRESS -> READY -> DELIVERED
    public boolean updateStatus(JobStatus newStatus) {
        if (status.next() == newStatus) {
            if (newStatus == JobStatus.IN_PROGRESS && mechanic == null) {
                System.out.println("Job " + jobId + " cannot start: no mechanic assigned.");
                return false;
            }
            status = newStatus;
            return true;
        }
        System.out.println("Illegal status change for job " + jobId + ": " + status + " -> " + newStatus);
        return false;
    }

    // ---------- Pricing hooks (overridden by subclasses) ----------
    public double getLaborRate()      { return 500.0; }   // per hour
    public double getPartsMultiplier() { return 1.0; }    // mark-up on parts
    public double getFixedCharges()   { return 0.0; }     // flat extras
    public String getJobType()        { return "STANDARD"; }

    public double getLaborCost() { return hoursWorked * getLaborRate(); }

    public double getPartsCost() {
        double sum = 0;
        for (double p : partsUsed.values()) sum += p;
        return sum * getPartsMultiplier();
    }

    // Polymorphic total: uses whichever overridden rates the runtime object supplies
    public double calculateTotal() {
        return getLaborCost() + getPartsCost() + getFixedCharges();
    }

    public void display() {
        System.out.println("Job " + jobId + " | " + getJobType() + " | " + vehicle.getRegNo()
                + " | " + serviceType + " | " + status
                + " | Mechanic: " + (mechanic == null ? "Unassigned" : mechanic.getName()));
    }
}
