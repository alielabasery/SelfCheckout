package com.autovend.software.test;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.AttendantLoginLogoutController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AttendantLoginLogoutTest {

    //member variables
    private AttendantLoginLogoutController attendantLoginLogoutController;

    private static final String attendant1Id = "001";
    private static final String attendant1Password = "Attendant1!";
    private static final String attendant2Id = "002";
    private static final String attendant2Password = "SelfCheck2@";
    private static final String attendant3Id = "003";
    private static final String attendant3Password = "Station3$";

    private static final String idNotInDatabase = "004";
    private static final String passwordNotInDatabase = "Access4#";

    ByteArrayOutputStream outContent; //to capture output stream

    @BeforeClass
    public static void setUpClass() {
        //construct database for attendant ids and passwords
        AttendantLoginLogoutController.idAndPasswords.put(attendant1Id, attendant1Password); 
        AttendantLoginLogoutController.idAndPasswords.put(attendant2Id, attendant2Password); 
        AttendantLoginLogoutController.idAndPasswords.put(attendant3Id, attendant3Password); 
    }

    @Before
    public void setUp() {
        attendantLoginLogoutController = new AttendantLoginLogoutController();

        //redirecting the output stream to ByteArrayOutputStream called outContent
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        attendantLoginLogoutController = null;

        //reset output stream
        System.setOut(System.out); 
        outContent = null;
    }

    @Test
    public void testValidLogin() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, attendant1Password);
        assertTrue(attendantLoginLogoutController.getLoggedIn());
    }

    @Test (expected = SimulationException.class)
    public void testInvalidID() {
        attendantLoginLogoutController.AttendantLogin(idNotInDatabase, attendant1Password);
        //check message 
        String expectedOutput = "Incorrect ID!";
        String actualOutput = outContent.toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test (expected = SimulationException.class)
    public void testInvalidPasswordNotInDatabase() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, passwordNotInDatabase);
        //check message
        String expectedOutput = "Incorrect password!";
        String actualOutput = outContent.toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test (expected = SimulationException.class)
    public void testInvalidPasswordInDatabase() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, attendant3Password);
        //check message
        String expectedOutput = "Incorrect password!";
        String actualOutput = outContent.toString();
        assertEquals(expectedOutput, actualOutput);

    }

    @Test (expected = SimulationException.class)
    public void testInvalidIDandPassword() {
        attendantLoginLogoutController.AttendantLogin(idNotInDatabase, passwordNotInDatabase);
        //check message
        String expectedOutput = "Incorrect ID and password!";
        String actualOutput = outContent.toString();
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void testValidLogout() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, attendant1Password);
        attendantLoginLogoutController.AttendantLogout();
        assertFalse(attendantLoginLogoutController.getLoggedIn());
    }

    @Test (expected = SimulationException.class)
    public void testInvalidLogout() {
        //didn't log in 
        attendantLoginLogoutController.AttendantLogout();
        //check message
        String expectedOutput = "Nobody is logged in!";
        String actualOutput = outContent.toString();
        assertEquals(expectedOutput, actualOutput);
    }
}