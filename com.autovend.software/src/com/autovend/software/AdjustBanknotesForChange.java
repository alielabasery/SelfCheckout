package com.autovend.software;

import java.util.ArrayList;
import java.util.Arrays;
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

	 public void checkCoinLevel() {
	        if (coinStorage.getCoinCount() < LOW_THRESHOLD) {
	            message = "Coin level is low, please refill.";
	        }
	    }

	    public void refillCoin(Coin coin) throws SimulationException, OverloadException {
	    	coinStorage.load(coin);
	    }
	    
	    public boolean billValueCheck(Bill bill) {
	    	if (bill.getValue() < 10) {
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    
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
	    
