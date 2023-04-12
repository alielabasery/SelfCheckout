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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.software.PreventPermitStation;
import com.autovend.software.controllers.CheckoutController;

public class PreventPermitStationTest {
	
	private Currency currency;
	private int[] billDenominations;
	private BigDecimal[] coinDenominations;
	private SelfCheckoutStation station;
	private CheckoutController controller;
	
	PreventPermitStation preventPermitStation;
	
	@Before
	public void setup() {
		currency = Currency.getInstance("CAD");
		billDenominations = new int[] { 5, 10, 20, 50, 100 };
		coinDenominations = new BigDecimal[] { new BigDecimal(25), new BigDecimal(100), new BigDecimal(5) };
    	station = new SelfCheckoutStation(currency, billDenominations, coinDenominations, 1000, 1);
    	controller = new CheckoutController("Station 1", station);
    	preventPermitStation = new PreventPermitStation();
	}
	
	@Test
    public void testIsSuspended() {
        controller.systemAvailableForCustomerUse = true;
        assertFalse(preventPermitStation.isSuspended(controller));
    }
	
	@Test
    public void testIsSuspendedFalse() {
        controller.systemAvailableForCustomerUse = false;
        assertTrue(preventPermitStation.isSuspended(controller));
    }

	@Test
    public void testisSessionInProgress() {
		controller.sessionInProgress = true;
        assertTrue(preventPermitStation.isSessionInProgress(controller));
    }
	
	@Test
    public void testisSessionInProgressFalse() {
		controller.sessionInProgress = false;
        assertFalse(preventPermitStation.isSessionInProgress(controller));
    }
	
	@Test
    public void testSuspend() {
		controller.sessionInProgress = false;
        assertTrue(preventPermitStation.suspend(controller));
        assertTrue(preventPermitStation.isSuspended(controller));
    }
	
	@Test
    public void testSuspendInUse() {
		controller.sessionInProgress = true;
        assertFalse(preventPermitStation.suspend(controller));
        assertFalse(preventPermitStation.isSuspended(controller));
    }
	
	@Test
    public void testSuspendForced() {
		controller.sessionInProgress = true;
        preventPermitStation.forceSuspend(controller);
        assertTrue(preventPermitStation.isSuspended(controller));
    }
	
	@Test
    public void testUnsuspend() {
		controller.systemAvailableForCustomerUse = false;
        preventPermitStation.unsuspend(controller);
        assertTrue(controller.systemAvailableForCustomerUse);
    }
	
	@Test
    public void testUnsuspendAlready() {
		controller.systemAvailableForCustomerUse = true;
        preventPermitStation.unsuspend(controller);
        assertTrue(controller.systemAvailableForCustomerUse);
    }
}
