package com.autovend.software.controllers;

import com.autovend.devices.EmptyException;
import com.autovend.devices.ReusableBagDispenser;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.ReusableBagDispenserObserver;
import com.autovend.devices.observers.TouchScreenObserver;

public class ReusableBagDispenserController extends DeviceController<ReusableBagDispenser, ReusableBagDispenserObserver> 
		implements ReusableBagDispenserObserver{

	public ReusableBagDispenserController(ReusableBagDispenser dispenser) {
		super(dispenser);
		
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
