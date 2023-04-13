package com.autovend.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.autovend.Coin;
import com.autovend.devices.CoinValidator;
import com.autovend.devices.observers.CoinValidatorObserver;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.CoinPaymentController;

public class CoinPaymentControllerTest {
	List<BigDecimal> denominations = Arrays.asList(new BigDecimal("5.0"));
    CoinValidator validator = new CoinValidator(Currency.getInstance("CAD"), denominations);
	
    private CoinValidator coinValidator = new CoinValidator(Currency.getInstance("CAD"), denominations);
    
    private CheckoutController checkoutController;
    
    private CoinPaymentController paymentController;
    
    @Before
    public void setup() {
        paymentController = new CoinPaymentController(coinValidator);
        paymentController.setMainController(checkoutController);
    }
    
    @Test
    public void reactToValidCoinDetectedEvent_addsToAmountPaid() {
        BigDecimal coinValue = BigDecimal.valueOf(0.25);
        paymentController.reactToValidCoinDetectedEvent(coinValidator, coinValue);
    }
    
    @Test
    public void reactToInvalidCoinDetectedEvent_doesNothing() {
    	BigDecimal coinValue = BigDecimal.valueOf(-0.25);
    	Coin coin = new Coin(BigDecimal.valueOf(5.0), Currency.getInstance("CAD"));
    	paymentController.reactToValidCoinDetectedEvent(coinValidator, coinValue);
    	coinValidator.accept(coin);
        paymentController.reactToInvalidCoinDetectedEvent(coinValidator);
        assertFalse(coinValidator.accept(coin));
    }
    
    
    @Test
    public void testReactToValidCoinDetectedEvent() {
        CoinValidator validator = new CoinValidator(Currency.getInstance("CAD"), denominations);
        CoinPaymentController paymentController = new CoinPaymentController(validator);
        CheckoutController checkoutController = new CheckoutController();
        paymentController.setMainController(checkoutController);
        BigDecimal value = new BigDecimal("0.25");
        BigDecimal value1 = new BigDecimal("-0.25");

        
        paymentController.reactToValidCoinDetectedEvent(validator, value);
        
        assertNotNull(checkoutController);
        assertEquals(value1, checkoutController.getRemainingAmount());
    }
    
}
