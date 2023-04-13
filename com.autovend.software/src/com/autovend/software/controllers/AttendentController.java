/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
package com.autovend.software.controllers;

import java.util.HashMap;

import com.autovend.devices.SupervisionStation;
import com.autovend.software.pojo.CartLineItem;

import Networking.NetworkController;

public class AttendentController {
	
	SupervisionStation supervisionStation;
	
	/**
	 * Constructor for the AttendantController
	 */
	public AttendentController() {
		supervisionStation = new SupervisionStation();
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
		CheckoutController checkoutController = NetworkController.getCheckoutStationController(checkoutStationName);
		if (checkoutController != null) {
			checkoutController.addItem(item);
		} else {
			throw new IllegalArgumentException(String.format("Station [%s] not found!",checkoutStationName));
		}		
	}
	
	public SupervisionStation getSupervisionStation() {
		return this.supervisionStation;
	}
	
	public CheckoutController getCheckoutController(String stationName) {
		return NetworkController.getCheckoutStationController(stationName.toLowerCase());
	}

}
