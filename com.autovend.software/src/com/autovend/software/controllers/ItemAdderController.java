// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

abstract class ItemAdderController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {
	private CheckoutController mainController;

	public ItemAdderController(D newDevice) {
		super(newDevice);
	}

	public final CheckoutController getMainController() {
		return this.mainController;
	}

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
