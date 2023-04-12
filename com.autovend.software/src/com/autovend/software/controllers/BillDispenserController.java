// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.Bill;
import com.autovend.devices.BillDispenser;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.observers.BillDispenserObserver;

import java.math.BigDecimal;

public class BillDispenserController extends ChangeDispenserController<BillDispenser, BillDispenserObserver>
		implements BillDispenserObserver {
	
	/**
	 * Constructor for the BillDispenserController
	 * @param newDevice
	 * 		The BillDispenser to connect
	 * @param denom
	 * 		The denomination
	 */
	public BillDispenserController(BillDispenser newDevice, BigDecimal denom) {
		super(newDevice, denom);
	}

	@Override
	public void emitChange() {
		try {
			this.getDevice().emit();
		} catch (EmptyException ex) {
			this.getMainController().changeDispenseFailed(this, this.getDenom());
		} catch (OverloadException ex) {
			System.out.println("This can't physically happen, something went wrong.");
		}
	}

	@Override
	public void reactToBillsFullEvent(BillDispenser dispenser) {
	}

	@Override
	public void reactToBillsEmptyEvent(BillDispenser dispenser) {

	}

	@Override
	public void reactToBillAddedEvent(BillDispenser dispenser, Bill bill) {
	}

	@Override
	public void reactToBillRemovedEvent(BillDispenser dispenser, Bill bill) {
	}

	@Override
	public void reactToBillsLoadedEvent(BillDispenser dispenser, Bill... bills) {
	}

	@Override
	public void reactToBillsUnloadedEvent(BillDispenser dispenser, Bill... bills) {
	}
}