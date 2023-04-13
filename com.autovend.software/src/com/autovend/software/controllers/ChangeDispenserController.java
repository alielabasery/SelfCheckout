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

import java.math.BigDecimal;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

abstract public class ChangeDispenserController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver>
		extends DeviceController<D, O> {

	private BigDecimal denom;
	private CheckoutController mainController;

	/**
	 * Constructor for the ChangeDispenserController
	 * @param newDevice
	 * 		The device to connect
	 * @param denom
	 * 		The denomination
	 */
	public ChangeDispenserController(D newDevice, BigDecimal denom) {
		super(newDevice);
		this.denom = denom;
	}

	/**
	 * Gets the main checkout controller
	 * @return
	 * 		The main checkout controller
	 */
	final CheckoutController getMainController() {
		return this.mainController;
	}

	/**
	 * Sets the main checkout controller
	 * @param newMainController
	 * 		The CheckoutController to set as the new main controller
	 */
	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterChangeDispenserController(this.denom, this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerChangeDispenserController(this.denom, this);
		}
	}

	/**
	 * Sets the denomination
	 * @param denom
	 * 		The BigDecimal value of the denomination to set
	 */
	void setDenom(BigDecimal denom) {
		this.denom = denom;
	}

	/**
	 * Gets the denomination
	 * @return
	 * 		The BigDecimal value of the denomination
	 */
	BigDecimal getDenom() {
		return this.denom;
	}

	abstract void emitChange();
}
