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
import org.junit.Test;
import com.autovend.software.PreventStation;

public class preventStationTest {

	
	@Test
    public void testConstructor() {
        PreventStation station = new PreventStation();
        assertFalse(station.isSuspended());
        assertFalse(station.isSessionInProgress());
    }

	  @Test
	    public void testSuspend() {
	        PreventStation station = new PreventStation();
	        assertFalse(station.isSuspended());
	        station.suspend();
	        assertTrue(station.isSuspended());
	        station.setSessionInProgress(true);
	        station.suspend();
	        assertTrue(station.isSuspended());
	        station.setSessionInProgress(false);
	        station.suspend();
	        assertTrue(station.isSuspended());
	    }
	
    @Test
    public void testSetSessionInProgress() {
        PreventStation station = new PreventStation();
        assertFalse(station.isSessionInProgress());
        station.setSessionInProgress(true);
        assertTrue(station.isSessionInProgress());
        station.setSessionInProgress(false);
        assertFalse(station.isSessionInProgress());
    }

    @Test
    public void testForceSuspend() {
        PreventStation station = new PreventStation();
        assertFalse(station.isSuspended());
        station.forceSuspend();
        assertTrue(station.isSuspended());
    }

    @Test
    public void testUnsuspend() {
        PreventStation station = new PreventStation();
        assertFalse(station.isSuspended());
        station.forceSuspend();
        assertTrue(station.isSuspended());
        station.unsuspend();
        assertFalse(station.isSuspended());
    }

    @Test
    public void testCannotSuspendWithSessionInProgress() {
        PreventStation station = new PreventStation();
        assertFalse(station.isSuspended());
        station.setSessionInProgress(true);
        station.suspend();
        assertTrue(station.isSessionInProgress());
        assertFalse(station.isSuspended());
        station.setSessionInProgress(false);
        station.suspend();
        assertTrue(station.isSuspended());
    }

}
