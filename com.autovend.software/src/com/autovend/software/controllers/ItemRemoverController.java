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
		
		scaleController = new ElectronicScaleController(scale);

		if (scaleController.getCurrentWeight() != scaleController.getExpectedWeight()) {

			scaleController.reactToWeightChangedEvent(scale, expectedWeight);
		}

		mainController.systemProtectionLock = false;
		//	//6. System:Unblockstheself-checkoutstation.
		//    resetGUI();  // implemented by the GUI team
	}




}
