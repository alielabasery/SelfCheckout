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

import com.autovend.ReusableBag;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReusableBagDispenser;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.ReusableBagDispenserObserver;
import com.autovend.devices.observers.TouchScreenObserver;

public class BagDispenserController extends DeviceController<ReusableBagDispenser, ReusableBagDispenserObserver> 
		implements ReusableBagDispenserObserver{
	
	CheckoutController mainController;
	ReusableBagDispenser bagDispenser;
	ReusableBag bags;

	/**
	 * The constructor for the BagDispenserController
	 * @param dispenser
	 * 		The Reusable bag dispenser
	 */
	public BagDispenserController(ReusableBagDispenser dispenser) {
		super(dispenser);
		
	}
	
	/**
	 * gets the main controller
	 * @return
	 * 		The main controller
	 */
	public final CheckoutController getMainController() {
		return this.mainController;
	}

	/**
	 * Setter for the main controller
	 * @param newMainController
	 * 		The CheckoutController to set as the main controller
	 */
	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterBagDispenserController(this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerBagDispenserController(this);
		}
	}
	
	/**
	 * Dispenses bags using the bag dispenser
	 * @throws EmptyException
	 * 		If the bag dispenser device is empty
	 */
	public void dispenseBags() throws EmptyException {
		
		try {
			this.getDevice().dispense();
		} catch (EmptyException e) {
			this.getMainController().bagDispenseFailed(this);
			this.getDevice().dispense();
		}
	}
	
	@Override
	public void bagsLoaded(ReusableBagDispenser dispenser,int count) {
		int capacity = this.getDevice().getCapacity();
		if (count <= capacity) {			
			try {
				this.getDevice().load(bags);
			} catch (OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void bagDispensed(ReusableBagDispenser dispenser) {
		
	}

	@Override
	public void outOfBags(ReusableBagDispenser dispenser) {
		if (dispenser != this.getDevice()) {
			return;
		}
		this.getMainController().bagDispenseFailed(this);
		
	}




}