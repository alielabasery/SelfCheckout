

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
		
	}
	
	
	//methos used as a private getter and setter to be used below 
	private boolean attendantApproval;

	public void setAttendantApproval(boolean approval) {
	    this.attendantApproval = approval;
	}

	public boolean getAttendantApproval() {
	    return this.attendantApproval;
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

	
	// Author Victor Campos  
	// method not fully complete but is the bagging area error from electronic scale 
	// needs to be called from electronic scale in the react to WEIGHTDISCREP event 
	// method bascially locks the system and calls for an attendant once attendant removes item just added after it is approaved
	// station will not continue until approval is had 

	public void baggingAreaError(double currentWeight) {
        // 1. Block the self-checkout station from further customer input.
        this.getMainController().baggingItemLock = true;
        System.out.println("Item is too heavy or Too big for bagging area, station is blocked see Attendant");

        // 2. Signal to the Attendant I/O that a no-bagging request is in progress.
        this.getAttendant().baggingAreaError();

        // 3. Wait for approval from the Attendant I/O. By using a While loop, method also sets attendant approval to whatever got response will be 
        boolean gotResponse = false;
        while (!gotResponse) {
            gotResponse = this.getAttendant().waitForApproval();
            this.setAttendantApproval(gotResponse);
        }

        if (this.getAttendantApproval()) {
                // 4. Reduce the expected weight in the Bagging Area by the expected weight of the item.
                double expectedWeight = this.getExpectedWeight();
                double newExpectedWeight = expectedWeight - currentWeight;
                this.setExpectedWeight(newExpectedWeight);

                // 5. Unblock the station.
                this.getMainController().baggingItemLock = false;
        } else {
            // Attendant did not approve, disable the station.
            this.getMainController().baggingItemLock = true;
        }
    } 




}






