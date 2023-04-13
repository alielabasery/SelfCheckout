package com.autovend.software.test;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;

import Networking.NetworkController;

public class CheckoutPanelTesting {
	private Currency currency;
	private int[] billDenominations;
	private BigDecimal[] coinDenominations;
	private SelfCheckoutStation station;
	private CheckoutController controller;
	private AttendentController attendantController;
	private SupervisionStation attendantStation;
	
	GuiController gc;
	
	@Before
	public void setup() {
		NetworkController.deregisterAllStations();
		currency = Currency.getInstance("CAD");
		billDenominations = new int[] { 5, 10, 20, 50, 100 };
		coinDenominations = new BigDecimal[] {new BigDecimal("0.05"), new BigDecimal("0.1"), 
				new BigDecimal("0.25"), new BigDecimal("0.5"), new BigDecimal("1"), new BigDecimal("2")};
    	station = new SelfCheckoutStation(currency, billDenominations, coinDenominations, 1000, 1);
    	controller = new CheckoutController("Station 1", station);
    	
    	attendantStation = new SupervisionStation();
    	attendantController = new AttendentController(attendantStation);
    	    	
		gc = new GuiController(attendantController);
	}
	
	@Test
	public void testCheckoutPanel() {
		gc.checkoutScreen(controller);
		gc.cp.selectedButton = "Credit/Debit Card";
		gc.cp.btnFinishAndPay.doClick();
	}
	
	@Test
	public void testCheckoutPanelGift() {
		gc.checkoutScreen(controller);
		gc.cp.selectedButton = "Gift Card";
		gc.cp.btnFinishAndPay.doClick();
		gc.cp.proceedToGiftCardPaymentMethod();
	}
	
	@Test
	public void testCheckoutPanelCash() {
		gc.checkoutScreen(controller);
		gc.cp.selectedButton = null;
		gc.cp.btnFinishAndPay.doClick();
		gc.cp.proceedToCashPaymentMethod();
	}
	
	@Test
	public void testCheckoutPanelSuccessScreen() {
		gc.checkoutScreen(controller);
		gc.cp.btnFinishAndPay.doClick();
		gc.cp.successfulPaymentScreen();
	}
}
