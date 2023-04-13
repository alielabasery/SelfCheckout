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
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/

package com.autovend.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.LinkedList;
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
      List<Coin> expectedCoin = new ArrayList<Coin>();
      Coin coin = new Coin(BigDecimal.valueOf(25), Currency.getInstance("CAD"));
      for (int i = 0; i < 24; i++) {
      	expectedCoin.add(coin);
      }
      List<Coin> actualCoins = adjuster.adjustBillToCoin(bill);
      for (int i = 0; i < 24; i++) {
      	assertEquals(expectedCoin.get(i).getValue(), actualCoins.get(i).getValue());
      }
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
