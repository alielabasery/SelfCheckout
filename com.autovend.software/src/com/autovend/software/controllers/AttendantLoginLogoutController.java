package com.autovend.software.controllers;

import com.autovend.devices.SimulationException;
import java.util.HashMap;

public class AttendantLoginLogoutController {
    private Boolean attendantLoggedIn = false;
    public static final HashMap<String, String> idAndPasswords = new HashMap<>();
    
    /**
     * Logs in the Attendant
     * @param attendantId
     * 		ID of the attendant
     * @param password
     * 		The password of the attendant
     */
    public void AttendantLogin(String attendantId, String password) {
        if (!idAndPasswords.containsKey(attendantId)) {
            throw new SimulationException("Incorrect ID or password!");
        }
        if (!(idAndPasswords.get(attendantId).equals(password))) {
            throw new SimulationException("Incorrect ID or password!");
        }
        attendantLoggedIn = true;
    }

    /**
     * Logout the logged in attendant
     */
    public void AttendantLogout() {
        if (attendantLoggedIn) {attendantLoggedIn = false;}
        else {throw new SimulationException("Nobody is logged in!");}
    }

    /**
     * Checks if an attendant is logged in
     * @return
     * 		If an attendant is logged in
     */
    public Boolean getLoggedIn() {
        return attendantLoggedIn;
    }
}
