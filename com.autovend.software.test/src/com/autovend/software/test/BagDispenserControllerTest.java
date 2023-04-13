package com.autovend.software.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReusableBagDispenser;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.software.controllers.BagDispenserController;
import com.autovend.software.controllers.CheckoutController;

import Configuration.GlobalConfigurations;

import com.autovend.devices.ReusableBagDispenser;
import com.autovend.ReusableBag;

public class BagDispenserControllerTest {

	
    private CheckoutController createCheckoutController() {
        SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), GlobalConfigurations.BILLDENOMINATIONS, GlobalConfigurations.COINDENOMINATIONS, 1000, 1);
        return new CheckoutController("Test checkout station", selfCheckoutStation);
    }
	
	@Test 
	public void dispenseBagsTest() throws EmptyException, OverloadException {	
		CheckoutController checkoutController = createCheckoutController();
		ReusableBagDispenser dispenser = new ReusableBagDispenser(150);
		BagDispenserController bagDispenser = new BagDispenserController(dispenser);
		bagDispenser.setMainController(checkoutController);
		ReusableBag[] bags = new ReusableBag[2];
		Arrays.fill(bags, new ReusableBag());
		dispenser.load(bags);
		bagDispenser.dispenseBags();
	}
	
	
	@Test
	public void settingControllerOnceTest() {
		CheckoutController checkoutController = createCheckoutController();
		ReusableBagDispenser dispenser = new ReusableBagDispenser(150);
		BagDispenserController bagDispenser = new BagDispenserController(dispenser);
		bagDispenser.setMainController(checkoutController);
		CheckoutController testController = bagDispenser.getMainController();
		assertEquals(checkoutController, testController);
	}
	
	
	@Test
	public void settingControllerTwiceTest() {
		CheckoutController checkoutController = createCheckoutController();
		ReusableBagDispenser dispenser = new ReusableBagDispenser(150);
		BagDispenserController bagDispenser = new BagDispenserController(dispenser);
		bagDispenser.setMainController(checkoutController);
		bagDispenser.setMainController(checkoutController);
	}
	
	@Test (expected = NullPointerException.class)
	public void dispenseWithNullControllerTest() throws EmptyException {
		CheckoutController checkoutController = createCheckoutController();
		ReusableBagDispenser dispenser = new ReusableBagDispenser(150);
		BagDispenserController bagDispenser = new BagDispenserController(dispenser);
		ReusableBag[] bags = new ReusableBag[2];
		Arrays.fill(bags, new ReusableBag());
		bagDispenser.dispenseBags();
	}
	
	@Test (expected = EmptyException.class)
	public void dispenseWithEmptyDispenserTest() throws EmptyException {
		CheckoutController checkoutController = createCheckoutController();
		ReusableBagDispenser dispenser = new ReusableBagDispenser(150);
		BagDispenserController bagDispenser = new BagDispenserController(dispenser);
		bagDispenser.setMainController(checkoutController);
		dispenser.unload();
		bagDispenser.dispenseBags();
	}
}