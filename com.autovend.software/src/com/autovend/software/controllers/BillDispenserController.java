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
* Sahaj Malhotra () 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
package com.autovend.software.controllers;

import com.autovend.Bill;
import com.autovend.devices.BillDispenser;
import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.observers.BillDispenserObserver;

import java.math.BigDecimal;

public class BillDispenserController extends ChangeDispenserController<BillDispenser, BillDispenserObserver>
		implements BillDispenserObserver {
	
	/**
	 * Constructor for the BillDispenserController
	 * @param newDevice
	 * 		The BillDispenser to connect
	 * @param denom
	 * 		The denomination
	 */
	public BillDispenserController(BillDispenser newDevice, BigDecimal denom) {
		super(newDevice, denom);
	}

	@Override
	public void emitChange() {
		try {
			this.getDevice().emit();
		} catch (EmptyException ex) {
			this.getMainController().changeDispenseFailed(this, this.getDenom());
		} catch (OverloadException ex) {
			System.out.println("This can't physically happen, something went wrong.");
		}
	}

	@Override
	public void reactToBillsFullEvent(BillDispenser dispenser) {
	}

	@Override
	public void reactToBillsEmptyEvent(BillDispenser dispenser) {

	}

	@Override
	public void reactToBillAddedEvent(BillDispenser dispenser, Bill bill) {
	}

	@Override
	public void reactToBillRemovedEvent(BillDispenser dispenser, Bill bill) {
	}

	@Override
	public void reactToBillsLoadedEvent(BillDispenser dispenser, Bill... bills) {
	}

	@Override
	public void reactToBillsUnloadedEvent(BillDispenser dispenser, Bill... bills) {
	}
}