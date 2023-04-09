package com.autovend.software.controllers;

import java.util.HashMap;

import com.autovend.software.pojo.CartLineItem;

import Networking.NetworkController;

public class AttendentController {
	
	public AttendentController() {
		NetworkController.registerAttendentController(this);
	}
	
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
