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
