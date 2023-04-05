package com.autovend.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.autovend.Bill;
import com.autovend.Coin;
import com.autovend.devices.BillStorage;
import com.autovend.devices.CoinStorage;
import com.autovend.devices.OverloadException;
import com.autovend.devices.SimulationException;
import com.autovend.software.AdjustBanknotesForChange;

public class AdjustBanknotesTest {

    private AdjustBanknotesForChange adjuster = new AdjustBanknotesForChange();
	private CoinStorage coinStorage = new CoinStorage(100);

    @Before
    public void setUp() {
        adjuster = new AdjustBanknotesForChange();
    }

    @Test
    public void testAdjustBillToCoin() throws OverloadException {
        Bill bill = new Bill(6, Currency.getInstance("CAD"));
        List<List<Integer>> expectedCoins = Arrays.asList(Arrays.asList(25, 25, 25, 25,
        		25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25));
        List<List<Integer>> actualCoins = adjuster.adjustBillToCoin(bill);
        assertEquals(expectedCoins, actualCoins);
    }

    @Test(expected = OverloadException.class)
    public void testAdjustBillToCoinWithLargeBill() throws OverloadException {
        Bill bill = new Bill(20, Currency.getInstance("CAD"));
        adjuster.adjustBillToCoin(bill);
    }
    
    @Test
    public void testCheckCoinLevelTest() throws SimulationException, OverloadException {
        AdjustBanknotesForChange adjustBanknotesForChange = new AdjustBanknotesForChange();
        Coin coin = new Coin(BigDecimal.valueOf(10.0), Currency.getInstance("CAD"));
        for (int i = 0; i < AdjustBanknotesForChange.LOW_THRESHOLD - 1; i++) {
        	adjustBanknotesForChange.refillCoin(coin);
        }
        adjustBanknotesForChange.checkCoinLevel();
        assertEquals("Coin level is low, please refill.", adjustBanknotesForChange.message);
    }

}
