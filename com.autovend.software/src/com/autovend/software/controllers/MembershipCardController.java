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

package com.autovend.software.controllers;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import com.autovend.IllegalDigitException;
import com.autovend.MembershipCard;
import com.autovend.Card.CardData;
import com.autovend.devices.BarcodeScanner;
import com.autovend.devices.CardReader;


public class MembershipCardController {
	private String membershipNumber;
	private boolean isActive = false;
	private BarcodeScanner barcodeScanner = new BarcodeScanner();
	// Did a max tries of 3, having a limit would help with like not having a
	// infinite input that is invalid,
	// and after the three invalid attempts it will return null
	private static final int MAX_TRIES = 3;

	/**
	 * Returns if the controller is active
	 * @return
	 * 		True if the controller is active, false otherwise
	 */
	public boolean getIsActive() {
		return this.isActive;
	}

	/**
	 * The isValid method will first check if memberNUM is null or if its length is
	 * not equal to 12. If any of those conditions are true then it will through the
	 * exception "IllegalDigitException" and say that it needs to be exactly 12
	 * digits long. Then it will use the Character.isDigit to check each digit in
	 * memberNum and see if there are any non-digits and that it is a digit between
	 * 0-9. If there is a non-digit it will throw the "IllegalDigitException" saying
	 * that it should be a digit between 0-9.
	 * 
	 * @param memberNum
	 * 		The member number
	 * @return
	 * 		The boolean if the number is valid
	 */

	public static boolean isValid(String memberNum) throws IllegalDigitException {
		if (memberNum == null || memberNum.length() != 12) {
			throw new IllegalDigitException("The Membership number should be exactly 12 digits long.");
		}

		for (int i = 0; i < memberNum.length(); i++) {

			char c = memberNum.charAt(i);

			if (!Character.isDigit(c)) {
				throw new IllegalDigitException("The Membership number should only contain digits between 0-9.");
			}
		}
		return true;
	}

	/**
	 * The getValidMembershipNumberByTyping method prompts the user to enter a Membership
	 * number (by tyiping) and checks whether the input is valid. If the input is valid (only
	 * digits between 0-9 and is exactly 12 digits long), the method returns the
	 * Membership number. If the input is invalid, the method prompts the user to
	 * try again or continue without a Membership number, up to a maximum number of
	 * tries (MAX_TRIES). If the user exceeds the maximum number of tries without
	 * entering a valid Membership number, the method returns null.
	 * 
	 * @param memberNum
	 * 		The member number
	 * @return
	 * 		The member number if it is valid otherwise, null
	 */

	//Takes string input now

	public static String getValidMembershipNumberByTyping(String memberNum) throws IllegalDigitException {
		int numTries = 0;
		while (numTries < MAX_TRIES) {
			System.out.println("Enter your Membership number: ");
			try {
				if (isValid(memberNum)) {
					return memberNum;
				}
			} catch (IllegalDigitException e) {
				System.out.println(e.getMessage());
			}
			numTries++;
			if (numTries < MAX_TRIES) {
				System.out.println(
						"Invalid membership number. Please try again or enter 'yes' to continue without a Membership number.");
			}
		}
		return null;
	}

	/**
	 * The getValidMembershipNumberByScanning method prompts the user to scan their Membership Card
	 * that enters their number into the system and checks whether the input is valid. If the input is valid (only
	 * digits between 0-9 and is exactly 12 digits long), the method returns the
	 * Membership number. If the input is invalid, the method prompts the user to
	 * try again or continue without a Membership number, up to a maximum number of
	 * tries (MAX_TRIES). If the user exceeds the maximum number of tries without
	 * scanning a valid Membership card, the method returns null.
	 * 
	 * @param scanner
	 * 		The barcode scanner
	 * @param membershipCard
	 * 		The membership card number
	 * 
	 * @return 
	 * 		The membership number if valid, else null
	 */

	public static String getValidMembershipNumberByScanning(BarcodeScanner scanner, MembershipCard membershipCard) {
		int numTries = 0;
		while (numTries < MAX_TRIES) {
			String memberNum = null;
			if (scanner.scan(membershipCard)) {
				memberNum = membershipCard.getBarcode().toString();
			}
			try {
				if (isValid(memberNum)) {
					return memberNum;
				}
			} catch (IllegalDigitException e) {
				System.out.println(e.getMessage());
			}
			numTries++; //continue here
			if (numTries < MAX_TRIES) {
				System.out.println("Membership card scan failed. Please try again or enter 'yes' to continue without a Membership number");
			}
		}

		return null;
	}


