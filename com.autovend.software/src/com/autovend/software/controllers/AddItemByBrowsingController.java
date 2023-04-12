package com.autovend.software.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.autovend.BarcodedUnit;
import com.autovend.devices.SimulationException;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.TouchScreenObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;

public class AddItemByBrowsingController extends ItemAdderController<TouchScreen, TouchScreenObserver> implements TouchScreenObserver {
	
	List<BarcodedProduct> catalog = new ArrayList<BarcodedProduct>();
	BarcodedUnit item; 
	BigDecimal price;
	double weight; 
	BarcodedProduct barcodedProduct;
	
	public AddItemByBrowsingController(TouchScreen touchscreen) {
		super(touchscreen);
	}
	
	/**
	 * Add products in database to catalog
	 */
	public void addProducts() {
		for(BarcodedProduct product : ProductDatabases.BARCODED_PRODUCT_DATABASE.values()) {
			catalog.add(product);
		}
	}
	
	/**
	 * Gets the selected Item's BarcodedUnit and price based on quantity
	 * @param name
	 * 		The name of the product
	 * @param quantity
	 * 		The quantity of products
	 */
	
	public void getProduct(String name, String quantity) {
		BigDecimal productQuantity = new BigDecimal(quantity);
		for(BarcodedProduct product : ProductDatabases.BARCODED_PRODUCT_DATABASE.values()) {
			if(product.getDescription().toLowerCase().equals(name)) {
				item = new BarcodedUnit(product.getBarcode(), product.getExpectedWeight());
				price = product.getPrice().multiply(productQuantity);
				weight = product.getExpectedWeight();
				barcodedProduct = product;
			}
		}
		if(item == null) {
			throw new NullPointerException("Item not in databse!");
		}
	}
	
	/**
	 * Reaction event when item is added by browsing
	 * @param touchscreen
	 * 		The Touch Screen which is being pressed
	 */

	public void reactToAddByBrowseEvent(TouchScreen touchscreen) {
		disableDevice();
		
		String entryName = displayCatalog(); //provided by GUI team, passes in string of item's name
		String entryQuantity = selectQuantity(); //provided by GUI team, passes in string of item's quantity
		boolean exit = cancelBrowse(); //provided by GUI team, passes in true if "Cancel/Back" is selected
		
		if(exit) {
			enableDevice();
			resetGUI();
			return;
		}
		
		//runs getProduct method above to get item information
		getProduct(entryName, entryQuantity);
		
		//Signals weight change
		if(barcodedProduct != null) {
			this.getMainController().addItem(this, barcodedProduct, weight);
		}
		else {
			throw new SimulationException("Null product!");
		}
		
		signalToAddToBaggingArea(); //provided by GUI team
		enableDevice();
		resetGUI(); //provided by GUI team
	}
	
	/**
	 * Getter for Catalog List
	 * @return
	 * 		Returns the Catalog as a list of BarcodedProducts
	 */
	public List<BarcodedProduct> getCatalog(){
		return catalog;
	}
	/**
	 * Getter for selected Item
	 * @return
	 * 		Returns the BarcodedUnit which is selected
	 */
	public BarcodedUnit getItem() {
		return item;
	}
	
	/**
	 * Getter for selected Product
	 * @return
	 * 		Returns the BarcodedProduct which is selected
	 */
	public BarcodedProduct getBarcodedProduct() {
		return barcodedProduct;
	}
	
	/**
	 * Getter for selected Item's price
	 * @return
	 * 		The BigDecimal value of the selected items price
	 */

	public BigDecimal getPrice() {
		return price;
	}
	
	/**
	 * Getter for selected Item's weight
	 * @return
	 * 		The double value of the selected items weight
	 */
	public double getWeight() {
		return weight;
	}
}
