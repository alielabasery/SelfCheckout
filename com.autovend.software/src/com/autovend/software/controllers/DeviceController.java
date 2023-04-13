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

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

@SuppressWarnings("unchecked")

public abstract class DeviceController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver> {
	private D device;

	/**
	 * Returns the device being controlled
	 * @return
	 * 		The device being controlled
	 */
	public D getDevice() {
		return this.device;
	}

	/**
	 * Constructor for the device controller
	 * @param newDevice
	 * 		The new device to connect to the controller
	 */
	public DeviceController(D newDevice) {
		this.device = newDevice;
		this.device.register((O) this);
	}

	/**
	 * Sets the device connected to the controller
	 * @param newDevice
	 * 		The device to connect to the controller
	 */
	public void setDevice(D newDevice) {
		if (device != null) {
			this.device.deregister((O) this);
		}
		this.device = newDevice;
		if (device != null) {
			this.device.register((O) this);
		}
	}

	/**
	 * Enables the device connected
	 */
	public void enableDevice() {
		this.device.enable();
	}

	/**
	 * Disables the connected device
	 */
	public void disableDevice() {
		this.device.disable();
	}

	/**
	 * Returns if the device is disabled
	 * @return
	 * 		Boolean True if the device is disabled, false otherwise
	 */
	boolean isDeviceDisabled() {
		return this.device.isDisabled();
	}

	public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}

	public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}
}
