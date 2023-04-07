package com.autovend.software;

import com.autovend.software.PreventStation;
import java.util.List;

public class PermitStation {

    public PermitStation() {
    }

    // Display the status of all stations
    public void displayStations(List<PreventStation> stations) {
        System.out.println("Stations status:");
        for (PreventStation station : stations) {
            System.out.println("Station ID: " + station.hashCode() + ", Suspended: " + station.isSuspended() + ", Session in progress: " + station.isSessionInProgress());
        }
        System.out.println();
    }

    // Display the list of suspended stations
    public void displaySuspendedStations(List<PreventStation> stations) {
        System.out.println("Suspended stations:");
        for (PreventStation station : stations) {
            if (station.isSuspended()) {
                System.out.println("Station ID: " + station.hashCode());
            }
        }
    }

    // Un-suspend a station
    public void unsuspendStation(int stationId, List<PreventStation> stations) {
        for (PreventStation station : stations) {
            if (station.hashCode() == stationId && station.isSuspended()) {
                station.unsuspend();
            }
        }
    }
}
