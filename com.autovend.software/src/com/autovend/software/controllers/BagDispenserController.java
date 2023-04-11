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

	public BagDispenserController(ReusableBagDispenser dispenser) {
		super(dispenser);
		
	}
	
	public final CheckoutController getMainController() {
		return this.mainController;
	}

	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterBagDispenserController(this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerBagDispenserController(this);
		}
	}
	
	public void dispenseBags() {
		
		try {
			this.getDevice().dispense();
		} catch (EmptyException e) {
			this.outOfBags(bagDispenser);
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
		if (dispenser != this.getDevice()) {
			return;
		}
		this.getMainController().bagDispense(this);
		
	}

	@Override
	public void outOfBags(ReusableBagDispenser dispenser) {
		if (dispenser != this.getDevice()) {
			return;
		}
		this.getMainController().bagDispenseFailed(this);
		
	}




}