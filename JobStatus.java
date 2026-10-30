package carworkshop;

public enum JobStatus {
    OPEN, IN_PROGRESS, READY, DELIVERED;

    // The only legal transition from each state
    public JobStatus next() {
        switch (this) {
            case OPEN:        return IN_PROGRESS;
            case IN_PROGRESS: return READY;
            case READY:       return DELIVERED;
            default:          return null; // DELIVERED is final
        }
    }
}
