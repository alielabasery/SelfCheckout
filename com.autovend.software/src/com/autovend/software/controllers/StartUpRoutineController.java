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

package com.autovend.software.controllers;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StartUpRoutineController {


		private SupervisionStation attendentStationHarware;
		private AttendantLoginLogoutController attendantLoginLogoutController;
		

		public StartUpRoutineController(SupervisionStation attendentStationHarware, AttendantLoginLogoutController attendantLoginLogoutController) {
			this.attendentStationHarware        = attendentStationHarware;
			this.attendantLoginLogoutController = attendantLoginLogoutController;
		}

		/**
		 * Returns a fresh instance of the main Self Checkout Station Controller 
		 * CheckoutController ensuring that the Attendant making the request has successfully authenticated and is responsible for
		 *  the given Self Checkout Station
		 * @param selfCheckoutStationHardware
		 * 		The Self checkout hardware
		 * @param initialStartUp
		 * 		Boolean indicating if the current startup is the initial start up 
		 * @return
		 * 		The new checkout controller instance
		 * @throws StartUpRoutineFailedException 
		 */

		public CheckoutController runStartUpRoutine(SelfCheckoutStation selfCheckoutStationHardware, String station_name, boolean initialStartUp) throws StartUpRoutineFailedException {
			// Ensure that Attendant is Authenticated
			if(!attendantLoginLogoutController.getLoggedIn()) {
				throw new StartUpRoutineFailedException("ERROR: Start up failed -> attendant not authenticated.");
			} 
			// Ensure that Attendant is responsible for self checkout station being started
			if(!attendentStationHarware.supervisedStations().contains(selfCheckoutStationHardware)) {
				throw new StartUpRoutineFailedException("ERROR: Start up failed -> attendant not supervising this station.");
			}
			

			CheckoutController selfCheckoutStationController = new CheckoutController(station_name, selfCheckoutStationHardware); 
			
			// Set station not available for customer usage
			disableStationForCustomerUse(selfCheckoutStationController);  
			
			if (!initialStartUp) { 
				try {
					// Try to load in state from file
					int[] station_state = loadSelfCheckoutStationState(station_name); 
					
					// Load in previously saved printer ink/paper state before last shutdown
					selfCheckoutStationController.getReceiptPrinter().estimatedInk   = station_state[0]; 
					selfCheckoutStationController.getReceiptPrinter().estimatedPaper = station_state[1];
				} catch (IOException e) {
					// Throw new exception upon failure 
					throw new StartUpRoutineFailedException("ERROR: Start up failed -> failed to load in previously saved state."); 
				}
			}
			
			// Return instance of Self Checkout Station Driver for given station
			return (selfCheckoutStationController); 
		}
		

		/**
		 * Saves the state of a self-checkout station to a file.
		 * The state consists of two integers representing the ink state and paper state.
		 * The values are saved to a file named "STATE_{@code station_name}.txt".
		 *
		 * @param ink_state The state of the ink, as an integer.
		 * @param paper_state The state of the paper, as an integer.
		 * @param station_name The name of the self-checkout station.
		 * @throws IOException If an I/O error occurs while writing to the file.
		 */
		public static void saveSelfCheckoutStationState(int ink_state, int paper_state, String station_name) throws IOException {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("STATE_" + station_name + ".txt"))) {
	            writer.write(ink_state + "\n" + paper_state);
	        }
	    }
		
		/**
		 * Loads the state of a self-checkout station from a file.
		 * The state consists of two integers representing the ink state and paper state.
		 * The values are read from a file named "STATE_{@code station_name}.txt".
		 *
		 * @param station_name The name of the self-checkout station.
		 * @return An array of two integers, where the first element is the ink state
		 *         and the second element is the paper state.
		 * @throws IOException If an I/O error occurs while reading from the file.
		 */

	    public static int[] loadSelfCheckoutStationState(String station_name) throws IOException {
	        int[] state_array = new int[2];
	        try (BufferedReader reader = new BufferedReader(new FileReader("STATE_" + station_name + ".txt"))) {
	            state_array[0] = Integer.parseInt(reader.readLine());
	            state_array[1] = Integer.parseInt(reader.readLine());
	        }
	        return state_array;
	    }
		
		/**
		 * Enables the station for customer use
		 * @param selfCheckoutStationController
		 * 		The checkout station to enable
		 */
	    
		public void enableStationForCustomerUse (CheckoutController selfCheckoutStationController) {
			// Set station available for customer usage
			selfCheckoutStationController.systemAvailableForCustomerUse = true; 
		}
		
		public void disableStationForCustomerUse(CheckoutController selfCheckoutStationController) {
			// Set station not available for customer usage
			selfCheckoutStationController.systemAvailableForCustomerUse = false; 
		}

	}