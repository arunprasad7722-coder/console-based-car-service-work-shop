package carworkshop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WorkshopService {
    private Map<String, Vehicle> vehicles;
    private List<ServiceJob> jobs;               // stored as the parent type -> polymorphism
    private List<Mechanic> mechanics;
    private Map<String, Double> coupons;         // code -> discount fraction
    public static final double GST_RATE = 0.18;

    public WorkshopService() {
        vehicles = new LinkedHashMap<>();
        jobs = new ArrayList<>();
        mechanics = new ArrayList<>();
        coupons = new HashMap<>();
        coupons.put("WELCOME10", 0.10);
        coupons.put("LOYAL15", 0.15);
        coupons.put("FESTIVE20", 0.20);
    }

    // ---------- Registration ----------
    public void registerVehicle(Vehicle v) {
        vehicles.put(v.getRegNo(), v);
        System.out.println("Vehicle registered: " + v);
    }

    public void registerMechanic(Mechanic m) {
        mechanics.add(m);
        System.out.println("Mechanic registered: " + m);
    }

    private ServiceJob findJob(int jobId) {
        for (ServiceJob j : jobs) {
            if (j.getJobId() == jobId) return j;
        }
        return null;
    }

    private Mechanic findMechanic(int mechId) {
        for (Mechanic m : mechanics) {
            if (m.getMechId() == mechId) return m;
        }
        return null;
    }

    // ---------- Job creation ----------
    public ServiceJob createJob(String regNo, String serviceType, boolean premium) {
        Vehicle v = vehicles.get(regNo);
        if (v == null) {
            System.out.println("Vehicle " + regNo + " is not registered.");
            return null;
        }
        ServiceJob job = premium ? new PremiumServiceJob(v, serviceType)
                                 : new BasicServiceJob(v, serviceType);
        jobs.add(job);
        System.out.println("Job " + job.getJobId() + " created (" + job.getJobType() + ") for " + v
                + " - " + serviceType);
        return job;
    }

    // ---------- Assignment ----------
    public boolean assignMechanic(int jobId, int mechId) {
        ServiceJob job = findJob(jobId);
        Mechanic m = findMechanic(mechId);
        if (job == null || m == null) {
            System.out.println("Assignment failed: invalid job or mechanic.");
            return false;
        }
        if (!m.hasSkill(job.getServiceType())) {
            System.out.println("Assignment failed: " + m.getName() + " lacks skill '" + job.getServiceType() + "'.");
            return false;
        }
        if (job.getJobType().equals("PREMIUM") && !m.getGrade().equalsIgnoreCase("A")) {
            System.out.println("Assignment failed: premium jobs need a Grade A mechanic.");
            return false;
        }
        if (!m.canTakeMoreJobs()) {
            System.out.println("Assignment failed: " + m.getName() + " is at full capacity.");
            return false;
        }
        job.setMechanic(m);
        m.assignJob(job);
        System.out.println("Job " + jobId + " assigned to " + m);
        return true;
    }

    // ---------- Status / work updates ----------
    public void updateStatus(int jobId, JobStatus newStatus) {
        ServiceJob job = findJob(jobId);
        if (job == null) {
            System.out.println("Job " + jobId + " not found.");
            return;
        }
        if (job.updateStatus(newStatus)) {
            System.out.println("Job " + jobId + " -> " + newStatus);
            if (newStatus == JobStatus.DELIVERED) {
                job.getVehicle().setLastServiceDate(LocalDate.of(2026, 10, 30));
            }
        }
    }

    public void addPart(int jobId, String part, double price) {
        ServiceJob job = findJob(jobId);
        if (job != null) job.addPart(part, price);
    }

    public void logHours(int jobId, double hours) {
        ServiceJob job = findJob(jobId);
        if (job != null) job.setHoursWorked(job.getHoursWorked() + hours);
    }

    // ---------- Billing (overloaded) ----------
    // 1) Without coupon
    public void generateInvoice(int jobId) {
        generateInvoice(jobId, null);
    }

    // 2) With coupon
    public void generateInvoice(int jobId, String couponCode) {
        ServiceJob job = findJob(jobId);
        if (job == null) {
            System.out.println("Job " + jobId + " not found.");
            return;
        }
        if (job.getStatus() != JobStatus.READY && job.getStatus() != JobStatus.DELIVERED) {
            System.out.println("Invoice blocked: job " + jobId + " is still " + job.getStatus());
            return;
        }

        double subtotal = job.calculateTotal();           // dynamic dispatch
        double discountRate = 0;
        String couponNote = "None";
        if (couponCode != null) {
            Double rate = coupons.get(couponCode.toUpperCase());
            if (rate != null) {
                discountRate = rate;
                couponNote = couponCode.toUpperCase() + " (" + (int) (rate * 100) + "% off)";
            } else {
                couponNote = couponCode.toUpperCase() + " (invalid)";
            }
        }
        double discount = subtotal * discountRate;
        double taxable = subtotal - discount;
        double gst = taxable * GST_RATE;
        double grand = taxable + gst;

        System.out.println("\n================ INVOICE ================");
        System.out.println("Job ID       : " + job.getJobId() + " (" + job.getJobType() + ")");
        System.out.println("Vehicle      : " + job.getVehicle().getRegNo() + " - " + job.getVehicle().getModel());
        System.out.println("Customer     : " + job.getVehicle().getOwnerName());
        System.out.println("Service      : " + job.getServiceType());
        System.out.println("Mechanic     : " + (job.getMechanic() == null ? "-" : job.getMechanic().getName()));
        System.out.println("-----------------------------------------");
        System.out.printf("Labor (%.1f h x Rs.%.0f)   : Rs.%9.2f%n", job.getHoursWorked(), job.getLaborRate(), job.getLaborCost());
        for (Map.Entry<String, Double> e : job.getPartsUsed().entrySet()) {
            System.out.printf("  Part: %-18s : Rs.%9.2f%n", e.getKey(), e.getValue() * job.getPartsMultiplier());
        }
        System.out.printf("Fixed charges           : Rs.%9.2f%n", job.getFixedCharges());
        System.out.printf("Subtotal                : Rs.%9.2f%n", subtotal);
        System.out.println("Coupon                  : " + couponNote);
        System.out.printf("Discount                : -Rs.%8.2f%n", discount);
        System.out.printf("GST @ 18%%               : Rs.%9.2f%n", gst);
        System.out.printf("GRAND TOTAL             : Rs.%9.2f%n", grand);
        System.out.println("=========================================");
    }

    // ---------- Reports ----------
    public void listAllJobs() {
        System.out.println("\n----- ALL JOBS -----");
        for (ServiceJob j : jobs) j.display();       // overridden display() chosen at runtime
    }

    public double totalBilledAmount() {
        double total = 0;
        for (ServiceJob j : jobs) total += j.calculateTotal();
        return total;
    }

    public void printDailyProductivity() {
        System.out.println("\n======= DAILY MECHANIC PRODUCTIVITY =======");
        System.out.printf("%-10s %-6s %-10s %-8s %-12s%n", "Mechanic", "Grade", "Completed", "Hours", "Revenue(Rs)");
        for (Mechanic m : mechanics) {
            System.out.printf("%-10s %-6s %-10d %-8.1f %-12.2f%n", m.getName(), m.getGrade(),
                    m.completedJobCount(), m.totalHoursWorked(), m.revenueGenerated());
        }
        System.out.println("===========================================");
    }
}
