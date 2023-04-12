/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
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
