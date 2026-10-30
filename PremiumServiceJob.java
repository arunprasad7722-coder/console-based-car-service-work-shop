package carworkshop;

public class PremiumServiceJob extends ServiceJob {
    private int warrantyMonths;
    private boolean freeWash;
    private boolean pickupAndDrop;
    private String packageName;
    private static final double PREMIUM_LABOR_RATE = 900.0;

    public PremiumServiceJob(Vehicle vehicle, String serviceType) {
        super(vehicle, serviceType);
        this.warrantyMonths = 6;
        this.freeWash = true;
        this.pickupAndDrop = true;
        this.packageName = "Premium Plus";
    }

    public int getWarrantyMonths() { return warrantyMonths; }

    public boolean isFreeWash() { return freeWash; }

    public boolean isPickupAndDrop() { return pickupAndDrop; }

    public String getPackageName() { return packageName; }

    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    public void setPickupAndDrop(boolean pickupAndDrop) { this.pickupAndDrop = pickupAndDrop; }

    @Override
    public double getLaborRate() { return PREMIUM_LABOR_RATE; }

    @Override
    public double getPartsMultiplier() { return 1.25; }         // genuine parts mark-up

    @Override
    public double getFixedCharges() { return pickupAndDrop ? 350.0 : 0.0; }

    @Override
    public String getJobType() { return "PREMIUM"; }

    @Override
    public void display() {
        super.display();
        System.out.println("   -> Package: " + packageName + " | Warranty: " + warrantyMonths
                + " months | Free wash: " + (freeWash ? "Yes" : "No")
                + " | Pickup & drop: " + (pickupAndDrop ? "Yes" : "No"));
    }
}
