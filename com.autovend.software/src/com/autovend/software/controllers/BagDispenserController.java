package com.autovend.software.controllers;

import com.autovend.devices.EmptyException;
import com.autovend.devices.ReusableBagDispenser;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.ReusableBagDispenserObserver;
import com.autovend.devices.observers.TouchScreenObserver;

public class BagDispenserController extends DeviceController<ReusableBagDispenser, ReusableBagDispenserObserver> 
		implements ReusableBagDispenserObserver{
	
	CheckoutController mainController;

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
	
	public void dispenceBags() {
		
		try {
			this.getDevice().dispense();
		} catch (EmptyException e) {
			this.getMainController().bagDispenceFailed(this);
			
		}
	}

	@Override
	public void bagDispensed(ReusableBagDispenser dispenser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfBags(ReusableBagDispenser dispenser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bagsLoaded(ReusableBagDispenser dispenser, int count) {
		// TODO Auto-generated method stub
		
	}

}