package carworkshop;

import java.util.ArrayList;
import java.util.List;

public class Mechanic {
    private int mechId;
    private String name;
    private String grade;                    // A (senior), B, C
    private List<String> skills;
    private List<ServiceJob> assignedJobs;
    private int maxActiveJobs;
    private static int totalMechanics = 0;

    public Mechanic(int mechId, String name, String grade, List<String> skills) {
        this.mechId = mechId;
        this.name = name;
        this.grade = grade;
        this.skills = new ArrayList<>(skills);
        this.assignedJobs = new ArrayList<>();
        this.maxActiveJobs = grade.equalsIgnoreCase("A") ? 3 : 2;
        totalMechanics++;
    }

    public int getMechId() { return mechId; }

    public String getName() { return name; }

    public String getGrade() { return grade; }

    public List<String> getSkills() { return skills; }

    public List<ServiceJob> getAssignedJobs() { return assignedJobs; }

    public static int getTotalMechanics() { return totalMechanics; }

    public void setGrade(String grade) { this.grade = grade; }

    public void addSkill(String skill) { skills.add(skill); }

    public boolean hasSkill(String skill) {
        for (String s : skills) {
            if (s.equalsIgnoreCase(skill)) return true;
        }
        return false;
    }

    public int activeJobCount() {
        int count = 0;
        for (ServiceJob j : assignedJobs) {
            if (j.getStatus() == JobStatus.OPEN || j.getStatus() == JobStatus.IN_PROGRESS) count++;
        }
        return count;
    }

    public boolean canTakeMoreJobs() { return activeJobCount() < maxActiveJobs; }

    public void assignJob(ServiceJob job) { assignedJobs.add(job); }

    public int completedJobCount() {
        int count = 0;
        for (ServiceJob j : assignedJobs) {
            if (j.getStatus() == JobStatus.READY || j.getStatus() == JobStatus.DELIVERED) count++;
        }
        return count;
    }

    public double totalHoursWorked() {
        double hours = 0;
        for (ServiceJob j : assignedJobs) hours += j.getHoursWorked();
        return hours;
    }

    public double revenueGenerated() {
        double total = 0;
        for (ServiceJob j : assignedJobs) {
            if (j.getStatus() == JobStatus.READY || j.getStatus() == JobStatus.DELIVERED) {
                total += j.calculateTotal();
            }
        }
        return total;
    }

    public void display() {
        System.out.println("Mechanic " + mechId + " | " + name + " | Grade " + grade + " | Skills: " + skills);
    }

    @Override
    public String toString() {
        return name + " (Grade " + grade + ")";
    }
}
