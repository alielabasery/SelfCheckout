/** 
* Group Members: 
* 
* Ella Tomlinson (30140549)
* Kofi Frempong (30054189) 
* Adam Beauferris (30056865) 
* Niran Malla (30086877)
* Owen Tinning (30102041)
* Victor Campos Goitia (30106934)
* Zoe Kirsman (30113704) 
* Youssef Abdelrhafour (30085837) 
* James Rettie (30123362) 
* Rezwan Ahmed (30134609)
* Angeline Tran (30139846) 
* Saad Elkadri (30089084) 
* Dante Kirsman (30120778) 
* Riyad Abdullayev (30140509)
* Saksham Puri (30140617) 
* Faisal Islam (30140826)
* Naheen Kabir (30142101) 
* Jose Perales Rivera (30143354) 
* Aditi Yadav (30143652)
* Sahaj Malhotra () 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/
package com.autovend.software.controllers;


import com.autovend.PriceLookUpCode;
import com.autovend.PriceLookUpCodedUnit;
import com.autovend.SellableUnit;
import com.autovend.devices.DisabledException;
import com.autovend.devices.SimulationException;
import com.autovend.devices.TouchScreen;
import com.autovend.devices.observers.TouchScreenObserver;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
//import com.autovend.software.utils.BarcodeUtils;
import com.autovend.devices.observers.AbstractDeviceObserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller for adding items by their PLU code, communicates with main checkout
 * controller to add items to order.
 */
public class AddItemByPLUController extends ItemAdderController<TouchScreen, TouchScreenObserver> implements TouchScreenObserver {

    List<PLUCodedProduct> catalog = new ArrayList<PLUCodedProduct>();
    BigDecimal price;  // used for testing
    PLUCodedProduct pluProduct;
    String newItemDescription;  // used for testing


    /**
     * Constructor for the AddItemByPLUController 
     * @param touchScreen
     * 		The touch screen being used
     */
    public AddItemByPLUController(TouchScreen touchScreen) {
        super(touchScreen);
    }


    /**
     * Add products in database to catalog
     */
    public void addProducts() {
        catalog.addAll(ProductDatabases.PLU_PRODUCT_DATABASE.values());
    }


    /**
     * Gets the selected Item's PLUCodedProduct, price and weight based on quantity
     * @param pluCode
     * 		The pluCode of the item
     * @param quantity
     * 		The quantity of items
     */
    public void handleInputPLU(PriceLookUpCode pluCode, String quantity) {
        BigDecimal productQuantity = new BigDecimal(quantity);
        pluProduct = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode);

        price = pluProduct.getPrice().multiply(productQuantity);
        newItemDescription = pluProduct.getDescription();
    }


    /**
     * Reaction event when item is added by PLU
     * @param touchScreen
     * 		The touch screen being used
     */
//    public void reactToAddByPLUEvent(TouchScreen touchScreen) {
//        if(isDeviceDisabled())
//            throw new DisabledException();
//
//        if (touchScreen == null)
//            throw new SimulationException(new NullPointerException("touch screen cannot be null"));
//
//        disableDevice();
//
//        String pluString = displayNumericKeyboard();  // provided by GUI team, passes in string of product's plu code
//        String entryQuantity = selectQuantity(); //provided by GUI team, passes in string of item's quantity
//
//        PriceLookUpCode pluCode = BarcodeUtils.stringPLUToPLU(pluString);
//
//        handleInputPLU(pluCode, entryQuantity);
//
//        if (pluProduct != null) {
//            // 1.0 is a placeholder, addItem() gets the weight of the product from the electronic scale
//            this.getMainController().addItem(this, pluProduct, 1.0);
//        }
//        else {
//            enableDevice();
//            throw new SimulationException(new NullPointerException("the entered product does not exist"));
//        }
//
//        signalToAddToBaggingArea();  // provided by GUI team
//        enableDevice();
//        resetGUI();  // provided by GUi team
//    }


    /**
     * Getter for Catalog List
     * @return
     * 		The list of PLU codes in the catalog
     */
    public List<PLUCodedProduct> getCatalog(){
        return catalog;
    }

    /**
     * Getter for selected Product
     * @return
     * 		The selected PLU product
     */
    public PLUCodedProduct getPluProduct() {
        return pluProduct;
    }

    /**
     * Getter for selected Item's price
     * @return
     * 		The price of the selected item
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Getter for the new Item's description
     * @return
     * 		The description of the selected item
     */
    public String getNewItemDescription() {
        return newItemDescription;
    }

}