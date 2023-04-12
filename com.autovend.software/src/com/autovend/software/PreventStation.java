package com.autovend.software;

public class PreventStation {

    private boolean isSuspended;
    private boolean isSessionInProgress;

    /**
     * Constructor for PreventStation()
     */
    public PreventStation() {
        this.isSuspended = false;
        this.isSessionInProgress = false;
    }

    /**
     * Method for checking if the station is suspended
     * @return isSuspended
     * 		Boolean value of if the station is suspended
     */
    public boolean isSuspended() {
        return isSuspended;
    }

    /**
     * Method to check if there is a session in progress
     * @return isSessionInProgress
     * 		Boolean value of if the station has a transaction in progress
     */
    public boolean isSessionInProgress() {
        return isSessionInProgress;
    }

    /**
     * Setter for "Session In Progress"
     * @param isSessionInProgress
     * 		Boolean value to set the sessionInProgress value to
     */
    public void setSessionInProgress(boolean isSessionInProgress) {
        this.isSessionInProgress = isSessionInProgress;
    }

    /**
     * Method to suspend use of the station
     */
    public void suspend() {
        if (!isSessionInProgress || isSuspended) {
            isSuspended = true;
            System.out.println("Station ID: " + this.hashCode() + " is now suspended.");
        } else {
            System.out.println("Cannot suspend Station ID: " + this.hashCode() + " as it has an ongoing session.");
        }
    }

    /**
     * Force the suspension of the system
     */
    public void forceSuspend() {
        isSuspended = true;
        System.out.println("Station ID: " + this.hashCode() + " is now force suspended.");
    }

    /**
     * Unsuspend the use of the system.
     */
    public void unsuspend() {
        isSuspended = false;
        System.out.println("Station ID: " + this.hashCode() + " is now un-suspended.");
    }
}
