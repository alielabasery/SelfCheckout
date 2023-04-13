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

package com.autovend.software.test;

import static org.junit.Assert.assertEquals;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;
import com.autovend.PriceLookUpCodedUnit;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.devices.TouchScreen;
import com.autovend.external.ProductDatabases;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.controllers.AddItemByPLUController;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.gui.AddItemsPanel;
import com.autovend.software.gui.StartScreenPanel;
import com.autovend.software.gui.dlgPLUProduct;

public class AddItemGUITest {
	private CheckoutController checkoutController;
    private List<PLUCodedProduct> testCatalog;
    private GuiController gui;
    private JFrame screen;
    private AddItemsPanel panel;

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
        int[] billDenominations = new int[] {5, 10, 20, 50, 100};
        BigDecimal[] coinDenominations = new BigDecimal[] {new BigDecimal(0.05), new BigDecimal(0.1), new BigDecimal(0.25), new BigDecimal(100), new BigDecimal(200)};
        SelfCheckoutStation station = new SelfCheckoutStation(Currency.getInstance("CAD"), billDenominations, coinDenominations,200, 1);

        checkoutController = new CheckoutController("1", station);
        SupervisionStation attendantStation = new SupervisionStation();
        AttendentController attendant = new AttendentController(attendantStation);
        
        testCatalog = new ArrayList<PLUCodedProduct>();

        unit1 = new PriceLookUpCodedUnit(PLUCODE_1, 1f);
        unit2 = new PriceLookUpCodedUnit(PLUCODE_2, 2f);

        product1 = new PLUCodedProduct(PLUCODE_1, "Item 1", new BigDecimal("4.0"));
        product2 = new PLUCodedProduct(PLUCODE_2, "Item 2", new BigDecimal("2.5"));

        ProductDatabases.PLU_PRODUCT_DATABASE.put(PLUCODE_1, product1);

        testCatalog.add(product1);
        
        gui = new GuiController(attendant);
        gui.addItemsScreen(checkoutController);
        screen = checkoutController.getSelfCheckoutStation().screen.getFrame();
        panel = (AddItemsPanel) ((Container) screen.getContentPane().getComponent(0)).getComponent(0);
    }
    
    @Test
    public void addItemPLULookup() {
        dlgPLUProduct dialog = new dlgPLUProduct(screen, "Add item by PLU", checkoutController);
    	panel.plubutton.doClick();
    	dialog.txKeyword.setText(PLUCODE_1.toString());
    	dialog.btnFind.doClick();
    	dialog.table1.addRowSelectionInterval(0, 0);
    	dialog.okButton.doClick();
    	assertEquals(checkoutController.getCart().getCartLineItems().get(0).getProductCode(),
    			     PLUCODE_1.toString());
    }
    
    @Test
    public void addItemBrowsing() {
    	
    }
    
    @After
    public void teardown() {
    	screen.dispose();
    }
}
