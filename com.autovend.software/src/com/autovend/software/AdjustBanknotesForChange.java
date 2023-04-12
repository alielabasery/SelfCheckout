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

package com.autovend.software;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.autovend.Bill;
import com.autovend.Coin;
import com.autovend.devices.BillStorage;
import com.autovend.devices.CoinStorage;
import com.autovend.devices.OverloadException;
import com.autovend.devices.SimulationException;

public class AdjustBanknotesForChange {
	public static final int LOW_THRESHOLD = 10; // low threshold for banknote storage
	private static final Currency CURRENCY = Currency.getInstance("CAD"); // currency for banknotes
	public String message = " ";
	public int[] coinValues = {25, 10, 5, 1};


	private BillStorage billStorage = new BillStorage(100);
	private CoinStorage coinStorage = new CoinStorage(100);

	/**
	 * Checks the level of the coins
	 */
	public void checkCoinLevel() {
		if (coinStorage.getCoinCount() < LOW_THRESHOLD) {
			message = "Coin level is low, please refill.";
		}
	}

	/**
	 * Refill the coin dispenser
	 * @param coin
	 * 		Coin to fill
	 * @throws SimulationException
	 * 		If coinStorage throws this exception
	 * @throws OverloadException
	 * 		If coinStorage throws this exception
	 */
	public void refillCoin(Coin coin) throws SimulationException, OverloadException {
		coinStorage.load(coin);
	}

	/**
	 * Check the value of the bill
	 * @param bill
	 * 		The bill to check
	 * @return
	 * 		Boolean if the bill is less than 10
	 */
	public boolean billValueCheck(Bill bill) {
		if (bill.getValue() < 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adjusts Bills to Coins
	 * @param bill
	 * 		Bill to adjust
	 * @return
	 * 		List of integer lists of coin values
	 * @throws OverloadException
	 * 		if the bill is too large
	 */
	public List<List<Integer>> adjustBillToCoin (Bill bill) throws OverloadException {
		if (billValueCheck(bill)) {
			billStorage.load(bill);
			List<List<Integer>> coins = new ArrayList<>();
			int billInCents = bill.getValue() * 100;

			for (int i = 0; i < coinValues.length; i++) {
				int coinValue = coinValues[i];
				List<Integer> coinSet = new ArrayList<>();
				while (billInCents >= coinValue) {
					coinSet.add(coinValue);
					billInCents -= coinValue;
				}
				if (!coinSet.isEmpty()) {
					coins.add(coinSet);
				}
			}
			return coins;
		} 
		throw new OverloadException("Bill too large");
	}
}