	/**
	 * The getValidMembershipNumberBySwiping method prompts the user to enter a Membership
	 * number (by swiping) and checks whether the input is valid. If the input is valid (only
	 * digits between 0-9 and is exactly 12 digits long), the method returns the
	 * Membership number. If the input is invalid, the method prompts the user to
	 * try again or continue without a Membership number, up to a maximum number of
	 * tries (MAX_TRIES). If the user exceeds the maximum number of tries without
	 * entering a valid Membership number, the method returns null.
	 * 
	 * @param mc
	 * 		The membership card
	 * @return
	 * 		The membership number if valid, null otherwise
	 */

	public String getValidMembershipNumberBySwiping(MembershipCard mc) {
		int numTries = 0;
		while (numTries < MAX_TRIES) {
			BufferedImage image = new BufferedImage(1,2,3);
			String number = null;
			CardReader read = new CardReader();

			try {
				CardData cardunit = read.swipe(mc, image);
				number = cardunit.getNumber();
				if (isValid(number)) {
					return number;
				}
			}

			catch (IOException io) {
				System.out.println("Swipe failure");
			}
			catch (IllegalDigitException e) {
				System.out.println(e.getMessage());
			}

			numTries++; 
			if (numTries < MAX_TRIES) {
				System.out.println("Membership card scan failed. Please try again or enter 'yes' to continue without a Membership number");
			}
		}
		return null;

	}

	/**
	 * The updateMembershipStatus method prompts the user to enter whether they have
	 * a membership or not. "yes" would call the getValidMembershipNumber method(s) 
	 * according to the users desired method of input (type,scan, or swipe) to
	 * retrieve and validate the membership number. If it is valid the Membership
	 * Card object is created with the given membership number and isActive would be
	 * set to true. If the membership number is invalid the user will be informed
	 * that they will be enrolled without a membership number. If the user responds
	 * with "no," they are prompted again to confirm whether they want to continue
	 * without a membership number. If the user responds with "no," the same process
	 * as described above for the "yes" response is followed. If the user responds
	 * with any other input, they are informed that their input is invalid and the
	 * method returns. If the user responds with "cancel," the method prints a
	 * message indicating that the membership enrollment has been cancelled and
	 * returns. Finally, the method prints out the membership status and the
	 * information about the membership card. If an IllegalDigitException is caught
	 * during the execution of the method, the user is informed of the error and
	 * prompted to try again. The method is then called recursively to repeat the
	 * process.
	 */

	// Changed String input lines

	@SuppressWarnings("resource")
	public void updateMembershipStatus() {
		String input = "";
		Scanner scan = new Scanner(System.in);
		MembershipCard mc = new MembershipCard("Membership Card", "123456789012", "XYZ", false);
		System.out.println("Do you have a Membership number? (yes or no or cancel)");
		String haveCardResponse = scan.nextLine();

		if (haveCardResponse.equalsIgnoreCase("yes")) {
			System.out.println("Choose how you would like to enter your membership card (type or scan or swipe)");
			String scanMethodResponse = scan.nextLine();

			if (scanMethodResponse.equalsIgnoreCase("type")) {

				input = scan.nextLine();
				membershipNumber = getValidMembershipNumberByTyping(input);

			} else if (scanMethodResponse.equalsIgnoreCase("scan")) {
				membershipNumber = getValidMembershipNumberByScanning(barcodeScanner, mc);
			} else {
				membershipNumber = getValidMembershipNumberBySwiping(mc);
			}

			if (membershipNumber != null) {
				mc = new MembershipCard("Membership Card", membershipNumber, "Regular Shopper", false);
				isActive = true;
			} else {
				System.out.println("You will be enrolled without a membership number.");
			}

		} else if (haveCardResponse.equalsIgnoreCase("no")) {
			System.out.println("Do you want to continue without a Membership number? (yes or no)");
			String response2 = scan.nextLine();

			if (!response2.equalsIgnoreCase("no") && !response2.equalsIgnoreCase("yes")) {
				System.out.println("Invalid input. Please enter 'yes' or 'no'.");
				return;
			} else if (response2.equalsIgnoreCase("no")) {

				input = scan.nextLine();
				membershipNumber = getValidMembershipNumberByTyping(input);

				if (membershipNumber != null) {
					mc = new MembershipCard("Membership Card", membershipNumber, "Regular Shopper", false);
					isActive = true;
				} else {
					System.out.println("You will be enrolled without a membership number.");
				}
			}

		} else if (haveCardResponse.equalsIgnoreCase("cancel")){
			System.out.println("Membership enrollment has been cancelled.");
			return;
		}

		System.out.println("Membership status: " + isActive);
		System.out.println("Membership card information: " + mc.toString());
	}

}
