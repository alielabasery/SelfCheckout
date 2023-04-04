// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.products.Product;

/**
 * An abstract class for objects which monitor and control the bagging area to
 * determine whether the customers order is valid or not, whether it be
 * validating the net weight is as expected, or through visual analysis of the
 * bagging area.
 */
public abstract class BaggingAreaController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {

	private CheckoutController mainController;
	private boolean orderValidated;

	public BaggingAreaController(D newDevice) {
		super(newDevice);
	}

	public final CheckoutController getMainController() {
		return this.mainController;
	};

	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterBaggingAreaController(this);
		}
		;
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerBaggingAreaController(this);
		}
		;
	}

	/**
	 * A method used to inform the bagging area controller to update the expected
	 * items in the area how this is done will vary by the method used for
	 * validation.
	 *
	 */
	// Note: this method is not very generalized, I want to generalize this code so
	// that it works with
	// more than just weight based bagging area devices (so it can implement more
	// types of validation)
	abstract void updateExpectedBaggingArea(Product nextProduct, double weightInGrams);

	abstract public void resetOrder();

	boolean getBaggingValid() {
		return orderValidated;
	}

	void setBaggingValid(boolean validation) {
		this.orderValidated = validation;
	}

}
