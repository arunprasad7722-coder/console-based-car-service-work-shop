package carworkshop;

import java.time.LocalDate;
import java.util.Arrays;

public class WorkshopAppMain {

    public static void main(String[] args) {
        System.out.println("===== CAR SERVICE WORKSHOP =====");
        WorkshopService ws = new WorkshopService();

        // 1. Register vehicles
        System.out.println("\n--- Registering vehicles ---");
        ws.registerVehicle(new Vehicle("TN01AB1234", "Arun Kumar", "Hyundai i20", 42000, LocalDate.of(2026, 3, 15)));
        ws.registerVehicle(new Vehicle("TN09CD5678", "Priya Nair", "Honda City", 28500, LocalDate.of(2026, 5, 2)));
        ws.registerVehicle(new Vehicle("TN22EF9012", "Karthik Raja", "Toyota Fortuner", 61000, LocalDate.of(2025, 12, 20)));

        // 2. Register mechanics
        System.out.println("\n--- Registering mechanics ---");
        ws.registerMechanic(new Mechanic(1, "Ravi", "A", Arrays.asList("Engine Overhaul", "Oil Change", "Brake Service")));
        ws.registerMechanic(new Mechanic(2, "Suresh", "B", Arrays.asList("Oil Change", "AC Repair")));

        // 3. Create jobs
        System.out.println("\n--- Creating jobs ---");
        ServiceJob j1 = ws.createJob("TN01AB1234", "Oil Change", false);       // Basic
        ServiceJob j2 = ws.createJob("TN09CD5678", "Brake Service", true);     // Premium
        ServiceJob j3 = ws.createJob("TN22EF9012", "AC Repair", false);        // Basic
        ws.createJob("TN00XX0000", "Oil Change", false);                       // unregistered vehicle

        // 4. Assign mechanics (includes rule checks)
        System.out.println("\n--- Assigning mechanics ---");
        ws.assignMechanic(j1.getJobId(), 2);   // OK: Suresh has Oil Change
        ws.assignMechanic(j2.getJobId(), 2);   // fails: premium needs Grade A + skill
        ws.assignMechanic(j2.getJobId(), 1);   // OK: Ravi (A)
        ws.assignMechanic(j3.getJobId(), 2);   // OK: Suresh has AC Repair

        // 5. Encapsulation demo: illegal jumps are rejected
        System.out.println("\n--- Status control ---");
        ws.updateStatus(j1.getJobId(), JobStatus.READY);        // illegal: skips IN_PROGRESS
        ws.updateStatus(j1.getJobId(), JobStatus.IN_PROGRESS);
        ws.updateStatus(j2.getJobId(), JobStatus.IN_PROGRESS);
        ws.updateStatus(j3.getJobId(), JobStatus.IN_PROGRESS);

        // 6. Work done: parts + labour hours
        ws.addPart(j1.getJobId(), "Engine Oil 4L", 1800);
        ws.addPart(j1.getJobId(), "Oil Filter", 350);
        ws.logHours(j1.getJobId(), 1.5);

        ws.addPart(j2.getJobId(), "Brake Pads (set)", 3200);
        ws.addPart(j2.getJobId(), "Brake Fluid", 600);
        ws.logHours(j2.getJobId(), 3.0);

        ws.addPart(j3.getJobId(), "AC Gas Refill", 1500);
        ws.logHours(j3.getJobId(), 2.0);

        // 7. Billing before completion is blocked
        System.out.println("\n--- Billing check ---");
        ws.generateInvoice(j1.getJobId());

        // 8. Progress to READY, then DELIVERED
        ws.updateStatus(j1.getJobId(), JobStatus.READY);
        ws.updateStatus(j2.getJobId(), JobStatus.READY);
        ws.updateStatus(j3.getJobId(), JobStatus.READY);

        // 9. Invoices: overloaded generateInvoice()
        ws.generateInvoice(j1.getJobId());                       // without coupon
        ws.generateInvoice(j2.getJobId(), "LOYAL15");            // with coupon
        ws.generateInvoice(j3.getJobId(), "BADCODE");            // invalid coupon

        System.out.println();
        ws.updateStatus(j1.getJobId(), JobStatus.DELIVERED);
        ws.updateStatus(j2.getJobId(), JobStatus.DELIVERED);

        // 10. Polymorphic listing + totals
        ws.listAllJobs();
        System.out.printf("%nTotal billed (pre-tax, all jobs): Rs.%.2f%n", ws.totalBilledAmount());

        // 11. Productivity
        ws.printDailyProductivity();

        System.out.println("\nTotal vehicles: " + Vehicle.getTotalVehicles()
                + " | Total mechanics: " + Mechanic.getTotalMechanics());
        System.out.println("===== END OF DAY =====");
    }
}

Execution Output Screenshot




GitHub Repository Link
https://github.com/<your-username>/CarServiceWorkshop.git
