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

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.autovend.devices.EmptyException;
import com.autovend.devices.OverloadException;
import com.autovend.devices.ReceiptPrinter;
import com.autovend.devices.observers.ReceiptPrinterObserver;
import com.autovend.products.Product;

public class ReceiptPrinterController extends DeviceController<ReceiptPrinter, ReceiptPrinterObserver>
		implements ReceiptPrinterObserver {
	private CheckoutController mainController;
	private ReceiptPrinter printer;

	// Flags/indicators that ink or paper levels are low
	public boolean inkLow = true;
	public boolean paperLow = true;

	public int estimatedInk = 0;
	public int estimatedPaper = 0;

	// How do we update the estimated ink and paper on refills? - Arie
	/**
	 * Constructor for the ReceiptPrinterController
	 * @param newDevice
	 * 		The Recipt Printer device
	 */
	public ReceiptPrinterController(ReceiptPrinter newDevice) {
		super(newDevice);
	}

	/**
	 * Gets the main controller
	 * @return
	 * 		The CheckoutController main controller
	 */
	public final CheckoutController getMainController() {
		return this.mainController;
	}

	/**
	 * Sets the main controller
	 * @param newMainController
	 * 		The CheckoutController to set as the main controller
	 */
	public final void setMainController(CheckoutController newMainController) {
		if (this.mainController != null) {
			this.mainController.deregisterReceiptPrinter(this);
		}
		this.mainController = newMainController;
		if (this.mainController != null) {
			this.mainController.registerReceiptPrinter(this);
		}
	}

	/**
	 * Function for software to keep track of how much ink printer has Since there
	 * are no sensors, whenever ink is added to printer, its incremented in the
	 * software using this function
	 * 
	 * @param inkAmount: amount of ink added to printer
	 */
	public void addedInk(int inkAmount) {
		if (inkAmount > 0)
			estimatedInk += inkAmount;
		else
			System.out.println("Negative Ink Not Allowed to be Added");

		if (inkAmount > 500)
			inkLow = false;
		else
			inkLow = true;
	}

	/**
	 * Function for software to keep track of how much paper printer has Since there
	 * are no sensors, whenever paper is added to printer, its incremented in the
	 * software using this function
	 * 
	 * @param paperAmount: amount of paper added to printer
	 */
	public void addedPaper(int paperAmount) {
		if (paperAmount > 0)
			estimatedPaper += paperAmount;
		else
			System.out.println("Negative Paper Not Allowed to be Added");

		if (paperAmount > 200)
			paperLow = false;
		else
			paperLow = true;
	}

	/**
	 * Responsible for printing out a properly formatted Receipt using the list of
	 * Products and total cost. The receipt will contain a numbered list containing
	 * the price of each product.
	 * 
	 * @param order: HashMap of Products on the order
	 * @param cost:  total cost of the order
	 */
	public void printReceipt(LinkedHashMap<Product, Number[]> order, BigDecimal cost) {

		printer = getDevice();

		// initialize String Builder to build the receipt
		StringBuilder receipt = new StringBuilder();
		receipt.append("Purchase Details:\n");

		// loop through every product in the order, appending the appropriate strings to
		// the receipt
		int i = 1;

		for (Product product : order.keySet()) {
			Number[] productInfo = order.get(product);

			// We only need to focus on per-unit costs currently, weight-based will be
			// handled later.
			// if (product.isPerUnit()){
			// going through the string and splitting to avoid writing too much
			// on one line
			String productName = product.getClass().getSimpleName();
			String productString = String.format("%d $%.2f %dx %s\n", i, productInfo[1], productInfo[0].intValue(),
					productName);
			int splitPos = 59;
			String splitterSubString = "-\n    -";
			while (splitPos < productString.length() - 1) {// -1 to not worry about the \n at the end.
				productString = productString.substring(0, splitPos) + splitterSubString
						+ productString.substring(splitPos);
				splitPos += 61;// 1 extra to account for \n being 1 character (prevents double-spacing of text)
			}
			// }
			receipt.append(productString);
			i++;
		}
		// append total cost at the end of the receipt
		receipt.append(String.format("Total: $%.2f\n", cost));

		try {
			for (char c : receipt.toString().toCharArray()) {
				if (c == '\n') {
					estimatedPaper--;
				} else if (!Character.isWhitespace(c)) {
					estimatedInk--;
				}

				printer.print(c);
			}
			printer.cutPaper();
		} catch (OverloadException e) {
			System.out.println("The receipt is too long.");
		} catch (EmptyException e) {
			System.out.println("The printer is out of paper or ink.");
			this.mainController.printerOutOfResources(this);
		}

		if (estimatedInk <= 500) {
			// Inform the I/O for attendant from the error message about low ink
			// this is a placeholder currently.
			System.out.println("Ink Low for Station: " + mainController.getID());
			inkLow = true;
		} else
			inkLow = false;
		if (estimatedPaper <= 200) {
			// Inform the I/O for attendant from the error message about low ink
			// this is a placeholder currently.
			System.out.println("Paper Low for Station: " + mainController.getID());
			paperLow = true;
		} else
			paperLow = false;
	}

	@Override
	public void reactToOutOfPaperEvent(ReceiptPrinter printer) {
		estimatedPaper = 0;
		this.mainController.printerOutOfResources(this);
	}

	@Override
	public void reactToOutOfInkEvent(ReceiptPrinter printer) {
		estimatedInk = 0;
		this.mainController.printerOutOfResources(this);
	}

	@Override
	public void reactToPaperAddedEvent(ReceiptPrinter printer) {
		this.mainController.printerRefilled(this);
	}

	@Override
	public void reactToInkAddedEvent(ReceiptPrinter printer) {
		this.mainController.printerRefilled(this);
	}

}
