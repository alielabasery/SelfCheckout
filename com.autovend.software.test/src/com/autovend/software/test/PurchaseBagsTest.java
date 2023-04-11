// Placeholder for Group 6: Names + UCID

package com.autovend.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Numeral;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.ElectronicScale;
import com.autovend.devices.TouchScreen;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.Product;
import com.autovend.software.controllers.BarcodeScannerController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.ElectronicScaleController;
import com.autovend.software.controllers.PurchaseBagController;

public class PurchaseBagsTest {

	PurchaseBagController bagController;
	TouchScreen stubScreen;
	ElectronicScaleController scaleController;
	ElectronicScale stubScale;
	BarcodedProduct newBag;

	BarcodedUnit validUnit;
	CheckoutController checkoutController;

	private Scanner scan;

	@Before
	public void setup() {
		newBag = new BarcodedProduct(new Barcode(Numeral.one, Numeral.zero), "new bag", BigDecimal.valueOf(2.50),
				500.0);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(newBag.getBarcode(), newBag);
		checkoutController = new CheckoutController();

		validUnit = new BarcodedUnit(new Barcode(Numeral.three, Numeral.three), 500.0);

		stubScreen = new TouchScreen();
		stubScale = new ElectronicScale(1000, 1);

		bagController = new PurchaseBagController(stubScreen);
		bagController.setMainController(checkoutController);

		scaleController = new ElectronicScaleController(stubScale);
		scaleController.setMainController(checkoutController);

		scan = new Scanner(System.in);
	}

	/**
	 * Tears down objects so they can be initialized again with setup
	 */
	@After
	public void teardown() {

		checkoutController = null;
		stubScreen = null;
		bagController = null;
		scaleController = null;
		stubScale = null;
		scan.close();
	}

	/**
	 * Test to check the method with 0 bags
	 */
	@Test
	public void testGetBagNumber_zeroBags() {
		String input = "0";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		assertEquals(0, checkoutController.getBagNumber());
	}

	/**
	 * Test to check the method with valid inputs
	 */
	@Test
	public void testGetBagNumber_validInput() {
		String input = "5";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		assertEquals(5, checkoutController.getBagNumber());
	}

	/**
	 * Test to check the method with invalid inputs
	 */
	@Test
	public void testGetBagNumber_invalidInput() {
		String input = "five";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		assertThrows(NumberFormatException.class, () -> checkoutController.getBagNumber());
	}

	/**
	 * Test to check the method with empty input
	 */
	@Test
	public void testGetBagNumber_emptyInput() {
		String input = "";
		System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
		assertThrows(NoSuchElementException.class, () -> checkoutController.getBagNumber());
	}

