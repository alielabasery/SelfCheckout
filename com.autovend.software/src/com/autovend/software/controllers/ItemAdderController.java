// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

abstract class ItemAdderController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {
	private CheckoutController mainController;

	/**
	 * The constructor for the ItemAdderController
	 * @param newDevice
	 * 		The device being used
	 */
	public ItemAdderController(D newDevice) {
		super(newDevice);
	}

	/**
	 * Gets the main checkout controller
	 * @return
	 * 		The main checkout controller
	 */
	public final CheckoutController getMainController() {
		return this.mainController;
	}

	/**
	 * Sets the main controller
	 * @param newMainController
	 * 		The CheckoutController to be set as the main controller
	 */
	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterItemAdderController(this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerItemAdderController(this);
		}
	}
}
