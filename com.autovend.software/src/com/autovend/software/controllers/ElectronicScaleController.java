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

import com.autovend.devices.ElectronicScale;
import com.autovend.devices.observers.ElectronicScaleObserver;
import com.autovend.products.Product;

public class ElectronicScaleController extends BaggingAreaController<ElectronicScale, ElectronicScaleObserver>
		implements ElectronicScaleObserver {
	private double currentWeight;
	private double expectedWeight;
	private boolean addingBags;

	private boolean AttendantApproval;

	/**
	 * The constructor for ElectronicScaleController
	 * @param newDevice
	 * 		The Electronic Scale being used
	 */
	public ElectronicScaleController(ElectronicScale newDevice) {
		super(newDevice);
	}

	// ******* I think that there has to be a maximum weight that a item is allowed ot be in order for it to go on the for electronic scale 
	/**
	 * Method used to update the expected weight for validation of orders.
	 * 
	 * @param nextProduct
	 * @param weightInGrams
	 */
	@Override
	void updateExpectedBaggingArea(double weightInGrams) {
		this.expectedWeight += weightInGrams;
		this.setBaggingValid(false);
		// TODO: Figure out how changes smaller than sensitivity would be handled
		// TODO: Also figure out how items which would cause the scale to be overloaded
		// should be handled.
	}

	@Override
	public void resetOrder() {
		this.setBaggingValid(true);
		this.currentWeight = 0;
		this.expectedWeight = 0;
	}
	
	public void attendantInput(boolean approval) {
		AttendantApproval = approval;
		return;
	}

	
	
	@Override
	public void reactToWeightChangedEvent(ElectronicScale scale, double weightInGrams) {
		if (scale != this.getDevice()) {
			return;
		}
		;
		this.currentWeight = weightInGrams;
		// if the customer is adding their own bags, no need to check the expected
		// weight as there is not one yet
		if (addingBags) {
			return;
		}
		if (this.currentWeight == this.expectedWeight) {
			this.getMainController().baggedItemsValid(this);
		}
		// case of weight discrepancy
		else {
			

			// boolean value resolveDisrepancy:
			// true if discrepancy is resolved by:
			// -a do not bag request from customer IO
			// -attendant approval
			boolean resolveDiscrepancy = false;

			// system blocks checkout from further interaction
			this.getMainController().baggingItemLock = true;

			// discrepancy resolved if customer signals a dnb request or attendant approves
			if (AttendantApproval)
				resolveDiscrepancy = true;

			// validates bagging if the discrepancy was resolved
			if (resolveDiscrepancy) {
				this.getMainController().baggedItemsValid(this);
				this.getMainController().baggingItemLock = false;
			}
			else {
			this.getMainController().baggedItemsInvalid(this,
					"The items in the bagging area don't have the correct weight.");
			}

		}
	}
 
	@Override
	public void reactToOverloadEvent(ElectronicScale scale) {
		if (scale != this.getDevice()) {
			return;
		}
		;
		this.getMainController().baggingAreaError(this,
				"The scale is currently overloaded, please take items off it to avoid damaging the system.");
	}

	@Override
	public void reactToOutOfOverloadEvent(ElectronicScale scale) {
		if (scale != this.getDevice()) {
			return;
		}
		;
		this.getMainController().baggingAreaErrorEnded(this, "The scale is no longer overloaded.");
	}

	public double getCurrentWeight() {
		return currentWeight;
	}

	public void updateWithBagWeight(double weight) {
		this.expectedWeight += weight;
	}

	public void setAddingBags(boolean value) {
		this.addingBags = value;
	}

	public double getExpectedWeight() {
		return this.expectedWeight;
	}

	public boolean getAddingBags() {
		return this.addingBags;
	}


	
}

