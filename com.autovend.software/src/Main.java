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

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.autovend.software.gui.frmApplication;
import com.autovend.software.pojo.ProductDescriptionEntry;
import com.autovend.software.utils.CodeUtils;

import data.DatabaseController;

import com.autovend.PriceLookUpCode;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.BarcodedProduct;
import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.products.*;


public class Main {
	public static void main(String[] args) {
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
        JFrame frame = new JFrame("Self Checkout System");
        frame.setSize(1200, 800);
        frame.setContentPane(new frmApplication().contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.pack();
        frame.setVisible(true);
        
        PriceLookUpCode PLUcode = CodeUtils.stringPLUToPLU("8219");
        Product product = DatabaseController.getProduct(PLUcode, 'P');
        if (product instanceof BarcodedProduct) {
        	BarcodedProduct barcodedProduct = (BarcodedProduct)product;
        	String productDescription = barcodedProduct.getDescription();
        	System.out.println("product found: " + productDescription);
        } else if (product instanceof PLUCodedProduct) {
        	PLUCodedProduct pludProduct = (PLUCodedProduct)product;
        	String productDescription = pludProduct.getDescription();
        	System.out.println("Product found: " + productDescription);
        }
    }
}
