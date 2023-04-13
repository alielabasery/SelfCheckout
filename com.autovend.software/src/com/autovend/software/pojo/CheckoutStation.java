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
