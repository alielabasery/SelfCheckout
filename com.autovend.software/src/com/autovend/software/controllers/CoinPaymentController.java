// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import java.math.BigDecimal;

import com.autovend.devices.CoinValidator;
import com.autovend.devices.observers.CoinValidatorObserver;

public class CoinPaymentController extends PaymentController<CoinValidator, CoinValidatorObserver>
		implements CoinValidatorObserver {
	
	/**
	 * Constructor for the CoinPaymentController
	 * @param device
	 * 		The CoinValidator device
	 */
	public CoinPaymentController(CoinValidator device) {
		super(device);
	}

	@Override
	public void reactToValidCoinDetectedEvent(CoinValidator validator, BigDecimal value) {
		if (validator != this.getDevice()) {
			return;
		}
		this.getMainController().addToAmountPaid(value);
	}

	@Override
	public void reactToInvalidCoinDetectedEvent(CoinValidator validator) {}
}