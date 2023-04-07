// Placeholder for Group 6: Names + UCID

package com.autovend.software.test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.MembershipCard;
import com.autovend.devices.BarcodeScanner;
import com.autovend.IllegalDigitException;
import com.autovend.MembershipCard;
import com.autovend.software.controllers.MembershipCardController;

@SuppressWarnings("unused")

public class TestMembershipCardController {

	private BarcodeScanner barcodeScanner;
	private MembershipCardController mcc;
	private Scanner scanner;
	private InputStreamReader inputReader;
	private MembershipCard validMembershipCard;
	private MembershipCard invalidMembershipCard;
	MembershipCard mc;
    
	@Before
	public void setup() {
		mcc = new MembershipCardController();
		scanner = new Scanner(System.in);
		barcodeScanner = new BarcodeScanner();
		validMembershipCard = new MembershipCard("Membership Card", "564823890124", "John Doe", true);
		invalidMembershipCard = new MembershipCard("Membership Card", "435", "John Doe", true);
	}

	@After
	public void teardown() {
	}

	@Test
	public void testIsValidNullValue() throws IllegalDigitException {
		String expectedMessage = "The Membership number should be exactly 12 digits long.";
		Exception exception = assertThrows(IllegalDigitException.class, () -> MembershipCardController.isValid(null));
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testIsValidLessDigits() throws IllegalDigitException {
		String expectedMessage = "The Membership number should be exactly 12 digits long.";
		Exception exception = assertThrows(IllegalDigitException.class,
				() -> MembershipCardController.isValid("123456"));
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testIsValidNan() throws IllegalDigitException {
		String expectedMessage = "The Membership number should only contain digits between 0-9.";
		Exception exception = assertThrows(IllegalDigitException.class,
				() -> MembershipCardController.isValid("abc234567890"));
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testIsValidReturnsTrue() {
		boolean expected = true;
		boolean actual = MembershipCardController.isValid("564823890124");
		assertEquals(expected, actual);
	}

	@Test
	public void testGetValidMembershipNumberByScanning_ValidCard() {
		String scannedMembershipNumber = MembershipCardController.getValidMembershipNumberByScanning(barcodeScanner,
				validMembershipCard);
		assertEquals(validMembershipCard.getBarcode().toString(), scannedMembershipNumber);
	}

	@Test
	public void testGetValidMembershipNumberByScanning_InvalidCard() {
		String scannedMembershipNumber = MembershipCardController.getValidMembershipNumberByScanning(barcodeScanner,
				invalidMembershipCard);
		assertNull(scannedMembershipNumber);
	}

	@Test
	public void testGetValidMembershipNumberByScanning_FailedScan() {
		String scannedMembershipNumber = null;
		for (int i = 0; i < 10; i++) {
			scannedMembershipNumber = MembershipCardController.getValidMembershipNumberByScanning(barcodeScanner,
					invalidMembershipCard);
		}

		assertNull(scannedMembershipNumber);
	}

	@Test
	public void testGetValidMembershipNumberValidInput() {
		String input = "564823890124";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in); // create a new Scanner object
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner); // pass the Scanner
																									// object
		// as an argument
		assertEquals(input, expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumber_ValidOnFirstTry() {
		String input = "564823890124\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in); // create a new Scanner object
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner); // pass the Scanner
																									// object
		// as an argument
		assertEquals(input.trim(), expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumber_ValidOnSecondTry() {
		String input = "123456\n564823890124\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in); // create a new Scanner object
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner); // pass the Scanner
																									// object
		// as an argument
		assertEquals("564823890124", expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumber_InvalidAndCancel() {
		String input = "123456\n789012\n345678\ncancel\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in); // create a new Scanner object
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner); // pass the Scanner
																									// object
		// as an argument
		assertNull(expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumber_InvalidAndValidInput() {
		String input = "123456\n564823890124\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in);
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner);
		assertEquals("564823890124", expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumber_ValidInput() throws IllegalDigitException {
		String input = "564823890124\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in); // create a new Scanner object
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner); // pass the Scanner
																									// object
		// as an argument
		assertTrue(MembershipCardController.isValid(expectedOutput)); // check if the membership number is valid
	}

	@Test
	public void testGetValidMembershipNumber_ValidInputButNotValidated() {
		String input = "123456789012";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in);
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner);
		assertEquals(input, expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumber_MaxTriesReached() {
		String input = "123456\n789012\n345678\n901234\n567890\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Scanner scanner = new Scanner(System.in);
		String expectedOutput = MembershipCardController.getValidMembershipNumberByTyping(scanner);
		assertNull(expectedOutput);
	}

	@Test
	public void testGetValidMembershipNumberReturnsValidNumber() throws IllegalDigitException {
		String expected = "123456789012";
		InputStream in = new ByteArrayInputStream(expected.getBytes());
		Scanner scanner = new Scanner(in);
		String actual = MembershipCardController.getValidMembershipNumberByTyping(scanner);
		assertEquals(expected, actual);
		assertTrue(MembershipCardController.isValid(actual));
	}

	// end of tests for get valid number

	@Test
	public void testUpdateMembershipStatus_WithValidMember() {
		String input = "yes\ntype\n564823890124\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertTrue(mcc.getIsActive());
	}

	@Test
	public void testUpdateMembershipStatus_WithoutdMember() {
		String input = "no\nyes\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertFalse(mcc.getIsActive());
	}

	/*
	 * Tests the class UpdateMembership when the customer says 'yes' to enroll
	 * without membership, and says 'no' when asked if they want to continue without
	 * a membership no and then finally provides a valid membership no.
	 */
	@Test
	public void testUpdateMembershipStatus_AfterTwoInputs() {
		String input = "no\nno\n564823890124\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertTrue(mcc.getIsActive());
	}

	@Test
	public void testUpdateMembershipStatus_AfterYesMaxOutTries() {
		String input = "yes\ntype\n1234\n564823890\n56294\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertFalse(mcc.getIsActive());
	}

	@Test
	public void testUpdateMembershipStatus_AfterNoMaxOutTries() {
		String input = "no\nno\n1234\n564823890\n56294\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertFalse(mcc.getIsActive());
	}

	@Test
	public void testUpdateMembershipStatus_Cancel() {
		String input = "cancel\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertFalse(mcc.getIsActive());
	}

	@Test
	public void testUpdateMembershipStatus_InvalidInput() {
		String input = "no\ninvalid\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertFalse(mcc.getIsActive());
	}

	@Test
	public void testUpdateMembershipStatus_IllegalDigitException() {
		String input = "1234567a89\nno\n";
		InputStream sysInBackup = System.in;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		mcc.updateMembershipStatus();
		assertFalse(mcc.getIsActive());
	}
	//Added Swipe Tests
	@Test
	public void testSwipeValidMembershipNumber() {
		String membershipnumber = "564823890124";
		mc = new MembershipCard("Membership Card", "564823890124", "XYZ", false);
		String expectedOutput = mcc.getValidMembershipNumberBySwiping(mc); // pass the Scanner object
																							// as an argument
		assertEquals(membershipnumber, expectedOutput);
	}
	
	@Test
	public void testSwipeInValidMembershipNumber() {
		mc = new MembershipCard("Membership Card", "123", "XYZ", false);
		String expectedOutput = mcc.getValidMembershipNumberBySwiping(mc);
		
	
  	assertEquals(null, expectedOutput);
	}


}
