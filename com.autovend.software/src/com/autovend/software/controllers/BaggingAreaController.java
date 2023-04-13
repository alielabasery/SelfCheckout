/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/

package com.autovend.software.controllers;
import com.autovend.software.controllers.*;
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
	private boolean attendantApproval;


	/**
	 * The constructor for the BaggingAreaController
	 * @param newDevice
	 * 		The new device
	 */
	public BaggingAreaController(D newDevice) {
		super(newDevice);
	}

	/**
	 * Gets the main controller
	 * @return
	 * 		The main controller
	 */
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
	
	
	/**
	 * Sets the attendant approval boolean value
	 * @param approval
	 * 		Boolean value of attendant approval
	 */
	public void setAttendantApproval(boolean approval) {
	    this.attendantApproval = approval;
	}
	
	/**
	 * Gets the attendants approval
	 * @return
	 * 		gets the attendants approval
	 */
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

	abstract void updateExpectedBaggingArea(double weightInGrams);

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
	// method basically locks the system and calls for an attendant once attendant removes item just added after it is approved
	// station will not continue until approval is had 
	/**
	 * Errors for the bagging area
	 * @param currentWeight
	 * 		The current weight of the scale
	 * @param expectedWeight
	 * 		What the weight should be
	 * @return
	 * 		Expected weight
	 */

	public double baggingAreaError(double currentWeight, double expectedWeight) {
        // 1. Block the self-checkout station from further customer input.
        this.getMainController().baggingItemLock = true;
      
    
        // 2. Wait for approval from the Attendant I/O. By using a While loop, method also sets attendant approval to whatever got response will be 
        boolean gotResponse = false;
        while (!gotResponse) {
            gotResponse = this.getAttendantApproval();
            this.setAttendantApproval(gotResponse);
        }

        if (this.getAttendantApproval()) {
                // 3. Reduce the expected weight in the Bagging Area by the expected weight of the item.
                double newExpectedWeight = expectedWeight - currentWeight;

                // 4. Unblock the station.
                this.getMainController().baggingItemLock = false;
                return newExpectedWeight;
        } else {
            // Attendant did not approve, disable the station.
            this.getMainController().baggingItemLock = true;
       }
        
        return 1;
	
	}



	

}






