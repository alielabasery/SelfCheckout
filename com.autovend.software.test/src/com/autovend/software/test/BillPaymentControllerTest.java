// Placeholder for Group 6: Names + UCID

package com.autovend.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedHashMap;

import com.autovend.Bill;
import com.autovend.devices.OverloadException;
import com.autovend.devices.SelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.devices.AbstractDevice;
import com.autovend.devices.BillValidator;
import com.autovend.devices.observers.BillSlotObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.Product;
import com.autovend.software.controllers.BillPaymentController;
import com.autovend.software.controllers.CheckoutController;

public class BillPaymentControllerTest extends AbstractDevice<BillSlotObserver> {
	SelfCheckoutStation selfCheckoutStation;
	CheckoutController checkoutControllerStub;
	BillPaymentController billPaymentControllerStub;
	int[] billDenominations;
	BigDecimal[] coinDenominations;
	LinkedHashMap<Product, Number[]> order;
	

	@Before
	public void setup() {
		// Init denominations
		billDenominations = new int[] {5, 10, 20, 50, 100};
		coinDenominations = new BigDecimal[] {new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25), new BigDecimal(100), new BigDecimal(200)};

		selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance("CAD"), billDenominations, coinDenominations,200, 1);

		checkoutControllerStub = new CheckoutController();
		billPaymentControllerStub = new BillPaymentController(selfCheckoutStation.billValidator);
		billPaymentControllerStub.setMainController(checkoutControllerStub);
		checkoutControllerStub.registerPaymentController(billPaymentControllerStub);

		BarcodedProduct barcodedProduct;
		barcodedProduct = new BarcodedProduct(new Barcode(Numeral.one, Numeral.one), "test item 1",
				BigDecimal.valueOf(83.29), 400.0);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
		barcodedProduct = new BarcodedProduct(new Barcode(Numeral.one, Numeral.two), "test item 2",
				BigDecimal.valueOf(50.00), 359.00);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
		barcodedProduct = new BarcodedProduct(new Barcode(Numeral.one, Numeral.three), "test item 3",
				BigDecimal.valueOf(29.99), 125.25);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
		barcodedProduct = new BarcodedProduct(new Barcode(Numeral.one, Numeral.four), "test item 4",
				BigDecimal.valueOf(9.99), 26.75);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
	}
	

	@Test
	public void partialPaymentAndRemainingAmountTest() {
		// create order cart
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(new Barcode(Numeral.one, Numeral.three));
		order = new LinkedHashMap<>();
		order.put(product, new Number[1]);

		checkoutControllerStub.setOrder(order);

		try {
			selfCheckoutStation.billInput.accept(new Bill(10, Currency.getInstance("CAD")));
		} catch (Exception ex) {
			System.out.printf("Exception " + ex.getMessage());
		}
		double amountRemaining = checkoutControllerStub.getRemainingAmount().doubleValue();
		double expextedAmount = new BigDecimal(19.99).doubleValue();
		assertEquals(amountRemaining, expextedAmount, 0);
//
//
//		billPaymentControllerStub.reactToValidBillDetectedEvent(billValidatorStub, Currency.getInstance("CAD"), 10);
//		remainingAmount = checkoutControllerStub.getRemainingAmount();
//		actualRemainingAmount = BigDecimal.valueOf(40.0);
//		assertEquals(actualRemainingAmount, remainingAmount);
	}

	@Test
	public void overpaymentTest() {
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(new Barcode(Numeral.one, Numeral.one));
		order = new LinkedHashMap<>();
		order.put(product, new Number[1]);

		checkoutControllerStub.setOrder(order);

		try {
			selfCheckoutStation.billInput.accept(new Bill(10, Currency.getInstance("CAD")));
			selfCheckoutStation.billInput.accept(new Bill(20, Currency.getInstance("CAD")));
			selfCheckoutStation.billInput.accept(new Bill(20, Currency.getInstance("CAD")));
			selfCheckoutStation.billInput.accept(new Bill(50, Currency.getInstance("CAD")));
		} catch (Exception ex) {
			System.out.printf("Exception " + ex.getMessage());
		}
		double amountRemaining = checkoutControllerStub.getRemainingAmount().doubleValue();
		double expextedAmount = new BigDecimal(-16.71).doubleValue();
		assertEquals(amountRemaining, expextedAmount, 0);
	}

	@Test (expected = OverloadException.class)
	public void invalidBillDanglingTest() throws OverloadException {
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(new Barcode(Numeral.one, Numeral.one));
		order = new LinkedHashMap<>();
		order.put(product, new Number[1]);

		checkoutControllerStub.setOrder(order);

		selfCheckoutStation.billInput.accept(new Bill(15, Currency.getInstance("CAD")));
		selfCheckoutStation.billInput.accept(new Bill(10, Currency.getInstance("CAD")));
	}

	@Test
	public void InvalidDanglingBillRemovedTest() {
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(new Barcode(Numeral.one, Numeral.one));
		order = new LinkedHashMap<>();
		order.put(product, new Number[1]);

		checkoutControllerStub.setOrder(order);

		try {
			selfCheckoutStation.billInput.accept(new Bill(15, Currency.getInstance("CAD")));
			selfCheckoutStation.billInput.removeDanglingBill();
			selfCheckoutStation.billInput.accept(new Bill(10, Currency.getInstance("CAD")));
		} catch (OverloadException e) {
			throw new RuntimeException(e);
		}
	}
}	