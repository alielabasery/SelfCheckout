package com.autovend.software.controllers;

import com.autovend.devices.SimulationException;
import java.util.HashMap;

public class AttendantLoginLogoutController {
    private Boolean attendantLoggedIn = false;
    public static final HashMap<String, String> idAndPasswords = new HashMap<>();
    public void AttendantLogin(String attendantId, String password) {
        if (!idAndPasswords.containsKey(attendantId)) {
            throw new SimulationException("ID does not exist in the system!");
        }
        if (!(idAndPasswords.get(attendantId) == password)) {
            throw new SimulationException("Wrong password!");
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
