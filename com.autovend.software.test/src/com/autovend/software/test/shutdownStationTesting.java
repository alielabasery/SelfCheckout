package com.autovend.software.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;
import com.autovend.devices.SupervisionStation;
import com.autovend.software.controllers.AttendantLoginLogoutController;
import com.autovend.software.controllers.AttendantShutdownStationController;

public class shutdownStationTesting {
	
	//Variables needed for setting up testing
	private AttendantShutdownStationController attendantShutdownStationController;
	private AttendantLoginLogoutController attendantLoginLogoutController;
	private SupervisionStation supervisionStation;
	
	private Currency currency;
	private int[] billDenominations;
	private BigDecimal[] coinDenominations;
	private SelfCheckoutStation station;
	
	private final static String attendantID = "001";
    private final static String attendantPassword = "Password123@";

    @Before
    public void setup() {
    	//Initialize the variables to be used before each test
    	attendantLoginLogoutController = new AttendantLoginLogoutController();
        supervisionStation = new SupervisionStation();
        attendantShutdownStationController = new AttendantShutdownStationController(
        		supervisionStation, attendantLoginLogoutController);
        
    	currency = Currency.getInstance("CAD");
		billDenominations = new int[] { 5, 10, 20, 50, 100 };
		coinDenominations = new BigDecimal[] { new BigDecimal(25), new BigDecimal(100), new BigDecimal(5) };
    	station = new SelfCheckoutStation(currency, billDenominations, coinDenominations, 1000, 1);
    	
    	supervisionStation.add(station);
    	
    	AttendantLoginLogoutController.idAndPasswords.put(attendantID, attendantPassword);
    }
    
    @After
    public void teardown() {
    	attendantShutdownStationController = null;
    	attendantLoginLogoutController = null;
    	supervisionStation = null;
    	
    	AttendantLoginLogoutController.idAndPasswords.clear();
    }
    
    @Test
    public void testValidShutdown() {
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.shutdownStation(station, false);
        assertTrue(attendantShutdownStationController.isShutdown());
    }
    
    @Test
    public void testConfirmShutdown() {
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(true);
    	attendantShutdownStationController.shutdownStation(station, true);
        assertTrue(attendantShutdownStationController.isShutdown());
    }
    
    @Test
    public void testSessionInProgress() {
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(true);
    	assertTrue(attendantShutdownStationController.isSessionInProgress());
    }
    
    @Test
    public void testSessionNotInProgress() {
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(false);
    	assertFalse(attendantShutdownStationController.isSessionInProgress());
    }
    
    @Test (expected = SimulationException.class)
    public void testShutdownNotLoggedIn() {
    	attendantShutdownStationController.shutdownStation(station, false);
        assertFalse(attendantShutdownStationController.isShutdown());
    }
    
    @Test (expected = SimulationException.class)
    public void testUnsupervisedShutdown() {
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	supervisionStation.remove(station);
    	attendantShutdownStationController.shutdownStation(station, false);
    	assertFalse(attendantShutdownStationController.isShutdown());
    }
    
    @Test (expected = SimulationException.class)
    public void testInUseShutdown() {
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(true);
    	attendantShutdownStationController.shutdownStation(station, false);
    	assertFalse(attendantShutdownStationController.isShutdown());
    }
}
