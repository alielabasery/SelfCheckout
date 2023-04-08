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
    	
    	//Add attendant login info to database
    	AttendantLoginLogoutController.idAndPasswords.put(attendantID, attendantPassword);
    }
    
    @After
    public void teardown() {
    	//Make variables ready for garbage collector after each test
    	attendantShutdownStationController = null;
    	attendantLoginLogoutController = null;
    	supervisionStation = null;
    	
    	AttendantLoginLogoutController.idAndPasswords.clear();
    }
    
    @Test
    public void testValidShutdown() {
    	//Login the attendant and shutdown the station
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.shutdownStation(station, false);
        assertTrue(attendantShutdownStationController.isShutdown());
    }
    
    @Test
    public void testConfirmShutdown() {
    	//Shutdown the station while still in use by confirming shutdown
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(true);
    	attendantShutdownStationController.shutdownStation(station, true);
        assertTrue(attendantShutdownStationController.isShutdown());
    }
    
    @Test
    public void testSessionInProgress() {
    	//Test if session in progress is updated to true
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(true);
    	assertTrue(attendantShutdownStationController.isSessionInProgress());
    }
    
    @Test
    public void testSessionNotInProgress() {
    	//Test if session in progress is updates to false
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(false);
    	assertFalse(attendantShutdownStationController.isSessionInProgress());
    }
    
    @Test (expected = SimulationException.class)
    public void testShutdownNotLoggedIn() {
    	//Try shutting down the station without an attendant being logged in
    	attendantShutdownStationController.shutdownStation(station, false);
        assertFalse(attendantShutdownStationController.isShutdown());
    }
    
    @Test (expected = SimulationException.class)
    public void testUnsupervisedShutdown() {
    	//Try shutting down the station without the station being supervised
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	supervisionStation.remove(station);
    	attendantShutdownStationController.shutdownStation(station, false);
    	assertFalse(attendantShutdownStationController.isShutdown());
    }
    
    @Test (expected = SimulationException.class)
    public void testInUseShutdown() {
    	//Try shutting down the station which is in use and not overriding shutdown
    	attendantLoginLogoutController.AttendantLogin(attendantID, attendantPassword);
    	attendantShutdownStationController.setSessionInProgress(true);
    	attendantShutdownStationController.shutdownStation(station, false);
    	assertFalse(attendantShutdownStationController.isShutdown());
    }
}