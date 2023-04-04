// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.observers.AbstractDeviceObserver;

@SuppressWarnings("unchecked")

public abstract class DeviceController<D extends AbstractDevice<O>, O extends AbstractDeviceObserver> {
	private D device;

	public D getDevice() {
		return this.device;
	}

	public DeviceController(D newDevice) {
		this.device = newDevice;
		this.device.register((O) this);
	}

	public void setDevice(D newDevice) {
		if (device != null) {
			this.device.deregister((O) this);
		}
		this.device = newDevice;
		if (device != null) {
			this.device.register((O) this);
		}
	}

	public void enableDevice() {
		this.device.enable();
	}

	public void disableDevice() {
		this.device.disable();
	}

	boolean isDeviceDisabled() {
		return this.device.isDisabled();
	}

	public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}

	public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
	}
}
