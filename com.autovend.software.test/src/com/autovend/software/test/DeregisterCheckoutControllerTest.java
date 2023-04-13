package com.autovend.software.test;

import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.software.controllers.CheckoutController;

import Configuration.GlobalConfigurations;
import Networking.NetworkController;

public class DeregisterCheckoutControllerTest {

    private CheckoutController createCheckoutController() {
        SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), GlobalConfigurations.BILLDENOMINATIONS, GlobalConfigurations.COINDENOMINATIONS, 1000, 1);
        return new CheckoutController("Test checkout station", selfCheckoutStation);
    }
	@Test
	public void deregsiterCheckoutControllerTest() {
		CheckoutController checkoutControllerTest = createCheckoutController();
		NetworkController.registerAttendentController(null);
		NetworkController.deregisterAllStations();
		NetworkController.registerCheckoutStation("Test checkout station", checkoutControllerTest);
		NetworkController.deregisterCheckoutStation("Test checkout station");
		assertEquals(0, NetworkController.getCheckoutStationNames().size());
	}
}
