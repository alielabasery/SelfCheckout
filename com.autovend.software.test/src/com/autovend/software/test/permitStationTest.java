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

package com.autovend.software.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.software.PermitStation;
import com.autovend.software.PreventStation;


public class permitStationTest {

	@Test
    public void testDisplayStations() {
        PreventStation station1 = new PreventStation();
        PreventStation station2 = new PreventStation();
        List<PreventStation> stations = new ArrayList<PreventStation>();
        stations.add(station1);
        stations.add(station2);
        PermitStation permitStation = new PermitStation();
        permitStation.displayStations(stations);
        // Ensure the method is printing out the status of each station
        assertTrue(systemOut().contains("Station ID: " + station1.hashCode() + ", Suspended: false, Session in progress: false"));
        assertTrue(systemOut().contains("Station ID: " + station2.hashCode() + ", Suspended: false, Session in progress: false"));
    }
	
	@Test
    public void testDisplayStations_sessionInProgress() {
        PreventStation station1 = new PreventStation();
        PreventStation station2 = new PreventStation();
        List<PreventStation> stations = new ArrayList<PreventStation>();
        stations.add(station1);
        stations.add(station2);
        PermitStation permitStation = new PermitStation();
        station1.setSessionInProgress(true);
        permitStation.displayStations(stations);
        // Ensure the method is printing out the status of each station
        assertTrue(systemOut().contains("Station ID: " + station1.hashCode() + ", Suspended: false, Session in progress: true"));
        assertFalse(systemOut().contains("Station ID: " + station1.hashCode() + ", Suspended: false, Session in progress: false"));
        assertTrue(systemOut().contains("Station ID: " + station2.hashCode() + ", Suspended: false, Session in progress: false"));
    }

    @Test
    public void testDisplaySuspendedStations() {
        PreventStation station1 = new PreventStation();
        PreventStation station2 = new PreventStation();
        PreventStation station3 = new PreventStation();
        station2.forceSuspend();
        station3.forceSuspend();
        List<PreventStation> stations = new ArrayList<PreventStation>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        PermitStation permitStation = new PermitStation();
        permitStation.displaySuspendedStations(stations);
        // Ensure the method is printing out the IDs of suspended stations only
        assertFalse(systemOut().contains("Station ID: " + station1.hashCode()));
        assertTrue(systemOut().contains("Station ID: " + station2.hashCode()));
        assertTrue(systemOut().contains("Station ID: " + station3.hashCode()));
    }

    @Test
    public void testUnsuspendStation() {
        PreventStation station1 = new PreventStation();
        PreventStation station2 = new PreventStation();
        PreventStation station3 = new PreventStation();
        station2.forceSuspend();
        List<PreventStation> stations = new ArrayList<PreventStation>();
        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        PermitStation permitStation = new PermitStation();
        permitStation.unsuspendStation(station2.hashCode(), stations);
        //Check if the station is unsuspended successfully
        assertFalse(station2.isSuspended());
        //Check if any other stations are suspended
        assertFalse(station1.isSuspended());
        assertFalse(station3.isSuspended());
    }

    //capturing System.out output to compare
    private static String systemOut() {
        return finalContent.toString();
    }

    //resetting System.out after each test
    @After
    public void resetOutStream() {
        System.setOut(System.out);
    }

    //helper variables for capturing System.out
    private final static ByteArrayOutputStream finalContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //redirecting System.out to a stream
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(finalContent));
    }
	
}

