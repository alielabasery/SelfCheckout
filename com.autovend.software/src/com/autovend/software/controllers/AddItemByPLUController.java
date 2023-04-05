package com.autovend.software.controllers;


import com.autovend.PriceLookUpCode;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.TouchScreenObserver;
import com.autovend.external.ProductDatabases;
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
        String pluString = "";

        // set to true if the user wants to exit the process of entering a PLU code
        Boolean exit = false;

        // 3. Customer I/O: Signals to the System that an item with a given PLU code is being added.
        // 4. System: Blocks the self-checkout station from further input.
        disableDevice();

        // 1. Customer I/O: Displays a virtual numeric keyboard.
        // 1. Simple mechanisms for self-correction by the customer, like a backspace key, should be supported.
        pluString, exit = displayNumericKeyboard();  // would return the PlU code entered by the user, and bool exit

        if (exit)
            return;

        PriceLookUpCode pluCode = BarcodeUtils.stringPLUToPLU(pluString);

        PLUCodedProduct item = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);

        if (item != null) {
            this.getMainController().addItem(this, item, item.getExpectedWeight());
        }
        else {
            while (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(pluCode)) {

                if (exit)
                    break;

                // 2. The customer ought to have the opportunity to see that the PLU does not correspond to their item.
                // At this point, communication with the attendant is likely the best option for correcting the problem.

                pluString, exit = displayNumericKeyboard();
                pluCode = BarcodeUtils.stringPLUToPLU(pluString);
            }
        }

        // 5. Customer I/O: Signals to the customer to add the item to the Bagging Area.


        // 6. Bagging Area: Signals the System that the weight has changed.


        // 7. System: Unblocks the station.
        enableDevice();

        // 8. Customer I/O: Returns to its standard display.
        resetGUI();
    }

}