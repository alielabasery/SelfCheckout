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

import com.autovend.Barcode;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.EmptyException;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.TouchScreenObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;

public class PurchaseBagController extends ItemAdderController<TouchScreen, TouchScreenObserver> implements TouchScreenObserver {

	BagDispenserController dispenserController;
	
	/**
	 * Constructor for the PurchaseBagController
	 * @param screen
	 * 		The touch screen being used
	 */
	public PurchaseBagController(TouchScreen screen) {
		super(screen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Reacts to the bag added event
	 * @param screen
	 * 		The touch screen being used
	 * @param barcode
	 * 		The barcode
	 * @throws EmptyException
	 * 		If the bags are empty
	 */
	public void reactToBagAddedEvent(TouchScreen screen, Barcode barcode) throws EmptyException {
		// if barcode is for a valid object, then add the product found to the order on
		// the main controller.
		// otherwise ignore the item.
		if (screen != this.getDevice()) {
			return;
		}

		BarcodedProduct newBag = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		if (newBag != null) {
			int numBag = this.getMainController().getBagNumber();
			this.getMainController().purchaseBags(this, newBag, newBag.getExpectedWeight(), numBag);
			dispenserController.dispenseBags();
		}
	}



}
