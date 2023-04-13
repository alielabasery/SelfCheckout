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
package com.autovend.software;

import java.util.ArrayList;
import java.util.List;

import com.autovend.software.controllers.CheckoutController;

public class PreventStation extends CheckoutController {
	private static List<PreventStation> suspendedStations = new ArrayList<>();
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

    public boolean isSessionInProgress() {
        return isSessionInProgress;
    }

    public void setSessionInProgress(boolean isSessionInProgress) {
        this.isSessionInProgress = isSessionInProgress;
    }

    public void suspend(CheckoutController controller) {
        if (!isSessionInProgress || isSuspended) {
            isSuspended = true;
            suspendedStations.add(this);
            System.out.println("Station ID: " + this.hashCode() + " is now suspended.");
            // Notify the CheckoutController about the suspension
            controller.onStationSuspended(this);
        } else {
            System.out.println("Cannot suspend Station ID: " + this.hashCode() + " as it has an ongoing session.");
        }
    }

    public void forceSuspend(CheckoutController controller) {
        isSuspended = true;
        controller.onStationSuspended(this);
        System.out.println("Station ID: " + this.hashCode() + " is now force suspended.");
    }

    public void unsuspend(CheckoutController controller) {
        if (isSuspended) {
            isSuspended = false;
            suspendedStations.remove(this);
            System.out.println("Station ID: " + this.hashCode() + " is now un-suspended.");
            controller.onStationUnsuspended(this);
        } else {
            System.out.println("Station ID: " + this.hashCode() + " is not suspended.");
        }
    }
    public static List<PreventStation> getSuspendedStations() {
        return suspendedStations;
    }
}