	/**
	 * Test to check if the method returns if the number of bags is 0
	 */
	@Test
	public void testPurchaseBags_0Bags() {

		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), 0);

		// Unblocks the station and lets a new item be scanned
		checkoutController.baggedItemsValid(scaleController);

		assertEquals(BigDecimal.ZERO, checkoutController.getCost());
	}

	/**
	 * Test to check if the bags get added to the order correctly and the cost is
	 * calculated correctly
	 */
	@Test
	public void testPurchaseBags_checkOrder() {

		// Set the number of bags to 3 bags;
		int numBags = 3;

		// create the variable to calculate the cost of the bags in total
		BigDecimal expectedPrice = BigDecimal.ZERO;
		HashMap<Product, Number[]> order = checkoutController.getOrder();

		// Add the bag to the order
		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), numBags);
		expectedPrice = expectedPrice.add(newBag.getPrice().multiply(BigDecimal.valueOf(numBags)));

		// Unblocks the station and lets a new item be scanned
		checkoutController.baggedItemsValid(scaleController);

		// Check that the bag number and cost in the order were updated correctly
		assertEquals(numBags, order.get(newBag)[0]);
		assertEquals(expectedPrice, checkoutController.getCost());

	}

	/**
	 * Test to check if the bags get added correctly after the bag addition prompt is used multiple times 
	 * Also checks if the cost is getting calculated correctly
	 */
	@Test
	public void testPurchaseBags_multipleBags() {

		// create the variable to calculate the cost of the bags in total
		BigDecimal expectedPrice = BigDecimal.ZERO;
		// Set the number of bags to 4 bags;
		int numBags = 4;

		HashMap<Product, Number[]> order = checkoutController.getOrder();

		// Purchase 2 bags
		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), 2);
		expectedPrice = expectedPrice.add(newBag.getPrice().multiply(BigDecimal.valueOf(2)));

		// Unblocks the station and lets a new bag be scanned
		checkoutController.baggedItemsValid(scaleController);

		// Purchase 1 bag
		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), 1);
		expectedPrice = expectedPrice.add(newBag.getPrice().multiply(BigDecimal.valueOf(1)));

		// Unblocks the station and lets a new bag be scanned
		checkoutController.baggedItemsValid(scaleController);

		// Purchase 1 bag
		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), 1);
		expectedPrice = expectedPrice.add(newBag.getPrice().multiply(BigDecimal.valueOf(1)));

		// Unblocks the station and lets a new bag be scanned
		checkoutController.baggedItemsValid(scaleController);

		// Checking that the bags were added to the order with correct bag numbers and
		// cost
		assertEquals(numBags, order.get(newBag)[0]);
		assertEquals(expectedPrice, checkoutController.getCost());
	}

	/**
	 * A method to test that bag is not added when baggingItemLock or systemProtectionLock are true
	 */
	@Test
	public void testDisabledLocks() {
		// Set the number of bags
		int numBags = 2;

		// Enables baggingItemLock
		checkoutController.baggingItemLock = true;

		// Adds Bag
		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), numBags);

		// Bag should not be added, order size should be 0
		assertEquals(0, checkoutController.getOrder().size());

		// Bag should not be added, and the cost should be 0
		assertEquals(BigDecimal.ZERO, checkoutController.getCost());

		// Disables baggingItemLock
		checkoutController.baggingItemLock = false;

		// Enables systemProtectionLock
		checkoutController.systemProtectionLock = true;

		// Adds the bag
		checkoutController.purchaseBags(bagController, newBag, validUnit.getWeight(), numBags);

		// Bag should not be added, order size should be 0
		assertEquals(0, checkoutController.getOrder().size());

		// Bag should not be added, and the cost should be 0
		assertEquals(BigDecimal.ZERO, checkoutController.getCost());
	}

	/**
	 * A method to test that bag is not added when the ItemAdderController is null
	 */
	@Test
	public void testPurchaseBags_invalidItemControllerAdder() {
		// Set the number of bags
		int numBags = 2;

		// purchaseBags is called with an invalid ItemControllerAdder
		checkoutController.purchaseBags(null, newBag, validUnit.getWeight(), numBags);

		// Bag should not be added, order size should be 0
		assertEquals(0, checkoutController.getOrder().size());

		// Bag should not be added, and the cost should be 0
		assertEquals(BigDecimal.ZERO, checkoutController.getCost());

	}

	/**
	 * Tests purchaseBags with an bag that has an invalid weight, and bag that is null
	 */
	@Test
	public void testPurchaseBags_invalidParameters() {
		// set the number of bags
		int numBags = 2;

		// Scan bag with negative weight
		checkoutController.purchaseBags(bagController, newBag, -1, numBags);

		// Bag should not be added, and order size should be 0
		assertEquals(0, checkoutController.getOrder().size());

		// Bag should not be added, and the cost should be 0
		assertEquals(BigDecimal.ZERO, checkoutController.getCost());

		// Scan null bag
		checkoutController.purchaseBags(bagController, null, validUnit.getWeight(), numBags);

		// Bag should not be added, and order size should be 0
		assertEquals(0, checkoutController.getOrder().size());

		// Bag should not be added, and the cost should be 0
		assertEquals(BigDecimal.ZERO, checkoutController.getCost());
	}
	
	/**
	 * Tests purchaseBags with an invalid ItemAdderController, and bag that is null
	 */
	@Test
	public void testPurchaseBags_nullAdder_nullBag() {
		// Set the number of bags
		int numBags = 2;

		// purchaseBags is called with an invalid ItemControllerAdder and null bag
		checkoutController.purchaseBags(null, null, validUnit.getWeight(), numBags);

		// Bag should not be added, order size should be 0
		assertEquals(0, checkoutController.getOrder().size());

		// Bag should not be added, and the cost should be 0
		assertEquals(BigDecimal.ZERO, checkoutController.getCost());

	}

}
