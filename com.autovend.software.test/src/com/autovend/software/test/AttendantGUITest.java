package com.autovend.software.test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;
import com.autovend.devices.SelfCheckoutStation;
import com.autovend.devices.SupervisionStation;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.attendantgui.AttendantPanel;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.gui.dlgSearchProduct;
import com.autovend.software.utils.CodeUtils;

import data.DatabaseController;

public class AttendantGUITest {
	
	private Currency currency;
	private int[] billDenominations;
	private BigDecimal[] coinDenominations;
	private SelfCheckoutStation station;
	private CheckoutController controller;
	private AttendentController attendantController;
	private SupervisionStation attendantStation;
	
	GuiController gc;
	
	@Before
	public void setup() {
		currency = Currency.getInstance("CAD");
		billDenominations = new int[] { 5, 10, 20, 50, 100 };
		coinDenominations = new BigDecimal[] {new BigDecimal("0.05"), new BigDecimal("0.1"), 
				new BigDecimal("0.25"), new BigDecimal("0.5"), new BigDecimal("1"), new BigDecimal("2")};
    	station = new SelfCheckoutStation(currency, billDenominations, coinDenominations, 1000, 1);
    	controller = new CheckoutController("Station 1", station);
    	
    	attendantStation = new SupervisionStation();
    	attendantController = new AttendentController(attendantStation);
    	
    	addItemsToDatabase();
    	
		gc = new GuiController(attendantController);
	}
	
	@Test
	public void testFailedLogin() {
		gc.attendantLoginScreen();
		gc.attendantLoginScreen.button.doClick();
		assertTrue(gc.attendantLoginScreen.failLabel.isVisible());
	}
	
	@Test
	public void testPassedLogin() {
		gc.attendantLoginScreen();
		gc.attendantLoginScreen.IDField.setText("001");
		gc.attendantLoginScreen.passField.setText("Attendant1!");
		gc.attendantLoginScreen.button.doClick();
	}
	
	@Test
	public void testAttendantButtonClicks() {
		gc.attendantScreen();
		gc.attendantPanelScreen.logoutButton.doClick();
		gc.attendantPanelScreen.addStationButton.doClick();
	}
	
	@Test
	public void testAddRemoveNotification() {
		gc.attendantScreen();
		gc.attendantPanelScreen.addNotification("Testing Notification");
		assertTrue(gc.attendantPanelScreen.notificationsBox.getComponentCount() > 0);
		gc.attendantPanelScreen.removeNotification((JPanel) gc.attendantPanelScreen.notificationsBox.getComponent(0), 
				(JSeparator) gc.attendantPanelScreen.notificationsBox.getComponent(1));
	}
	
	
	@Test
	public void testAddRemoveStation() {
		gc.attendantScreen();
		gc.attendantPanelScreen.addStation("Station 1");
		assertTrue(gc.attendantPanelScreen.stationsBox.getComponentCount() > 0);
		gc.attendantPanelScreen.removeStation((JPanel) gc.attendantPanelScreen.stationsBox.getComponent(0), 
				(JSeparator) gc.attendantPanelScreen.stationsBox.getComponent(1));
	}
	
	@Test
	public void testaddItemToStation() {
		gc.attendantScreen();
		dlgSearchProduct test = new dlgSearchProduct(null, null);
		test.selectedItemCode = "0001";
		test.selectedItemType = 'P';
		AttendantPanel.addItemToStation(test, "Station 1");
		gc.attendantPanelScreen.addStation("Station 1");
		assertTrue(gc.attendantPanelScreen.stationsBox.getComponentCount() > 0);
		gc.attendantPanelScreen.removeStation((JPanel) gc.attendantPanelScreen.stationsBox.getComponent(0), 
				(JSeparator) gc.attendantPanelScreen.stationsBox.getComponent(1));
	}
	
	private void addItemsToDatabase() {
		try {        	
            PriceLookUpCode code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.five, Numeral.six, Numeral.eight});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Gala Apples", new BigDecimal(1.50)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.one, Numeral.one});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Ambrosia Apples", new BigDecimal(1.89)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.eight, Numeral.six});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Mcintosh Apples", new BigDecimal(2.25)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.three, Numeral.seven});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Green Apples", new BigDecimal(0.89)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.one, Numeral.nine});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Granny Smith Apples", new BigDecimal(1.35)));
            code = new PriceLookUpCode(new Numeral[] {Numeral.eight, Numeral.two, Numeral.five, Numeral.four});
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Honey Crisp Apples", new BigDecimal(1.99)));
            Barcode bcode = CodeUtils.stringBarcodeToBarcode("349896");
            DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Ham", new BigDecimal(15.00), 4.00));
            bcode = CodeUtils.stringBarcodeToBarcode("127634");
            DatabaseController.addBarcodedProduct(bcode, new BarcodedProduct(bcode, "Herbal Essance Shampoo", new BigDecimal(8.59), 2.09));
            code = CodeUtils.stringPLUToPLU("0001");
            DatabaseController.addPLUdProduct(code, new PLUCodedProduct(code, "Plastic bag", new BigDecimal(0.05)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	@After
	public void teardown() {
		currency = null;
		billDenominations = null;
		coinDenominations = null;
    	station = null;
    	controller = null;
    	
    	attendantStation = null;
    	attendantController = null;
    	
    	gc.attendantLoginScreen = null;
    	gc.attendantPanelScreen = null;
		gc = null;
	}
}
