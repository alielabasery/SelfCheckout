package com.autovend.software.test;

import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;
import com.autovend.PriceLookUpCodedUnit;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.DisabledException;
import com.autovend.devices.SimulationException;
import com.autovend.devices.TouchScreen;
import com.autovend.external.ProductDatabases;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;
import com.autovend.software.controllers.AddItemByPLUController;
import com.autovend.software.controllers.CheckoutController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;


/**
 * Test class for the add item by PLU code use case
 */
public class AddItemByPLUTest {

    private CheckoutController checkoutController;
    private AddItemByPLUController addItemByPLUController;
    private TouchScreen touchScreen;
    private List<PLUCodedProduct> testCatalog;

    PLUCodedProduct product1;
    PLUCodedProduct product2;

    PriceLookUpCodedUnit unit1;
    PriceLookUpCodedUnit unit2;

    static final PriceLookUpCode PLUCODE_1 = new PriceLookUpCode(Numeral.one, Numeral.two, Numeral.three, Numeral.four);
    static final PriceLookUpCode PLUCODE_2 = new PriceLookUpCode(Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five);


    /*
     * Setup for testing
     */
    @Before
    public void setup() {
        checkoutController = new CheckoutController();
        touchScreen = new TouchScreen();
        addItemByPLUController = new AddItemByPLUController(touchScreen);
        testCatalog = new ArrayList<PLUCodedProduct>();

        unit1 = new PriceLookUpCodedUnit(PLUCODE_1, 1f);
        unit2 = new PriceLookUpCodedUnit(PLUCODE_2, 2f);

        product1 = new PLUCodedProduct(PLUCODE_1, "Item 1", new BigDecimal("4.0"));
        product2 = new PLUCodedProduct(PLUCODE_2, "Item 2", new BigDecimal("2.5"));

        ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUCODE_1, product1);

        testCatalog.add(product1);

        addItemByPLUController.setMainController(checkoutController);
        addItemByPLUController.addProducts();

        touchScreen.register(addItemByPLUController);
    }


    /*
     * Tears down objects so that they can be initialized again with setup
     */
    @After
    public void teardown() {
        checkoutController = null;
        touchScreen = null;

        unit1 = null;
        unit2 = null;

        product1 = null;
        product2 = null;

        ProductDatabases.PLU_PRODUCT_DATABASE.clear();
    }


    /*
     * Test to see if catalog is populated with products correctly
     */
    @Test
    public void getCatalog() {
        List<PLUCodedProduct> catalog = addItemByPLUController.getCatalog();
        boolean match = catalog.containsAll(testCatalog);
        assertTrue(match);
        assertFalse(catalog.isEmpty());
        assertEquals(1, catalog.size());
    }


    /*
     * Test to see if correct price of product1 is returned
     */
    @Test
    public void getProductPrice() {
        addItemByPLUController.handleInputPLU(PLUCODE_1, "1");
        BigDecimal product1_price = addItemByPLUController.getPrice();
        assertEquals(new BigDecimal(4), product1_price);
    }


    /*
     * Test to see if correct description of product1 is returned
     */
    @Test
    public void getProductDescription() {
        addItemByPLUController.handleInputPLU(PLUCODE_1, "1");
        String description = addItemByPLUController.getNewItemDescription();
        assertEquals("Item 1", description);
    }


    /*
     * Test to see if correct price of multiple products is returned
     */
    @Test
    public void getItemsMultipliedPrice() {
        addItemByPLUController.handleInputPLU(PLUCODE_1, "5");
        BigDecimal items_price = addItemByPLUController.getPrice();
        assertEquals(new BigDecimal(20), items_price);
    }


    /*
     * Tests that AddItemBYPLUController reacts correctly to the addition of an
     * item not in the database
     */
    @Test (expected = SimulationException.class)
    public void testNotFoundItem() {
        addItemByPLUController.handleInputPLU(PLUCODE_2, "1.0");
    }


    // Testing ItemAdderController methods with AddItemByPLUController

    /*
     * Tests that the setMainController method of ItemAdderController correctly
     * replaces the controller's main controller and deregisters the controller from
     * the old CheckoutController
     */
    @Test
    public void testNewMainController() {
        CheckoutController newMainController = new CheckoutController();
        addItemByPLUController.setMainController(newMainController);

        assertNotSame("New checkout controller should be set in AddItemByPLUController field", checkoutController,
                addItemByPLUController.getMainController());
        assertTrue("AddItemByPLUController should be in the new checkout controller's item adder list",
                newMainController.getAllItemAdders().contains(addItemByPLUController));
        assertTrue("AddItemByPLUController should not be in the old checkout controller's item adder list",
                checkoutController.getAllItemAdders().isEmpty());
    }


    // Testing DeviceController methods with AddItemByPLUController

    /*
     * Tests that the disableDevice method of DeviceController causes a
     * DisabledException to be thrown when an addition is attempted
     */
    @Test(expected = DisabledException.class)
    public void testDisabledAddItemController() {
        addItemByPLUController.disableDevice();
        addItemByPLUController.reactToAddByPLUEvent(touchScreen);
    }


    /*
     * Tests that the setDevice method of DeviceController correctly replaces the
     * old touch screen with the new one
     */
    @Test
    public void testNewScanner() {
        TouchScreen secondScreen = new TouchScreen();
        addItemByPLUController.setDevice(secondScreen);
        assertNotSame("New touch screen should be ..", secondScreen, addItemByPLUController.getDevice());
    }


}
