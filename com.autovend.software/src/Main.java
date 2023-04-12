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
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;

import com.autovend.software.gui.frmApplication;
import com.autovend.software.pojo.CartLineItem;
import com.autovend.software.pojo.CartLineItem.CODETYPE;
import com.autovend.software.pojo.ProductDescriptionEntry;
import com.autovend.software.utils.CodeUtils;

import Configuration.GlobalConfigurations;
import Networking.NetworkController;
import data.DatabaseController;

import com.autovend.devices.SelfCheckoutStation;
import com.autovend.PriceLookUpCode;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.BarcodedProduct;
import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.products.*;


public class Main {
	   public static void main(String[] args) {
	        //** Initialize database
	        initializeDatabase();

	        System.out.println("Main: Creating AttendantController");
	        new AttendentController();

	        //** initialize Checkout Stations
	        for (String workstationId : GlobalConfigurations.WORKSTATIONIDS) {
	            System.out.println("Main: Creating CheckoutController " + workstationId);
	            new CheckoutController(workstationId, new SelfCheckoutStation(Currency.getInstance(Locale.CANADA), GlobalConfigurations.BILLDENOMINATIONS, GlobalConfigurations.COINDENOMINATIONS, 1000, 1));
	            System.out.println("Main: Created CheckoutController " + workstationId);
	      }


	        //** test that cart management for each station is working
	        PLUCodedProduct pluProduct;
	        BarcodedProduct barProduct;
	        
	        //** Station 1
	        pluProduct = (PLUCodedProduct) DatabaseController.getProduct(CodeUtils.stringPLUToPLU("8211"), 'P');
	        NetworkController.getAttendentController().addItemToStationCart("1", new CartLineItem(pluProduct.getPLUCode().toString(), CODETYPE.PLU, pluProduct.getPrice(), pluProduct.isPerUnit(), pluProduct.getDescription(), 0.0, 1.5, 0));
	        barProduct = (BarcodedProduct) DatabaseController.getProduct(CodeUtils.stringBarcodeToBarcode("349896"), 'B');
	        NetworkController.getAttendentController().addItemToStationCart("1", new CartLineItem(barProduct.getBarcode().toString(), CODETYPE.BARCODE, barProduct.getPrice(), barProduct.isPerUnit(), barProduct.getDescription(), barProduct.getExpectedWeight(), 1.50, 2));

	        //** Station 3
	        pluProduct = (PLUCodedProduct) DatabaseController.getProduct(CodeUtils.stringPLUToPLU("8219"), 'P');
	        NetworkController.getAttendentController().addItemToStationCart("3", new CartLineItem(pluProduct.getPLUCode().toString(), CODETYPE.PLU, pluProduct.getPrice(), pluProduct.isPerUnit(), pluProduct.getDescription(), 0.0, 3.25, 0));
	        barProduct = (BarcodedProduct) DatabaseController.getProduct(CodeUtils.stringBarcodeToBarcode("127634"), 'B');
	        NetworkController.getAttendentController().addItemToStationCart("3", new CartLineItem(barProduct.getBarcode().toString(), CODETYPE.BARCODE, barProduct.getPrice(), barProduct.isPerUnit(), barProduct.getDescription(), barProduct.getExpectedWeight(), 2, 1));

	        //** Station 6
	        pluProduct = (PLUCodedProduct) DatabaseController.getProduct(CodeUtils.stringPLUToPLU("8219"), 'P');
	        NetworkController.getAttendentController().addItemToStationCart("6", new CartLineItem(pluProduct.getPLUCode().toString(), CODETYPE.PLU, pluProduct.getPrice(), pluProduct.isPerUnit(), pluProduct.getDescription(), 0.0, 3.25, 0));
	        barProduct = (BarcodedProduct) DatabaseController.getProduct(CodeUtils.stringBarcodeToBarcode("127634"), 'B');
	        NetworkController.getAttendentController().addItemToStationCart("6", new CartLineItem(barProduct.getBarcode().toString(), CODETYPE.BARCODE, barProduct.getPrice(), barProduct.isPerUnit(), barProduct.getDescription(), barProduct.getExpectedWeight(), 2, 1));

	        //** Let's see if the cart totals at each checkout station matches correctly
	        System.out.println(String.format("Total for station %d = %f", 1,  NetworkController.getCheckoutStationController("1").getCost()));
	        System.out.println(String.format("Total for station %d = %f", 3, NetworkController.getCheckoutStationController("3").getCost()));
	        System.out.println(String.format("Total for station %d = %f", 6, NetworkController.getCheckoutStationController("6").getCost()));
//	        System.out.println(String.format("Total for station %d Cart = %f", 1,  NetworkController.getCheckoutStationController("1").getCart().getSubtotal()));
//	        System.out.println(String.format("Total for station %d Cart = %f", 1,  NetworkController.getCheckoutStationController("3").getCart().getSubtotal()));
//	        System.out.println(String.format("Total for station %d Cart = %f", 1,  NetworkController.getCheckoutStationController("6").getCart().getSubtotal()));

	        //** show main UI **
	        JFrame frame = new JFrame("Self Checkout System");
	        frame.setSize(1200, 800);
	        frame.setContentPane(new frmApplication().contentPane);
	        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        frame.setSize(400, 300);
	        frame.pack();
	        frame.setVisible(true);
	}
	   
	   private static void initializeDatabase() {
	        try {
	           addPLUProduct("0001", "Plastic Bag", new BigDecimal(0.05));
	           addPLUProduct("8568", "Gala Apples", new BigDecimal(1.50));
	           addPLUProduct("8211", "Ambrosia Apples", new BigDecimal(1.89));
	           addPLUProduct("8286", "Mcintosh Apples", new BigDecimal(2.25));
	           addPLUProduct("8237", "Green Apples", new BigDecimal(0.89));
	           addPLUProduct("8219", "Granny Smith Apples", new BigDecimal(1.35));
	           addPLUProduct("8254", "Honey Crisp Apples", new BigDecimal(1.99));
//	         addPLUProduct("0001", "AAA", new BigDecimal(9999));
	           addBarcodedProduct("349896", "Coke Classic - 2L", new BigDecimal(1.99), 1.50);
	           addBarcodedProduct("127634", "Herbal Essance Shampoo", new BigDecimal(8.59), 2.0);
//	         addBarcodedProduct("0001", "AAA", new BigDecimal(9999, 9999);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	   }
	   private static void addPLUProduct(String code, String description, BigDecimal price) {
	        DatabaseController.addPLUdProduct(CodeUtils.stringPLUToPLU(code), new PLUCodedProduct(CodeUtils.stringPLUToPLU(code), description, price));       
	   }
	   private static void addBarcodedProduct(String code, String description, BigDecimal price, double expectedWeight) {
	        DatabaseController.addBarcodedProduct(CodeUtils.stringBarcodeToBarcode(code), new BarcodedProduct(CodeUtils.stringBarcodeToBarcode(code), description, price, expectedWeight));       
	   }
	}