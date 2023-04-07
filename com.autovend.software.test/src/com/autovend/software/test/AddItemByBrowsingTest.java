package com.autovend.software.test;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.devices.TouchScreen;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.software.controllers.AddItemByBrowsingController;
import com.autovend.software.controllers.CheckoutController;

public class AddItemByBrowsingTest {
	
	private TouchScreen touchscreen;
	private CheckoutController checkout;
	private AddItemByBrowsingController browser;
	
	@Before
	public void setup() {
		touchscreen = new TouchScreen();
		checkout = new CheckoutController();
		browser = new AddItemByBrowsingController(touchscreen);
		
		Barcode breadCode = new Barcode(Numeral.one, Numeral.two, Numeral.three);
		BarcodedProduct breadsticks = new BarcodedProduct(breadCode, "Breadsticks", new BigDecimal(5), 21);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(breadCode, breadsticks);
		
		browser.setMainController(checkout);
	}
	
	@After
	public void teardown() {
		touchscreen = null;
		checkout = null;
	}
	
	
	

}
