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
* Sahaj Malhotra () 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
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
