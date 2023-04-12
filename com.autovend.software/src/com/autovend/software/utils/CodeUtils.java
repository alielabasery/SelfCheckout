// Placeholder for Group 6: Names + UCID

package com.autovend.software.utils;

import java.util.ArrayList;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;

public class CodeUtils {

	/**
	 * Turns a string to a numeral array
	 * @param input
	 * 		Input to be turned into an array
	 * @return
	 * 		The array of numerals representing the inputted string
	 */
	private static Numeral[] stringToNumeralArray(String input) {
		char[] chars = input.toCharArray();
		ArrayList<Numeral> numerals = new ArrayList<Numeral>();
		for (char c: chars) {
			numerals.add(Numeral.valueOf((byte)Integer.parseInt(String.valueOf(c))));
		}
		return numerals.toArray(new Numeral[0]);
	}

	/**
	 * Creates a barcode from a string input
	 * @param input
	 * 		String to be converted to barcode
	 * @return
	 * 		The produced barcode
	 */
	public static Barcode stringBarcodeToBarcode(String input) {
		Numeral[] numerals = stringToNumeralArray(input);
		return new Barcode(numerals);
	}

	/**
	 * Creates a PLU code from a string input
	 * @param input
	 * 		String to be converted to PLU code
	 * @return
	 * 		The produced PLU code
	 */
	public static PriceLookUpCode stringPLUToPLU(String input) {
		Numeral[] numerals = stringToNumeralArray(input);
		return new PriceLookUpCode(numerals);
	}
}
