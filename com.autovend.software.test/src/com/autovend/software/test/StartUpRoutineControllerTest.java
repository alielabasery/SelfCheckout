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

package com.autovend.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import org.junit.*;
import org.junit.Assert.*; 

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.software.controllers.AttendantLoginLogoutController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.StartUpRoutineController;
import com.autovend.software.controllers.StartUpRoutineFailedException;

import java.io.File;

public class StartUpRoutineControllerTest {
	
		// SET attendant default credentials
		private final String DEFAULT_USERNAME = "USERNAME"; 
		private final String DEFAULT_PASSWORD = "PASSWORD";
		
		private StartUpRoutineController 		startUpRoutineController; 
		private AttendantLoginLogoutController 	attendantLoginLogoutController;
		private SupervisionStation 				attendentStationHarware;
		private SelfCheckoutStation 			selfCheckoutStation;
		
		private void setup() {
	    	attendantLoginLogoutController 		= new AttendantLoginLogoutController();
	    	// Register attendant credentials in database  
	    	AttendantLoginLogoutController.idAndPasswords.put(DEFAULT_USERNAME, DEFAULT_PASSWORD);
	    	
	    	// Initialize attendant station
	    	attendentStationHarware             = new SupervisionStation();
	    	startUpRoutineController 			= new StartUpRoutineController(
	    			attendentStationHarware, attendantLoginLogoutController);
	        
	    	// Create Self Checkout Station Hardware 
	    	int[] billDenominations 			= new int[] {5, 10, 20, 50, 100};
	    	BigDecimal[] coinDenominations 		= new BigDecimal[] {new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25), new BigDecimal(100), new BigDecimal(200)};
			selfCheckoutStation 				= new SelfCheckoutStation(Currency.getInstance("CAD"), billDenominations, coinDenominations, 200, 1);
			
