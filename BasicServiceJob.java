package carworkshop;

public class BasicServiceJob extends ServiceJob {
    private int warrantyDays;
    private boolean aftermarketPartsAllowed;
    private String packageName;
    private static final double BASIC_LABOR_RATE = 400.0;

    public BasicServiceJob(Vehicle vehicle, String serviceType) {
        super(vehicle, serviceType);
        this.warrantyDays = 30;
        this.aftermarketPartsAllowed = true;
        this.packageName = "Basic Care";
    }

    public int getWarrantyDays() { return warrantyDays; }

    public boolean isAftermarketPartsAllowed() { return aftermarketPartsAllowed; }

    public String getPackageName() { return packageName; }

    public void setWarrantyDays(int warrantyDays) { this.warrantyDays = warrantyDays; }

    public void setPackageName(String packageName) { this.packageName = packageName; }

    @Override
    public double getLaborRate() { return BASIC_LABOR_RATE; }

    @Override
    public double getPartsMultiplier() { return 1.0; }          // parts at cost

    @Override
    public double getFixedCharges() { return 100.0; }           // consumables / shop supplies

    @Override
    public String getJobType() { return "BASIC"; }

    @Override
    public void display() {
        super.display();
        System.out.println("   -> Package: " + packageName + " | Warranty: " + warrantyDays + " days");
    }
}
