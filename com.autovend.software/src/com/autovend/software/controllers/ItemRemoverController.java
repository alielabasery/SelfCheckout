package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;

abstract class ItemRemoverController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
extends DeviceController<D, O> {
	
	CheckoutController mainController;
	BarcodedProduct barcodedProduct =null;
	PLUCodedProduct pluProduct = null;
	double expectedWeight;
	
public ItemRemoverController(D newDevice) {
		super(newDevice);
	}


public void removeFromOrder(Product product) {
	
	if(product instanceof PLUCodedProduct) {
		
		expectedWeight = mainController.getPLUWeight(product);
		mainController.removeItem(product, expectedWeight);
	}
	else {
		barcodedProduct = (BarcodedProduct) product;
		expectedWeight = barcodedProduct.getExpectedWeight();
		mainController.removeItem(product, expectedWeight);
	}
	
//	//5. System: Signals to the Customer I/O that the item should be removed from the bagging area.
//	signalToRemoveFromBaggingArea(); // implemented by the GUI team
//	
//	
//	//6. System:Unblockstheself-checkoutstation.
//    resetGUI();  // implemented by the GUI team
}


}
