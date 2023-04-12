// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import java.math.BigDecimal;
import java.util.Currency;

import com.autovend.devices.BillValidator;
import com.autovend.devices.observers.BillValidatorObserver;

/*
 * A class for objects that controls payment made with cash bills
 */
public class BillPaymentController extends PaymentController<BillValidator, BillValidatorObserver>
		implements BillValidatorObserver {

	/**
	 * Constructor for the BillPaymentController
	 * @param device
	 * 		BillValidator to connect
	 */
	public BillPaymentController(BillValidator device) {
		super(device);
	}

	/**
	 * The following class checks we have the same device and then
	 */
	@Override
	public void reactToValidBillDetectedEvent(BillValidator validator, Currency currency, int value) {
		this.getMainController().addToAmountPaid(new BigDecimal(value));
	}

	@Override
	public void reactToInvalidBillDetectedEvent(BillValidator validator) {
	}

}
