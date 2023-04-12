// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

abstract public class ChangeSlotController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {
	
	/**
	 * Constructor for the ChangeSlotController
	 * @param newDevice
	 * 		The device to connect
	 */
	public ChangeSlotController(D newDevice) {
		super(newDevice);
	}

	private CheckoutController mainController;

	/**
	 * Returns the main controller
	 * @return
	 * 		The main CheckoutController
	 */
	final CheckoutController getMainController() {
		return this.mainController;
	};

	/**
	 * Sets the main checkout controller
	 * @param newMainController
	 * 		The CheckoutController to set as the main controller
	 */
	final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterChangeSlotController(this);
		}
		;
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerChangeSlotController(this);
		}
		;
	}
}
