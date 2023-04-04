// Placeholder for Group 6: Names + UCID

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
	private double quantity;
	private double lineTotalPrice;

	public CartLineItem(String productCode, CODETYPE codeType, BigDecimal price, boolean isPerUnit, String description,
			double expectedWeight, double quantity) {
		this.productCode = productCode;
		this.codeType = codeType;
		this.price = price;
		this.isPerUnit = isPerUnit;
		this.description = description;
		this.expectedWeight = expectedWeight;
		this.quantity = quantity;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public CODETYPE getCodeType() {
		return codeType;
	}

	public void setCodeType(CODETYPE codeType) {
		this.codeType = codeType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
		recalculate();
	}

	public boolean isPerUnit() {
		return isPerUnit;
	}

	public String getDescription() {
		return description;
	}

	public double getExpectedWeight() {
		return expectedWeight;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
		recalculate();
	}

	public double getLineTotalPrice() {
		return lineTotalPrice;
	}

	private void recalculate() {
		this.lineTotalPrice = this.price.doubleValue() * quantity;
	}
}
