package com.autovend.software.pojo;

import com.autovend.software.controllers.CheckoutController;

public class CheckoutStation {
	
	String checkoutStationName;
	CheckoutController checkoutController;
	
	public CheckoutStation(String checkoutStationName, CheckoutController checkoutContoller) {
		this.checkoutStationName = checkoutStationName;
		this.checkoutController = checkoutContoller;
	}
	
	
	public String getCheckoutStationName() {
		return checkoutStationName;
	}
	
	
	public CheckoutController getCheckoutController() {
		return checkoutController;
	}
		
}
