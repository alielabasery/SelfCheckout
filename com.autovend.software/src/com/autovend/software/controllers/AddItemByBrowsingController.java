package com.autovend.software.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.autovend.BarcodedUnit;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;

public class AddItemByBrowsingController {
	
	List<BarcodedProduct> catalog = new ArrayList<BarcodedProduct>();
	BarcodedUnit item; 
	BigDecimal price;
	
	/*
	 * Add products in database to catalog
	 */
	public void addProducts() {
		for(BarcodedProduct product : ProductDatabases.BARCODED_PRODUCT_DATABASE.values()) {
			catalog.add(product);
		}
	}
	
	/*
	 * Gets the selected Item's BarcodedUnit and price based on quantity
	 */
	public void getProduct(String name, String quantity) {
		BigDecimal productQuantity = new BigDecimal(quantity);
		for(BarcodedProduct product : ProductDatabases.BARCODED_PRODUCT_DATABASE.values()) {
			if(product.getDescription().toLowerCase().equals(name)) {
				item = new BarcodedUnit(product.getBarcode(), product.getExpectedWeight());
				price = product.getPrice().multiply(productQuantity);
			}
		}
	}
	
	/*
	 * Getter for Catalog List
	 */
	public List<BarcodedProduct> getCatalog(){
		return catalog;
	}
	/*
	 * Getter for selected Item
	 */
	public BarcodedUnit getItem() {
		return item;
	}
	/*
	 * Getter for selected Item's price
	 */
	public BigDecimal getPrice() {
		return price;
	}

}
