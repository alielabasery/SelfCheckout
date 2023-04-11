package com.autovend.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
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
	private List<BarcodedProduct> testCatalog;
	@Before
	public void setup() {
		touchscreen = new TouchScreen();
		checkout = new CheckoutController();
		browser = new AddItemByBrowsingController(touchscreen);
		testCatalog = new ArrayList<BarcodedProduct>();
		
		Barcode breadCode = new Barcode(Numeral.one, Numeral.two, Numeral.three);
		BarcodedProduct breadsticks = new BarcodedProduct(breadCode, "breadsticks", new BigDecimal(5), 21);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(breadCode, breadsticks);
		
		Barcode gatorCode = new Barcode(Numeral.one, Numeral.zero, Numeral.zero);
		BarcodedProduct gatorade = new BarcodedProduct(gatorCode, "gatorade", new BigDecimal(2.5), 500);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(gatorCode, gatorade);
		
		Barcode jellyCode = new Barcode(Numeral.two, Numeral.three, Numeral.zero);
		BarcodedProduct jellyStraws = new BarcodedProduct(jellyCode, "jelly straws", new BigDecimal(13), 1400);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(jellyCode, jellyStraws);
		
		testCatalog.add(breadsticks);
		testCatalog.add(gatorade);
		testCatalog.add(jellyStraws);
		
		browser.setMainController(checkout);
		browser.addProducts();
	}
	
	@After
	public void teardown() {
		touchscreen = null;
		checkout = null;
		ProductDatabases.BARCODED_PRODUCT_DATABASE.clear();
	}
	
	/*
	 * Test to see if catalog is populated with products correctly
	 */
	@Test
	public void getCatalog() {
		List<BarcodedProduct> catalog = browser.getCatalog();
		boolean match = catalog.containsAll(testCatalog);
		assertTrue(match);
		assertFalse(catalog.isEmpty());
		assertEquals(3, catalog.size());
	}
	
	/*
	 * Test to see if correct price of breadsticks is returned
	 */
	@Test
	public void getBreadsticksPrice() {
		browser.getProduct("breadsticks", "1");
		BigDecimal breadstick_price = browser.getPrice();
		assertEquals(new BigDecimal(5), breadstick_price);
	}
	
	/*
	 * Test to see if correct weight of breadsticks is returned
	 */
	@Test
	public void getBreadsticksWeight() {
		browser.getProduct("breadsticks", "1");
		double breadstick_weight = browser.getWeight();
		assertEquals(21, breadstick_weight, 0);
	}
	
	/*
	 * Test to see if correct barcode of breadsticks is returned
	 */
	@Test
	public void getBreadsticksBarcode() {
		Barcode breadCode = new Barcode(Numeral.one, Numeral.two, Numeral.three);
		browser.getProduct("breadsticks", "1");
		BarcodedProduct selectedProduct = browser.getBarcodedProduct();
		Barcode selectedBarcode = selectedProduct.getBarcode();
		assertEquals(breadCode, selectedBarcode);
	}
	
	/*
	 * Test to see if correct description of breadsticks is returned
	 */
	@Test
	public void getBreadsticksDescription() {
		browser.getProduct("breadsticks", "1");
		BarcodedProduct selectedProduct = browser.getBarcodedProduct();
		String description = selectedProduct.getDescription();
		assertEquals("breadsticks", description);
	}
	
	/*
	 * Test to see if correct price of multiple breadsticks is returned
	 */
	@Test 
	public void getBreadsticksMultipliedPrice() {
		browser.getProduct("breadsticks", "3");
		BigDecimal breadstick_price = browser.getPrice();
		assertEquals(new BigDecimal(15), breadstick_price);
	}
	
	/*
	 * Test to see if an exception is thrown when an invalid item is selected
	 * p.s. this case is probably impossible to occur in application as the GUI should not allow for the
	 * selection of an invalid item
	 */
	@Test (expected = NullPointerException.class)
	public void getInvalidItem() {
		browser.getProduct("fried chicken", "1");
	}
	
	

}
