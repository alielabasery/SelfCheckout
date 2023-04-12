package com.autovend.software.pojo;

import com.autovend.software.controllers.CheckoutController;

public class CheckoutStation {
	
	String checkoutStationName;
	CheckoutController checkoutController;
	
	/**
	 * The Constructor for the CheckoutStation
	 * @param checkoutStationName
	 * 		The name of the checkout station
	 * @param checkoutContoller
	 * 		The controller for the checkout station
	 */
	public CheckoutStation(String checkoutStationName, CheckoutController checkoutContoller) {
		this.checkoutStationName = checkoutStationName;
		this.checkoutController = checkoutContoller;
	}
	
	/**
	 * Gets the name of the checkout station
	 * @return
	 * 		The name of the station
	 */
	public String getCheckoutStationName() {
		return checkoutStationName;
	}
	
	/**
	 * Gets the controller for the checkout station
	 * @return
	 * 		The checkout controller
	 */
	public CheckoutController getCheckoutController() {
		return checkoutController;
	}
		
}
