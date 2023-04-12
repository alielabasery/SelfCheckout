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

import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.BillDispenser;
import com.autovend.devices.BillSlot;
import com.autovend.devices.BillStorage;
import com.autovend.devices.BillValidator;
import com.autovend.devices.CardReader;
import com.autovend.devices.CoinDispenser;
import com.autovend.devices.CoinSlot;
import com.autovend.devices.CoinStorage;
import com.autovend.devices.CoinTray;
import com.autovend.devices.CoinValidator;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SimulationException;
import com.autovend.devices.SupervisionStation;
import com.autovend.devices.TouchScreen;

public class AttendantShutdownStationController {

	private SupervisionStation attendantStation;
	private boolean shutdown;
	private AttendantLoginLogoutController attendantlog;
	private boolean sessionInProgress;
	
	/**
	 * The Constructor for AttendantShutdownStationController
	 * @param superStation
	 * 		The station of the attendant
	 * @param attlog
	 * 		the attendant login/logout controller
	 */
	public AttendantShutdownStationController(SupervisionStation superStation, AttendantLoginLogoutController attlog) {
		this.attendantStation = superStation;
		this.attendantlog = attlog;
		this.shutdown = false;
		this.sessionInProgress = false;
	}
	
	/**
	 * Shuts down a given station. Checks whether an attendant is logged in and whether the station is
	 * being supervised. Also check whether a station is in use.
	 * @param station
	 * 		The station to shutdown
	 * @param confirm
	 * 		Boolean to confirm shutdown
	 */

	public void shutdownStation(SelfCheckoutStation station, boolean confirm) {
		// check if an attendant is logged in
		if(!attendantlog.getLoggedIn()) {
			throw new SimulationException("No attendant logged in");
		}
		// check if the station is being supervised
		if(!attendantStation.supervisedStations().contains(station)) {
			throw new SimulationException("Given station is not being supervised");
		}
		// check if station is currently in use
		if(sessionInProgress) {
			// if confirmed to shutdown by attendant, continue to shutdown
			if(confirmShutdown(confirm)) {
				this.shutdown = true;
			}
			// else, allow customer to continue
			else {
				throw new SimulationException("Given station is still in use");
			}
		}
		//begin shutdown by disabling all devices
		this.shutdown = true;
		station.scale.disable();
		station.baggingArea.disable();
		station.screen.disable();
		station.printer.disable();
		station.cardReader.disable();
		station.mainScanner.disable();
		station.handheldScanner.disable();
		station.billInput.disable();
		station.billValidator.disable();
		station.billStorage.disable();
		station.billOutput.disable();
		for(BillDispenser dispenser : station.billDispensers.values()) {
			dispenser.disable();
		}
		station.coinSlot.disable();
		station.coinValidator.disable();
		station.coinStorage.disable();
		for (CoinDispenser dispenser : station.coinDispensers.values()) {
			dispenser.disable();
		}
		
	}
	
	/**
	 * Implemented in gui. Attendant is asked if they want to confirm shutdown whena customer session is in progress
	 * @param confirm
	 * 		Confirmation to shutdown
	 * @return
	 * 		Returns the confirmation
	 */
	public boolean confirmShutdown(boolean confirm) {
		return confirm;
	}
	
	/**
	 * Returns if the station is shutdown
	 * @return
	 * 		Boolean value true if the station is shutdown
	 */
	public boolean isShutdown() {
		return shutdown;
	}
	
	/**
	 * Sets a session in progress
	 * @param session
	 * 		Boolean if a session is in progress
	 */
	public void setSessionInProgress(boolean session) {
        this.sessionInProgress = session;
    }
	
	/**
	 * returns if a session is in progress
	 * @return
	 * 		Boolean value, True if a session is in progress
	 */
	public boolean isSessionInProgress() {
		return sessionInProgress;
	}
}
