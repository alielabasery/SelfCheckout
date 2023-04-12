package com.autovend.software.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.management.loading.PrivateClassLoader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.validator.PublicClassValidator;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.pojo.CartLineItem;
import com.autovend.software.pojo.Cart;
import com.autovend.software.pojo.CartLineItem.CODETYPE;
import com.autovend.software.pojo.CheckoutStation;
import com.autovend.software.pojo.ProductDescriptionEntry;
import com.autovend.software.utils.CodeUtils;

import Configuration.GlobalConfigurations;
import Networking.NetworkController;
import data.DatabaseController;
import data.ProductIndex;
import junit.framework.Assert;
import models.FoundProductsTableModel;

@SuppressWarnings("deprecation")
public class AddItemByTextSearchTest {
	
	
    int[] billDenominations = new int[] {5, 10, 20, 50, 100};
    BigDecimal[] coinDenominations = new BigDecimal[] {new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25), new BigDecimal(100), new BigDecimal(200)};
	
	@Test
	public void succesfullyFoundPLUProductByDescriptionTest() {
		ProductIndex productIndex = new ProductIndex();
		DatabaseController databaseController = new DatabaseController();
		NetworkController networkController = new NetworkController(); 
		PriceLookUpCode code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.five, Numeral.six, Numeral.eight});
		DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Gala Apples", new BigDecimal(1.50)));
		code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.one, Numeral.one});	
		DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Ambrosia Apples", new BigDecimal(1.89)));
		Barcode bcode = CodeUtils.stringBarcodeToBarcode("349896");
        DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Ham", new BigDecimal(15.00), 4.00));
        bcode = CodeUtils.stringBarcodeToBarcode("127634");
        DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Herbal Essance Shampoo", new BigDecimal(8.59), 2.09));
		List<Object> foundProducts = DatabaseController.findProductByDescription("Gala Apples");
		Assert.assertEquals("Gala Apples", ((PLUCodedProduct)foundProducts.get(0)).getDescription());
		}
	
	@Test
	public void succesfullyFoundBarcodeProductByDescriptionTest() {
		List<Object> foundProducts = DatabaseController.findProductByDescription("Ham");
		Assert.assertEquals("Ham", ((BarcodedProduct)foundProducts.get(0)).getDescription());
	}
	
	
	@Test
	public void multipleItemResultSearchTest() {
		List<Object> foundProducts = DatabaseController.findProductByDescription("apple");
		int productListSize = foundProducts.size();
		Assert.assertEquals(4, productListSize);
	}
	
	@Test
	public void getBarcodedProduct() {
		Barcode code = CodeUtils.stringBarcodeToBarcode("127634");
		BarcodedProduct product = (BarcodedProduct)DatabaseController.getProduct(code, 'B');
		assertTrue(product.getDescription().contains("Essance"));
	}
	
	@Test
	public void getPluProduct() {
		PriceLookUpCode code = CodeUtils.stringPLUToPLU("8568");
		PLUCodedProduct product = (PLUCodedProduct)DatabaseController.getProduct(code, 'P');
		assertTrue(product.getDescription().contains("Gala"));
	}
	
	@Test
	public void getProductWithInvalidType() {
		PriceLookUpCode code = CodeUtils.stringPLUToPLU("8211");
		PLUCodedProduct product = (PLUCodedProduct)DatabaseController.getProduct(code, 'L');
		assertNull(product);
	}
	
	@Test
	public void addItemToStationCartTest() {
		PriceLookUpCode code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.five, Numeral.six, Numeral.eight});
		DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Gala Apples", new BigDecimal(1.50)));
		code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.one, Numeral.one});	
		DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Ambrosia Apples", new BigDecimal(1.89)));
		Barcode bcode = CodeUtils.stringBarcodeToBarcode("349896");
        DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Ham", new BigDecimal(15.00), 4.00));
        bcode = CodeUtils.stringBarcodeToBarcode("127634");
        DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Herbal Essance Shampoo", new BigDecimal(8.59), 2.09));
		SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), billDenominations, coinDenominations, 1000, 1);
		CheckoutController checkoutController = new CheckoutController("1", selfCheckoutStation);
		AttendentController attendentController = new AttendentController();
