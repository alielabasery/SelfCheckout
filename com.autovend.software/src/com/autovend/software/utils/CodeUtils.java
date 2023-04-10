// Placeholder for Group 6: Names + UCID

package com.autovend.software.utils;

import java.util.ArrayList;

import com.autovend.Barcode;
import com.autovend.Numeral;
import com.autovend.PriceLookUpCode;

public class CodeUtils {
	 private static Numeral[] stringToNumeralArray(String input) {
	        char[] chars = input.toCharArray();
	        ArrayList<Numeral> numerals = new ArrayList<Numeral>();
	        for (char c: chars) {
	            numerals.add(Numeral.valueOf((byte)Integer.parseInt(String.valueOf(c))));
	        }
	        return numerals.toArray(new Numeral[0]);
	    }

	    public static Barcode stringBarcodeToBarcode(String input) {
	        Numeral[] numerals = stringToNumeralArray(input);
	        return new Barcode(numerals);
	    }

	    public static PriceLookUpCode stringPLUToPLU(String input) {
	        Numeral[] numerals = stringToNumeralArray(input);
	        return new PriceLookUpCode(numerals);
	    }
}
