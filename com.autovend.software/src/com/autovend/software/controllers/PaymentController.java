// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

/**
 * An abstract class for objects that connects and provide functionalities for
 * different payment methods
 */
abstract class PaymentController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {

	private CheckoutController mainController;

	public PaymentController(D newDevice) {
		super(newDevice);
	}

	final CheckoutController getMainController() {
		return this.mainController;
	};

	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterPaymentController(this);
		}
		;
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerPaymentController(this);
		}
		;
	}

}
