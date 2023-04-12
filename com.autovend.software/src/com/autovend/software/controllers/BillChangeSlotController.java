// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.BillSlot;
import com.autovend.devices.observers.BillSlotObserver;

public class BillChangeSlotController extends ChangeSlotController<BillSlot, BillSlotObserver>
		implements BillSlotObserver {
	
	/**
	 * The constructor for the BillChangeSlotController
	 * @param newDevice
	 * 		The BillSlot to connect
	 */
	public BillChangeSlotController(BillSlot newDevice) {
		super(newDevice);
	}

	@Override
	public void reactToBillInsertedEvent(BillSlot slot) {
	}

	@Override
	public void reactToBillEjectedEvent(BillSlot slot) {
	}

	@Override
	public void reactToBillRemovedEvent(BillSlot slot) {
		this.getMainController().dispenseChange(this);
	}
}
