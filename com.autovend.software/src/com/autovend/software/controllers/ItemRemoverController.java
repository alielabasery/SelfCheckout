package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.TouchScreenObserver;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;

public class ItemRemoverController extends DeviceController<TouchScreen, TouchScreenObserver> implements TouchScreenObserver {

	CheckoutController mainController;
	ElectronicScale scale;
	ElectronicScaleController scaleController;
	BarcodedProduct barcodedProduct =null;
	PLUCodedProduct pluProduct = null;
	double expectedWeight;

	/**
	 * The constructor for the ItemRemoverController
	 * @param touchScreen
	 * 		The touch screen being used
	 */
	public ItemRemoverController(TouchScreen touchScreen) {
		super(touchScreen);
	}

	/**
	 * Gets the main checkout controller
	 * @return
	 * 		The checkout controller which is the main controller
	 */
	public final CheckoutController getMainController() {
		return this.mainController;
	}

	/**
	 * Sets the main checkout controller
	 * @param newMainController
	 * 		The CheckoutController to be set as the main controller
	 */
	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterItemRemoverController(this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerItemRemoverController(this);
		}
	}

	/**
	 * Removes a product from the order
	 * @param product
	 * 		The product to be removed
	 */
	public void removeFromOrder(Product product) {

		if(product instanceof PLUCodedProduct) {

			expectedWeight = mainController.getPLUWeight(product);
			mainController.removeItem(this, product, expectedWeight);
		}
		else {
			barcodedProduct = (BarcodedProduct) product;
			expectedWeight = barcodedProduct.getExpectedWeight();
			mainController.removeItem(this, product, expectedWeight);
		}
		//	//5. System: Signals to the Customer I/O that the item should be removed from the bagging area.
		//	signalToRemoveFromBaggingArea(); // implemented by the GUI team

		if (scaleController.getCurrentWeight() != scaleController.getExpectedWeight()) {

			scaleController.reactToWeightChangedEvent(scale, expectedWeight);
		}

		mainController.systemProtectionLock = false;
		//	//6. System:Unblockstheself-checkoutstation.
		//    resetGUI();  // implemented by the GUI team
	}




}
