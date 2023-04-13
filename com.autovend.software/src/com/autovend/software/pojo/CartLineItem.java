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
* Sahaj Malhotra (30144405) 
* Ali Elabasery (30148424)
* Fabiha Fairuzz Subha (30148674) 
* Umesh Oad (30152293)
* Daniel Boettcher (30153811) 
* Nam Nguyen Vu (30154892)
* 
*/

package com.autovend.software.pojo;

import java.math.BigDecimal;

public class CartLineItem {
	public enum CODETYPE {
		BARCODE, PLU
	}

	private String productCode;
	private CODETYPE codeType;
	private BigDecimal price;
	private boolean isPerUnit;
	private String description;
	private double expectedWeight;
	private double weight;
	private double quantity;
	private double lineTotalPrice;

	/**
	 * The constructor for CartLineItem
	 * @param productCode
	 * 		The product code
	 * @param codeType
	 * 		The type of the code
	 * @param price
	 * 		The price of the item
	 * @param isPerUnit
	 * 		If the item is per unit or weight
	 * @param description
	 * 		The item description
	 * @param expectedWeight
	 * 		The expected weight of the item
	 * @param weight
	 * 		The weight of the item
	 * @param quantity
	 * 		The quantity of items
	 */
	public CartLineItem(String productCode, CODETYPE codeType, BigDecimal price, boolean isPerUnit, String description,
			double expectedWeight, double weight, double quantity) {
		this.productCode = productCode;
		this.codeType = codeType;
		this.price = price;
		this.isPerUnit = isPerUnit;
		this.description = description;
		this.expectedWeight = expectedWeight;
		this.weight = weight;
		if (this.isPerUnit) {
			this.weight = this.expectedWeight;
		}
		this.quantity = quantity;
	}

	/**
	 * Returns the products code
	 * @return
	 * 		The products code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the products code
	 * @param productCode
	 * 		Product code to be set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * gets the Code Type
	 * @return
	 * 		The enumeration item of Code type
	 */
	public CODETYPE getCodeType() {
		return codeType;
	}

	/**
	 * sets the code type
	 * @param codeType
	 * 		The codetype to be set
	 */
	public void setCodeType(CODETYPE codeType) {
		this.codeType = codeType;
	}

	/**
	 * Gets the price of the item
	 * @return
	 * 		The price of the item
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price of the item
	 * @param price
	 * 		The price to be set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
		recalculate();
	}
	
	/**
	 * If the item is per unit
	 * @return
	 * 		Boolean, True if the item is per unit
	 */
	public boolean isPerUnit() {
		return isPerUnit;
	}

	/**
	 * The description of the item
	 * @return
	 * 		The description of the item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the expected weight of the item
	 * @return
	 * 		The expected weight of the item
	 */
	public double getExpectedWeight() {
		return expectedWeight;
	}

	/**
	 * Returns the quantity of items
	 * @return
	 * 		The quantity of items
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of items
	 * @param quantity
	 * 		Sets the quantity of items
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
		recalculate();
	}

	/**
	 * Gets the line total price
	 * @return
	 * 		The line total price
	 */
	public double getLineTotalPrice() {
		return lineTotalPrice;
	}

	/**
	 * Recalculates the line item
	 */
	private void recalculate() {
		if (this.isPerUnit) {
			this.lineTotalPrice = this.price.doubleValue() * quantity;
		} else {
			this.lineTotalPrice = this.price.doubleValue() * weight;
		}
	}

	/**
	 * Gets the weight of items
	 * @return
	 * 		The weight of items
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight of items in the cart
	 * @param weight
	 * 		The weight of items in the cart
	 */
	private void setWeight(double weight) {
		this.weight = weight;
		recalculate();
	}
}
