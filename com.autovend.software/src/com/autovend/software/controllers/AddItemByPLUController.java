package com.autovend.software.controllers;


import com.autovend.PriceLookUpCode;
import com.autovend.PriceLookUpCodedUnit;
import com.autovend.SellableUnit;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.TouchScreenObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.utils.BarcodeUtils;

/**
 * Controller for adding items by their PLU code, communicates with main checkout
 * controller to add items to order.
 */
public class AddItemByPLUController extends ItemAdderController<TouchScreen, TouchScreenObserver> implements TouchScreenObserver {

    /*
    1. Customer I/O: Displays a virtual numeric keyboard.
    2. Customer I/O: Accepts the customer’s input of the PLU code.
    3. Customer I/O: Signals to the System that an item with a given PLU code is being added.
    4. System: Blocks the self-checkout station from further input.
    5. Customer I/O: Signals to the customer to add the item to the Bagging Area.
    6. Bagging Area: Signals the System that the weight has changed.
    7. System: Unblocks the station.
    8. Customer I/O: Returns to its standard display.


    1. Simple mechanisms for self-correction by the customer,like a backspace key,should be supported.
    2. The customer ought to have the opportunity to see that the PLU does not correspond to their item.
        At this point, communication with the attendant is likely the best option for correcting the problem.
     */

    public AddItemByPLUController(TouchScreen touchScreen) {
        super(touchScreen);
    }

    public void reactToAddByPLUCodeEvent(TouchScreen touchScreen) {
        // 3. Customer I/O: Signals to the System that an item with a given PLU code is being added.
        // 4. System: Blocks the self-checkout station from further input.
        disableDevice();

        // 1. Customer I/O: Displays a virtual numeric keyboard.
        // 1. Simple mechanisms for self-correction by the customer, like a backspace key, should be supported.
        // displayNumericKeyboard() return type: return pluString + "," + exit;
        String result = displayNumericKeyboard();  // provided by GUI team
        String[] values = result.split(",");
        String pluString = values[0];
        boolean exit = Boolean.parseBoolean(values[1]);  // true if user wants to exit the process of entering a PLU code

        if (exit) {
            enableDevice();
            // resetGUI() executes point no. 8
            resetGUI();  // provided by GUI team
            return;
        }

        PriceLookUpCode pluCode = BarcodeUtils.stringPLUToPLU(pluString);

        PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);

        double productWeight = 0;

        // trying to get the weight having only the PlU product
        for (BarcodedProduct barcodedProduct : ProductDatabases.BARCODED_PRODUCT_DATABASE.values()) {
            if (barcodedProduct.getDescription().equalsIgnoreCase(product.getDescription())) {
                productWeight = barcodedProduct.getExpectedWeight();
            }
        }

        if (product != null) {
            // 6. Bagging Area: Signals the System that the weight has changed.
            this.getMainController().addItem(this, product, productWeight);
        }
        else {
            while (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(pluCode)) {

                // 2. The customer ought to have the opportunity to see that the PLU does not correspond to their item.
                // At this point, communication with the attendant is likely the best option for correcting the problem.
                // incorrectPLUCode() executes point no. 2
                incorrectPLUCode();  // provided by GUi team

                result = displayNumericKeyboard();  // provided by GUI team
                values = result.split(",");
                pluString = values[0];
                exit = Boolean.parseBoolean(values[1]);  // true if user wants to exit the process of entering a PLU code

                if (exit) {
                    enableDevice();
                    // resetGUI() executes point no. 8
                    resetGUI();  // provided by GUI team
                    return;
                }

                pluCode = BarcodeUtils.stringPLUToPLU(pluString);

                product = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);

                productWeight = 0;

                // trying to get the weight having only the PLU product
                for (BarcodedProduct barcodedProduct : ProductDatabases.BARCODED_PRODUCT_DATABASE.values()) {
                    if (barcodedProduct.getDescription().equalsIgnoreCase(product.getDescription())) {
                        productWeight = barcodedProduct.getExpectedWeight();
                    }
                }
            }
            // 6. Bagging Area: Signals the System that the weight has changed.
            this.getMainController().addItem(this, product, productWeight);

        }
        // 5. Customer I/O: Signals to the customer to add the item to the Bagging Area.
        // signalToAddToBaggingArea() executes point no. 5
        signalToAddToBaggingArea();  // provided by GUI team

        // 7. System: Unblocks the station.
        enableDevice();

        // 8. Customer I/O: Returns to its standard display.
        // resetGUI() executes point no. 8
        resetGUI();  // provided by GUi team
    }

}