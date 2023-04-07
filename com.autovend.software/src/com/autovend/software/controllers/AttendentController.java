package com.autovend.software.controllers;

import java.util.HashMap;

import com.autovend.software.pojo.CartLineItem;

public class AttendentController {
	
	HashMap<String, CheckoutController> checkoutControllerList = new HashMap<>();
	
	
	public void registerCheckoutController(String checkoutStationName, CheckoutController checkoutController) {
		checkoutControllerList.put(checkoutStationName, checkoutController);
	}
	
	public void addItemToStationCart(String checkoutStationName, CartLineItem item) {
		checkoutControllerList.get(checkoutStationName).addItem(item);
		
	}

}
