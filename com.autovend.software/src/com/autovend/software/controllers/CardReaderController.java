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

import com.autovend.Card;
import com.autovend.Card.CardInsertData;
import com.autovend.ChipFailureException;
import com.autovend.GiftCard.GiftCardInsertData;
import com.autovend.devices.CardReader;
import com.autovend.devices.observers.CardReaderObserver;
import com.autovend.external.CardIssuer;

import java.math.BigDecimal;

public class CardReaderController extends PaymentController<CardReader, CardReaderObserver>
		implements CardReaderObserver {
	public boolean isPaying;

	/**
	 * The constructor for the CardReaderController
	 * @param newDevice
	 * 		The CardReader device to connect
	 */
	public CardReaderController(CardReader newDevice) {
		super(newDevice);
	}

	public Card card;
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
		if (reader != this.getDevice() || !this.isPaying || (this.bank==null && !data.getType().equals("Gift Card"))) {
			return;
		}
		
		if(data.getType().equals("Gift Card")) {
			GiftCardInsertData d = (GiftCardInsertData) data;
			
			try {
				if(d.deduct(this.amount)) {
					getMainController().addToAmountPaid(this.amount);
				}else {
					getMainController().addToAmountPaid(d.getRemainingBalance());
					d.deduct(d.getRemainingBalance());
				}
			} catch (ChipFailureException e) {
				//Do something (probably GUI related)
			}
			
		}else{
			
			int holdNum = bank.authorizeHold(data.getNumber(), this.amount);
			if (holdNum !=-1 && (bank.postTransaction(data.getNumber(), holdNum, this.amount))) {
				getMainController().addToAmountPaid(this.amount);
			}
		
		}
		

		this.disableDevice();

		this.amount = BigDecimal.ZERO;
		this.bank = null;
	}

	/**
	 * Enables payment
	 * @param issuer
	 * 		The CardIssuer
	 * @param amount
	 * 		The amount to pay
	 */
	public void enablePayment(CardIssuer issuer, BigDecimal amount) {
		this.enableDevice();
		this.bank = issuer;
		this.amount = amount;
	}
}
