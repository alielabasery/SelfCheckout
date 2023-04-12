package com.autovend.software.test;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;

import Configuration.GlobalConfigurations;
import Networking.NetworkController;
import junit.framework.Assert;

public class DeregisterCheckoutControllerTest {

    private CheckoutController createCheckoutController() {
        SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), GlobalConfigurations.BILLDENOMINATIONS, GlobalConfigurations.COINDENOMINATIONS, 1000, 1);
        return new CheckoutController("Test checkout station", selfCheckoutStation);
    }
	@Test
	public void deregsiterCheckoutControllerTest() {
		CheckoutController checkoutControllerTest = createCheckoutController();
		NetworkController.registerCheckoutStation("Test checkout station", checkoutControllerTest);
		NetworkController.deregisterCheckoutStation("Test checkout station");
		Assert.assertEquals(0, NetworkController.getCheckoutStationNames().size());
	}
}
