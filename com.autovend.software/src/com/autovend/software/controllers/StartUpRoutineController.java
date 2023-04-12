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
package com.autovend.software.controllers;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;
import com.autovend.devices.SupervisionStation;

public class StartUpRoutineController {


		private SupervisionStation attendentStationHarware;
		private AttendantLoginLogoutController attendantLoginLogoutController;
		
		/**
		 * The constructor for the StartUpRoutineController
		 * @param attendentStationHarware
		 * 		The attendants station
		 * @param attendantLoginLogoutController
		 * 		The login/logout controller for the attendant
		 */
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
		 * @param confirm
		 * 		The confirmation
		 * @return
		 * 		The new checkout controller instance
		 */

		public CheckoutController runsStartUpRoutine(SelfCheckoutStation selfCheckoutStationHardware, boolean confirm) {
			// Ensure that Attendant is Authenticated
			if(!attendantLoginLogoutController.getLoggedIn()) {
				throw new SimulationException("ERROR: Start up failed, attendant not authenticated.");
			}
			// Ensure that Attendant is responsible for self checkout station being started
			if(!attendentStationHarware.supervisedStations().contains(selfCheckoutStationHardware)) {
				throw new SimulationException("ERROR: Start up failed, attendant not supervising this station.");
			}
			
			// Create new instance of Self Checkout Station Driver 
			CheckoutController selfCheckoutStationController = new CheckoutController(selfCheckoutStationHardware); 
			
			// Set station not available for customer usage
			selfCheckoutStationController.systemAvailableForCustomerUse = false; 
			
			// Return instance of Self Checkout Station Driver for given station
			return (selfCheckoutStationController); 
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
		
		/**
		 * Disables the station for customer use
		 * @param selfCheckoutStationController
		 * 		The station to disable
		 */
		public void disableStationForCustomerUse(CheckoutController selfCheckoutStationController) {
			// Set station not available for customer usage
			selfCheckoutStationController.systemAvailableForCustomerUse = false; 
		}

	}
