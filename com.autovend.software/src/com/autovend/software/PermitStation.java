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

import com.autovend.software.PreventStation;
import java.util.List;

public class PermitStation {

    public PermitStation() {
    }

    /**
     * Display the status of all stations
     * @param stations
     * 		The list of stations
     */
    public void displayStations(List<PreventStation> stations) {
        System.out.println("Stations status:");
        for (PreventStation station : stations) {
            System.out.println("Station ID: " + station.hashCode() + ", Suspended: " + station.isSuspended() + ", Session in progress: " + station.isSessionInProgress());
        }
        System.out.println();
    }

    /**
     * Display the list of suspended stations
     * @param stations
     * 		The list of stations
     */
    public void displaySuspendedStations(List<PreventStation> stations) {
        System.out.println("Suspended stations:");
        for (PreventStation station : stations) {
            if (station.isSuspended()) {
                System.out.println("Station ID: " + station.hashCode());
            }
        }
    }

    /**
     *  Un-suspend a station
     * @param stationId
     * 		The ID of the station to unsuspend
     * @param stations
     * 		The list of stations
     */
    public void unsuspendStation(int stationId, List<PreventStation> stations) {
        for (PreventStation station : stations) {
            if (station.hashCode() == stationId && station.isSuspended()) {
                station.unsuspend();
            }
        }
    }
}
