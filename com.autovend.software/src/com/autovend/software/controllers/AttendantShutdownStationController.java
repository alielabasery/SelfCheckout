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
	
	
	public AttendantShutdownStationController(SupervisionStation superStation, AttendantLoginLogoutController attlog) {
		this.attendantStation = superStation;
		this.attendantlog = attlog;
		this.shutdown = false;
		this.sessionInProgress = false;
	}
	
	/*
	 *Shuts down a given station. Checks whether an attendant is logged in and whether the station is
	 *being supervised. Also check whether a station is in use.
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
	
	// Implemented in gui. Attendant is asked if they want to confirm shutdown when
	// a customer session is in progress
	public boolean confirmShutdown(boolean confirm) {
		return confirm;
	}
	
	public boolean isShutdown() {
		return shutdown;
	}
	
	public void setSessionInProgress(boolean session) {
        this.sessionInProgress = session;
    }
	
	public boolean isSessionInProgress() {
		return sessionInProgress;
	}
}