			// Add Self Checkout Station Hardware to list of attendant supervised stations 
			attendentStationHarware.add(selfCheckoutStation);
	    	    	
		}
		
		private void removeState(String station_name) {
			   File stateFile = new File("STATE_"+ station_name + ".txt");
		        if (stateFile.exists()) {
		            stateFile.delete();
		        }
		}
		
		@Test 
		public void testSuccessfulStartUpInitialTime() {
			// Set up default test state
			setup(); 
			// Log attendant in 
			attendantLoginLogoutController.AttendantLogin(DEFAULT_USERNAME, DEFAULT_PASSWORD);
			// Indicate that this is the first startup
			Boolean initialStartup = true;  
			 
			CheckoutController selfCheckoutStationController = null;
			try {
				// Run start up routine and save result
				selfCheckoutStationController = startUpRoutineController.runStartUpRoutine(selfCheckoutStation, "Station_1", initialStartup);
			} catch (StartUpRoutineFailedException e) {
				fail();
			}
			// Ensure that successfully returned valid instance
			assertNotNull(selfCheckoutStationController);
		}
	
	 	@Test
	    public void testRunStartUpRoutineNotAuthenticated() {
	        setup();
	        try {
	            startUpRoutineController.runStartUpRoutine(selfCheckoutStation, "test_station", true);
	            fail("ERROR: Expected StartUpRoutineFailedException.");
	        } catch (StartUpRoutineFailedException e) {
	            
	        }
	    }

	    @Test
	    public void testRunStartUpRoutineNotSupervising() {
	    	// Set up default test state
			setup(); 
			// Remove Self Checkout Station Hardware to list of attendant supervised stations 
			attendentStationHarware.remove(selfCheckoutStation);
			// Log attendant in 
			attendantLoginLogoutController.AttendantLogin(DEFAULT_USERNAME, DEFAULT_PASSWORD);
			// Indicate that this is the first startup
			Boolean initialStartup = true; 
			
			CheckoutController selfCheckoutStationController = null;
	        try {
	        	// Run start up routine and save result
				selfCheckoutStationController = startUpRoutineController.runStartUpRoutine(selfCheckoutStation, "Station_1", initialStartup);
	            fail("ERROR: Expected StartUpRoutineFailedException.");
	        } catch (StartUpRoutineFailedException e) {
	        	// Success
	        	assertTrue(e.getMessage().contains("attendant not supervising this station")); 
	        }
	    } 
	    
	    @Test
	    public void testSaveLoadState() {
	        setup();
	        // Array to hold state 
	        int[] state = null; 
	        
	        try {
				StartUpRoutineController.saveSelfCheckoutStationState(100, 1000, "Station_1");
				state    = StartUpRoutineController.loadSelfCheckoutStationState("Station_1");
			} catch (IOException e) {
				fail("ERROR: Expected successful saving of state");
			}
	       
	        assertEquals(100,  state[0]);
	        assertEquals(1000, state[1]);
	    } 
	    
	    @Test
	    public void testRunStartUpRoutineNoStateNotInitialStartUp() {
	    	// Set up default test state
			setup(); 
			// Log attendant in 
			attendantLoginLogoutController.AttendantLogin(DEFAULT_USERNAME, DEFAULT_PASSWORD);
			// Indicate that this is the first startup
			Boolean initialStartup = false; 
			
			CheckoutController selfCheckoutStationController = null;
	        try {
	        	// Run start up routine and save result
				selfCheckoutStationController = startUpRoutineController.runStartUpRoutine(selfCheckoutStation, "Station_1", initialStartup);
	            fail("ERROR: Expected StartUpRoutineFailedException.");
	        } catch (StartUpRoutineFailedException e) {
	        	// Success
	        	assertTrue(e.getMessage().contains("failed to load in previously saved state")); 
	        }
	    }
	    
	    @Test
	    public void testRunStartUpRoutineSuccessNotInitialStartUp() {
	    	// Set up default test state
			setup(); 
			// Log attendant in 
			attendantLoginLogoutController.AttendantLogin(DEFAULT_USERNAME, DEFAULT_PASSWORD);
			// Create new state file
			try {
				StartUpRoutineController.saveSelfCheckoutStationState(100, 100, "Station_1");
			} catch (IOException e) {
				fail("ERROR: Expected successful saving of state");
			}
			// Indicate that this is the first startup
			Boolean initialStartup = false; 
			
			CheckoutController selfCheckoutStationController = null;
	        try {
	        	// Run start up routine and save result
				selfCheckoutStationController = startUpRoutineController.runStartUpRoutine(selfCheckoutStation, "Station_1", initialStartup);

	        } catch (StartUpRoutineFailedException e) {
	            fail("ERROR: Expected successful start up");
	        }
	        
	        assertEquals(100, selfCheckoutStationController.getReceiptPrinter().estimatedInk);
	        assertEquals(100, selfCheckoutStationController.getReceiptPrinter().estimatedPaper);
	        
	        // Remove state file after test 
	        removeState("Station_1");
	    }



	    @Test
	    public void testEnableDisableStationForCustomerUse() throws StartUpRoutineFailedException {
	    	// Set up default test state
			setup(); 
			// Log attendant in 
			attendantLoginLogoutController.AttendantLogin(DEFAULT_USERNAME, DEFAULT_PASSWORD);
			// Indicate that this is the first startup
			Boolean initialStartup = true;  
			 
			CheckoutController selfCheckoutStationController = null;
			try {
				// Run start up routine and save result
				selfCheckoutStationController = startUpRoutineController.runStartUpRoutine(selfCheckoutStation, "Station_1", initialStartup);
			} catch (StartUpRoutineFailedException e) {
				fail();
			}
			
			// Enable station for customer use 
			startUpRoutineController.enableStationForCustomerUse(selfCheckoutStationController);
			
			assertTrue(selfCheckoutStationController.systemAvailableForCustomerUse); 
	    }


}