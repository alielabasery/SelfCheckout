// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.Barcode;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.observers.BarcodeScannerObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;

/**
 * Controller for the barcode scanner, communicates with main checkout
 * controller to add items to order.
 */
public class BarcodeScannerController extends ItemAdderController<BarcodeScanner, BarcodeScannerObserver>
		implements BarcodeScannerObserver {
	
	/**
	 * Constructor for the BarcodeScannerController
	 * @param scanner
	 * 		The BarcodeScanner to connect
	 */
	public BarcodeScannerController(BarcodeScanner scanner) {
		super(scanner);
	}

	/**
	 * Reacts to a barcode scanned event
	 * 
	 * @param barcodeScanner
	 * 		The scanner used
	 * @param barcode
	 * 		The barcode being scanned
	 */
	public void reactToBarcodeScannedEvent(BarcodeScanner barcodeScanner, Barcode barcode) {
		// if barcode is for a valid object, then add the product found to the order on
		// the main controller.
		// otherwise ignore the item.
		if (barcodeScanner != this.getDevice()) {
			return;
		}

		BarcodedProduct scannedItem = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		if (scannedItem != null) {
			this.getMainController().addItem(this, scannedItem, scannedItem.getExpectedWeight());
		}
	}
}
