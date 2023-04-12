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

import java.util.ArrayList;
import java.util.List;

import com.autovend.software.controllers.CheckoutController;

public class PreventPermitStation{

    /**
     * Method for checking if the station is suspended
     * @return isSuspended
     * 		Boolean value of if the station is suspended
     */

    public boolean isSuspended(CheckoutController controller) {
        return !controller.systemAvailableForCustomerUse;
    }

    public boolean isSessionInProgress(CheckoutController controller) {
        return controller.sessionInProgress;
    }

    public boolean suspend(CheckoutController controller) {
        if (!controller.sessionInProgress) {
            controller.systemAvailableForCustomerUse = false;
            controller.systemProtectionLock = true;
            return true;
        } else return false;
    }

    public void forceSuspend(CheckoutController controller) {
        controller.systemAvailableForCustomerUse = false;
        controller.systemProtectionLock = true;
    }

    public void unsuspend(CheckoutController controller) {
    	if (!controller.systemAvailableForCustomerUse) {
    		controller.systemAvailableForCustomerUse = true;
    		controller.systemProtectionLock = false;
    	}
    }
}
