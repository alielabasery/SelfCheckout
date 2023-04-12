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
package com.autovend.software.pojo;

import java.util.ArrayList;

import javax.print.attribute.standard.JobMessageFromOperator;

public class Cart {
	private ArrayList<CartLineItem> cartLineItems;
	private double subtotal;
	private double taxRate1;
	private String taxName1;
	private double taxAmount1;
	private double taxRate2;
	private String taxName2;
	private double taxAmount2;
	private double total;
	private boolean compoundTaxes;

	/**
	 * The first tax name
	 * @return
	 * 		The name of the first tax
	 */
	public String getTaxName1() {
		return taxName1;
	}

	/**
	 * The amount of the first tax
	 * @return
	 * 		The amount of the first tax
	 */
	public double getTaxAmount1() {
		return taxAmount1;
	}

	/**
	 * The second tax name
	 * @return
	 * 		The name of the second tax
	 */
	public String getTaxName2() {
		return taxName2;
	}

	/**
	 * The amount of the second tax
	 * @return
	 * 		The amount of the second tax
	 */
	public double getTaxAmount2() {
		return taxAmount2;
	}

	/**
	 * if the taxes are compound
	 * @return
	 * 		True if compound taxes, false otherwise
	 */
	public boolean isCompoundTaxes() {
		return compoundTaxes;
	}

	/**
	 * Returns the subtotal of the cart
	 * @return
	 * 		The subtotal of the cart
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * Returns the first tax rate
	 * @return
	 * 		The first tax rate
	 */
	public double getTaxRate1() {
		return taxRate1;
	}

	/**
	 * Returns the second tax rate
	 * @return
	 * 		The second tax rate
	 */
	public double getTaxRate2() {
		return taxRate2;
	}

	/**
	 * Returns the total of the cart
	 * @return
	 * 		The total of the cart
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Returns the cart items
	 * @return
	 * 		The array list of cart items
	 */
	public ArrayList<CartLineItem> getCartLineItems() {
		return cartLineItems;
	}

	/**
	 * The constructor for the cart
	 * @param taxName1
	 * 		The first tax's name
	 * @param taxRate1
	 * 		The first tax's rate
	 * @param taxName2
	 * 		The second tax's name
	 * @param taxRate2
	 * 		The second tax's rate
	 * @param compoundTaxes
	 * 		True if the taxes are compound, false otherwise
	 */
	public Cart(String taxName1, double taxRate1, String taxName2, double taxRate2, boolean compoundTaxes) {
		cartLineItems = new ArrayList<>();
		this.taxName1 = taxName1;
		this.taxRate1 = taxRate1;
		this.taxName2 = taxName2;
		this.taxRate2 = taxRate2;
		this.compoundTaxes = compoundTaxes;
		this.subtotal = 0.0;
		this.total = 0.0;
		if (this.taxName1 != null && this.taxName1.trim().length() > 0 && this.taxRate1 <= 0) {
			throw new IllegalArgumentException("Invalid tax rate provided.");
		}
		if (this.taxName2 != null && this.taxName2.trim().length() > 0 && this.taxRate2 <= 0) {
			throw new IllegalArgumentException("Invalid tax rate provided.");
		}
		if (this.taxRate1 == 0 && this.taxRate2 > 0) {
			throw new IllegalArgumentException("Tax Rate2 cannot be provided when Tax Rate1 is 0");
		}
	}

	/**
	 * Adds an item to the cart
	 * @param lineItem
	 * 		The item to add to the cart
	 * @return
	 * 		True if successful
	 */
	public boolean addCartItem(CartLineItem lineItem) {
		this.cartLineItems.add(lineItem);
		recalculate();
		return true;
	}

	/**
	 * Removes item from the cart
	 * @param lineIndex
	 * 		The index of the item in the cart
	 * @return
	 * 		True if successful
	 */
	public boolean removeLineItem(int lineIndex) {
		this.cartLineItems.remove(lineIndex);
		recalculate();
		return true;
	}

	/**
	 * Recalculates the total of the cart
	 */
	private void recalculate() {
		double linesSubtotal = 0.0;
		double cartTotal = 0.0;
		for (CartLineItem lineItem: this.cartLineItems) {
			linesSubtotal += lineItem.getLineTotalPrice();
		}
		cartTotal = linesSubtotal;
		if (taxRate1 > 0) {
			this.taxAmount1 = cartTotal * this.taxRate1;
		}
		if (taxRate2 > 0) {
			if (compoundTaxes) {
				cartTotal += this.taxAmount1;
			}
			this.taxAmount2 = cartTotal * this.taxRate2;
		}
		cartTotal = linesSubtotal + this.taxAmount1 + this.taxAmount2;
		this.subtotal = linesSubtotal;
		this.total = cartTotal;
	}
	
	/**
	 * Returns the weight of the cart
	 * @return
	 * 		The cart's weight
	 */
	public double CartTotalWeight() {
		double totalWeight = 0.0;
		for (CartLineItem lineItem: this.cartLineItems) {
			totalWeight += lineItem.getWeight();
		}
		return totalWeight;
	}
}
