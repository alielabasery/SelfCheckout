// Placeholder for Group 6: Names + UCID

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
