// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.CoinTray;
import com.autovend.devices.observers.CoinTrayObserver;

public class CoinTrayController extends ChangeSlotController<CoinTray, CoinTrayObserver> implements CoinTrayObserver {
	public CoinTrayController(CoinTray coinTray) {
		super(coinTray);
	}

	@Override
	public void reactToCoinAddedEvent(CoinTray tray) {
		// if stuff in coin tray, then check if the tray is full and if not then tell
		// the checkout controller
		// to give more change
		if (tray != this.getDevice()) {
			return;
		}
		;
		if (tray.hasSpace()) {
			this.getMainController().dispenseChange(this);
		}
	}
}
