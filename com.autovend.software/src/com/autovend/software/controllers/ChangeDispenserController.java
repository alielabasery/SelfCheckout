// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import java.math.BigDecimal;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

abstract public class ChangeDispenserController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {

	private BigDecimal denom;
	private CheckoutController mainController;

	/**
	 * Constructor for the ChangeDispenserController
	 * @param newDevice
	 * 		The device to connect
	 * @param denom
	 * 		The denomination
	 */
	public ChangeDispenserController(D newDevice, BigDecimal denom) {
		super(newDevice);
		this.denom = denom;
	}

	/**
	 * Gets the main checkout controller
	 * @return
	 * 		The main checkout controller
	 */
	final CheckoutController getMainController() {
		return this.mainController;
	}

	/**
	 * Sets the main checkout controller
	 * @param newMainController
	 * 		The CheckoutController to set as the new main controller
	 */
	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterChangeDispenserController(this.denom, this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerChangeDispenserController(this.denom, this);
		}
	}

	/**
	 * Sets the denomination
	 * @param denom
	 * 		The BigDecimal value of the denomination to set
	 */
	void setDenom(BigDecimal denom) {
		this.denom = denom;
	}

	/**
	 * Gets the denomination
	 * @return
	 * 		The BigDecimal value of the denomination
	 */
	BigDecimal getDenom() {
		return this.denom;
	}

	abstract void emitChange();
}
