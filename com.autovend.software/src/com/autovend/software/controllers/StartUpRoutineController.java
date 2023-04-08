package com.autovend.software.controllers;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;
import com.autovend.devices.SupervisionStation;

public class StartUpRoutineController {


		private SupervisionStation attendentStationHarware;
		private AttendantLoginLogoutController attendantLoginLogoutController;
		

		public StartUpRoutineController(SupervisionStation attendentStationHarware, AttendantLoginLogoutController attendantLoginLogoutController) {
			this.attendentStationHarware        = attendentStationHarware;
			this.attendantLoginLogoutController = attendantLoginLogoutController;
		}

		/*
		 * Returns a fresh instance of the main Self Checkout Station Controller (CheckoutController)
		 * ensuring that the Attendant making the request has successfully authenticated and is responsible for 
		 * the given Self Checkout Station
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
		
		public void enableStationForCustomerUse (CheckoutController selfCheckoutStationController) {
			// Set station available for customer usage
			selfCheckoutStationController.systemAvailableForCustomerUse = true; 
		}
		
		public void disableStationForCustomerUse(CheckoutController selfCheckoutStationController) {
			// Set station not available for customer usage
			selfCheckoutStationController.systemAvailableForCustomerUse = false; 
		}

	}
