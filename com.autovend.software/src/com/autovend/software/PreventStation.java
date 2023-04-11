package com.autovend.software;

public class PreventStation {

    private boolean isSuspended;
    private boolean isSessionInProgress;

    public PreventStation() {
        this.isSuspended = false;
        this.isSessionInProgress = false;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public boolean isSessionInProgress() {
        return isSessionInProgress;
    }

    public void setSessionInProgress(boolean isSessionInProgress) {
        this.isSessionInProgress = isSessionInProgress;
    }

    public void suspend() {
        if (!isSessionInProgress || isSuspended) {
            isSuspended = true;
            System.out.println("Station ID: " + this.hashCode() + " is now suspended.");
        } else {
            System.out.println("Cannot suspend Station ID: " + this.hashCode() + " as it has an ongoing session.");
        }
    }

    public void forceSuspend() {
        isSuspended = true;
        System.out.println("Station ID: " + this.hashCode() + " is now force suspended.");
    }

    public void unsuspend() {
        isSuspended = false;
        System.out.println("Station ID: " + this.hashCode() + " is now un-suspended.");
    }
}
