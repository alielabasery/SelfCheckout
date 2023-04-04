// Placeholder for Group 6: Names + UCID

package com.autovend.software.controllers;

import com.autovend.Card;
import com.autovend.devices.CardReader;
import com.autovend.devices.observers.CardReaderObserver;
import com.autovend.external.CardIssuer;

import java.math.BigDecimal;

public class CardReaderController extends PaymentController<CardReader, CardReaderObserver>
		implements CardReaderObserver {
	public boolean isPaying;

	public CardReaderController(CardReader newDevice) {
		super(newDevice);
	}

	public CardIssuer bank;
	private BigDecimal amount;

	// TODO: Add Messages And Stuff
	@Override
	public void reactToCardInsertedEvent(CardReader reader) {
		this.isPaying = true;
	}

	@Override
	public void reactToCardRemovedEvent(CardReader reader) {
		this.isPaying = false;
	}

	// Don't need to implement below yet (use case only asks for insertion so far)
	@Override
	public void reactToCardTappedEvent(CardReader reader) {
	}

	@Override
	public void reactToCardSwipedEvent(CardReader reader) {
	}

	@Override
	public void reactToCardDataReadEvent(CardReader reader, Card.CardData data) {
		if (reader != this.getDevice() || !this.isPaying || this.bank==null) {
			return;
		}
		// TODO: Given the data, handle stuff with the transaction
		int holdNum = bank.authorizeHold(data.getNumber(), this.amount);
		if (holdNum !=-1 && (bank.postTransaction(data.getNumber(), holdNum, this.amount))) {
			getMainController().addToAmountPaid(this.amount);
		}

		this.disableDevice();

		this.amount = BigDecimal.ZERO;
		this.bank = null;
		// Clear bank and such if it fails to hold or not (might change this, I am tired
		// rn so might be dumb here)
	}

	public void enablePayment(CardIssuer issuer, BigDecimal amount) {
		this.enableDevice();
		this.bank = issuer;
		this.amount = amount;
	}
}
