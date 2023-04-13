package com.autovend.software.test;

import com.autovend.Coin;
import com.autovend.devices.CoinTray;
import com.autovend.devices.DisabledException;
import com.autovend.devices.OverloadException;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.CoinTrayController;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

public class CoinTrayControllerTest {
    
    private CoinTray coinTray;
    private CoinTrayController coinTrayController;
    private CheckoutController checkoutController;
    
    @Before
    public void setup() {
        coinTray = new CoinTray(10);
        coinTrayController = new CoinTrayController(coinTray);
    }
    
    @Test
    public void testReactToCoinAddedEvent() throws DisabledException, OverloadException {
        // Test that the tray does not dispense change when it is empty
        coinTrayController.reactToCoinAddedEvent(coinTray);
        
        // Add a few coins to the tray and test that it dispenses change
        coinTray.accept(new Coin(BigDecimal.valueOf(5), Currency.getInstance("CAD")));
        coinTray.accept(new Coin(BigDecimal.valueOf(10), Currency.getInstance("CAD")));
        coinTrayController.reactToCoinAddedEvent(coinTray);
    }
}
