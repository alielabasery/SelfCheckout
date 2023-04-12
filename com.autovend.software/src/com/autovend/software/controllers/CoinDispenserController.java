// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.Coin;
import com.autovend.devices.CoinDispenser;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.observers.CoinDispenserObserver;

import java.math.BigDecimal;

public class CoinDispenserController extends ChangeDispenserController<CoinDispenser, CoinDispenserObserver>
		implements CoinDispenserObserver {
	
	/**
	 * The CoinDispenserController constructor
	 * @param newDevice
	 * 		The CoinDispenser device
	 * @param denom
	 * 		The denomination of the controller
	 */
	public CoinDispenserController(CoinDispenser newDevice, BigDecimal denom) {
		super(newDevice, denom);
	}

	@Override
	public void reactToCoinsFullEvent(CoinDispenser dispenser) {
	}

	@Override
	public void reactToCoinsEmptyEvent(CoinDispenser dispenser) {
	}

	@Override
	public void reactToCoinAddedEvent(CoinDispenser dispenser, Coin coin) {
	}

	@Override
	public void reactToCoinRemovedEvent(CoinDispenser dispenser, Coin coin) {
	}

	@Override
	public void reactToCoinsLoadedEvent(CoinDispenser dispenser, Coin... coins) {
	}

	@Override
	public void reactToCoinsUnloadedEvent(CoinDispenser dispenser, Coin... coins) {
	}

	@Override
	void emitChange() {
		try {
			this.getDevice().emit();
		} catch (EmptyException ex) {
			this.getMainController().changeDispenseFailed(this, this.getDenom());
		} catch (OverloadException ex) {
			System.out.println("This can't physically happen, something went wrong.");
		}
	}
}
