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
	
	public PurchaseBagController(TouchScreen screen) {
		super(screen);
		// TODO Auto-generated constructor stub
	}
	
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