//		attendentController.addCheckoutController("Checkout station 1", checkoutController);
		CartLineItem item = new CartLineItem("349896", CODETYPE.BARCODE, new BigDecimal(15.00), false, "Ham", 4.00, 4.00, 1);
		attendentController.addItemToStationCart("1", item);
		Assert.assertEquals("Ham", checkoutController.getCart().getCartLineItems().get(0).getDescription());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addItemToStationCartWithNullStationTest() {
		SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), billDenominations, coinDenominations, 1000, 1);
		CheckoutController checkoutController = new CheckoutController("1", selfCheckoutStation);
		AttendentController attendentController = new AttendentController();
//		attendentController.addCheckoutController("Checkout station 1", checkoutController);
		CartLineItem item = new CartLineItem("349896", CODETYPE.BARCODE, new BigDecimal(15.00), true, "Ham", 4.00, 4.00, 1);
		attendentController.addItemToStationCart("Null checkout station", item);
	}
	
	@Test
	public void getSuperVisionStationTest() {
		AttendentController attendentController = new AttendentController();
		attendentController.getSupervisionStation();
		assertNotNull(attendentController.getSupervisionStation());
	}
	
	@Test
	public void getCheckoutControllerTest() {
		SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), billDenominations, coinDenominations, 1000, 1);
		AttendentController attendentController = new AttendentController();
		CheckoutController checkoutController = new CheckoutController("1", selfCheckoutStation);
//		attendentController.addCheckoutController("Checkout station 1", checkoutController);
		assertNotNull(attendentController.getCheckoutController("1"));
	}
	
	@Test
	public void getCheckoutControllerNameTest() {
		SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), billDenominations, coinDenominations, 1000, 1);
		CheckoutController checkoutController = new CheckoutController("1", selfCheckoutStation);
		Assert.assertEquals("1", NetworkController.getCheckoutStationNames().get(0));
	}
	
	@Test
	public void getCheckoutControllerInCheckoutStationTest() {
		CheckoutController checkoutController = new CheckoutController();
		CheckoutStation checkoutStation = new CheckoutStation("Checkout station 1", checkoutController);
		assertNotNull(checkoutStation.getCheckoutController());
	}
	
	@Test
	public void getDescriptionTest() {
		Barcode code = CodeUtils.stringBarcodeToBarcode("420");
		ProductDescriptionEntry productDescriptionEntry = new ProductDescriptionEntry("Herbal Happiness", 'B', code);
		productDescriptionEntry.setDescriptiption("Herbal Happiness");
		Assert.assertEquals("Herbal Happiness", productDescriptionEntry.getDescriptiption());
	}
	
	@Test
	public void getTypeTest() {
		Barcode code = CodeUtils.stringBarcodeToBarcode("420");
		ProductDescriptionEntry productDescriptionEntry = new ProductDescriptionEntry("Herbal Happiness", 'B', code);
		productDescriptionEntry.setType('B');
		Assert.assertEquals('B', productDescriptionEntry.getType());
	}	
	
	@Test
	public void getCodeTest() {
		Barcode code = CodeUtils.stringBarcodeToBarcode("420");
		ProductDescriptionEntry productDescriptionEntry = new ProductDescriptionEntry("Herbal Happiness", 'B', code);
		productDescriptionEntry.setCode(code);
		assertEquals(code, productDescriptionEntry.getCode());
	}
	
	@Test
	public void getAttendantControllerTest() {
		new AttendentController();
		assertNotNull(NetworkController.getAttendentController());
	}
	
	@Test
	public void getCheckoutStationNameTest() {
		CheckoutController checkoutController = new CheckoutController();
		CheckoutStation checkoutStation = new CheckoutStation("Station 1", checkoutController);
		Assert.assertEquals("Station 1", checkoutStation.getCheckoutStationName());
	}
}