// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import java.math.BigDecimal;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

abstract public class ChangeDispenserController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {

	private BigDecimal denom;
	private CheckoutController mainController;

	public ChangeDispenserController(D newDevice, BigDecimal denom) {
		super(newDevice);
		this.denom = denom;
	}

	final CheckoutController getMainController() {
		return this.mainController;
	}

	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterChangeDispenserController(this.denom, this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerChangeDispenserController(this.denom, this);
		}
	}

	void setDenom(BigDecimal denom) {
		this.denom = denom;
	}

	BigDecimal getDenom() {
		return this.denom;
	}

	abstract void emitChange();
}
