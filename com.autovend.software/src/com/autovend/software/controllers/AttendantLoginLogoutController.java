package com.autovend.software.controllers;

import com.autovend.devices.SimulationException;
import java.util.HashMap;

public class AttendantLoginLogoutController {
    private Boolean attendantLoggedIn = false;
    public static final HashMap<String, String> idAndPasswords = new HashMap<>();
    public void AttendantLogin(String attendantId, String password) {
        if (!idAndPasswords.containsKey(attendantId)) {
            throw new SimulationException("Incorrect ID or password!");
        }
        if (!(idAndPasswords.get(attendantId).equals(password))) {
            throw new SimulationException("Incorrect ID or password!");
        }
        attendantLoggedIn = true;
    }

    public void AttendantLogout() {
        if (attendantLoggedIn) {attendantLoggedIn = false;}
        else {throw new SimulationException("Nobody is logged in!");}
    }

    public Boolean getLoggedIn() {
        return attendantLoggedIn;
    }
}
