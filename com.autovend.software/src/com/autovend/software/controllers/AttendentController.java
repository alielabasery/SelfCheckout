package com.autovend.software.controllers;

import java.util.HashMap;

import com.autovend.software.pojo.CartLineItem;

import Networking.NetworkController;

public class AttendentController {
	
	/**
	 * Constructor for the AttendantController
	 */
	public AttendentController() {
		NetworkController.registerAttendentController(this);
	}
	
	/**
	 * Adds an item to the cart
	 * @param checkoutStationName
	 * 		The name of the station
	 * @param item
	 * 		The item to add
	 * @throws IllegalArgumentException
	 * 		If the station does not exist
	 */
	public void addItemToStationCart(String checkoutStationName, CartLineItem item) throws IllegalArgumentException {
//		checkoutControllerList.get(checkoutStationName).
		CheckoutController checkoutController = NetworkController.getCheckoutStationController(checkoutStationName);
		if (checkoutController != null) {
			checkoutController.addItem(item);
		} else {
			throw new IllegalArgumentException(String.format("Station [%s] not found!",checkoutStationName));
		}		
	}

}
