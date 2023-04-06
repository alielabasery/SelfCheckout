package com.autovend.software.test;

import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;
import com.autovend.PriceLookUpCodedUnit;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.DisabledException;
import com.autovend.devices.TouchScreen;
import com.autovend.external.ProductDatabases;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;
import com.autovend.software.controllers.AddItemByPLUController;
import com.autovend.software.controllers.CheckoutController;

import java.math.BigDecimal;
import java.util.Set;


/**
 * Test class for the add item by PLU code use case
 */
public class AddItemByPLUTest {

    private CheckoutController checkoutController;
    private AddItemByPLUController addItemByPLUController;
    private TouchScreen touchScreen;

    PLUCodedProduct product1;
    PLUCodedProduct product2;

    PriceLookUpCodedUnit unit1;
    PriceLookUpCodedUnit unit2;

    static final PriceLookUpCode PLUCODE_1 = new PriceLookUpCode(Numeral.one, Numeral.two, Numeral.three, Numeral.four);
    static final PriceLookUpCode PLUCODE_2 = new PriceLookUpCode(Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five);


    /**
     * Setup for testing
     */
    @Before
    public void setup() {
        checkoutController = new CheckoutController();
        touchScreen = new TouchScreen();

        unit1 = new PriceLookUpCodedUnit(PLUCODE_1, 1f);
        unit2 = new PriceLookUpCodedUnit(PLUCODE_2, 2f);

        product1 = new PLUCodedProduct(PLUCODE_1, "Item 1", BigDecimal.valueOf(1f));
        product2 = new PLUCodedProduct(PLUCODE_2, "Item 2", BigDecimal.valueOf(2f));

        ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUCODE_1, product1);
        ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUCODE_2, product2);

        addItemByPLUController.setMainController(checkoutController);

        touchScreen.register(addItemByPLUController);  // not sure abt this
    }


    /**
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

        ProductDatabases.PLU_PRODUCT_DATABASE.remove(PLUCODE_1);
        ProductDatabases.PLU_PRODUCT_DATABASE.remove(PLUCODE_2);
    }


    // Testing AddItemByPLUController methods


    /**
     * Tests that AddItemBYPLUController reacts correctly to the addition of an
     * item in the database
     */
    @Test
    public void testValidItem() {

    }

    /**
     * Tests that AddItemBYPLUController reacts correctly to the addition of an
     * item not in the database
     */
    @Test
    public void testNotFoundItem() {

    }


    //	Testing ItemAdderController methods with AddItemByPLUController


    /**
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


    /**
     * Tests that the disableDevice method of DeviceController causes a
     * DisabledException to be thrown when a scan is attempted
     */
    @Test(expected = DisabledException.class)
    public void testDisabledAddItemController() {
        addItemByPLUController.disableDevice();
        .scan();
    }


    /**
     * Tests that the enableDevice method of DeviceController works correctly,
     * allowing scans to take place again
     */
    @Test
    public void testReenabledScanController() {
        addItemByPLUController.disableDevice();
        addItemByPLUController.enableDevice();
        while (!.(validUnit1)) {
        } // loop until successful scan
        Set<Product> orderSet = addItemByPLUController.getMainController().getOrder().keySet();
        Product[] orderArr = orderSet.toArray(new Product[orderSet.size()]);
        assertSame("Scanned product should be in the order list", orderArr[0].getPrice(), databaseItem1.getPrice());
    }


    /**
     * Tests that the setDevice method of DeviceController correctly replaces the
     * old BarcodeScanner with the new one
     */
    @Test
    public void testNewScanner() {
        BarcodeScanner newScanner = new BarcodeScanner();
        addItemByPLUController.setDevice();
        assertNotSame("New barcode scanner should be ..", stubScanner, addItemByPLUController.getDevice());
    }




}
