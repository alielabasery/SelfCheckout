package com.autovend.software.test;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.AttendantLoginLogoutController;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    }

    @After
    public void tearDown() {
        attendantLoginLogoutController = null;
    }

    @Test
    public void testValidLogin() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, attendant1Password);
        assertTrue(attendantLoginLogoutController.getLoggedIn());
    }

    @Test (expected = SimulationException.class)
    public void testInvalidID() {
        attendantLoginLogoutController.AttendantLogin(idNotInDatabase, attendant1Password);
    }

    @Test (expected = SimulationException.class)
    public void testInvalidPasswordNotInDatabase() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, passwordNotInDatabase);
    }

    @Test (expected = SimulationException.class)
    public void testInvalidPasswordInDatabase() {
        attendantLoginLogoutController.AttendantLogin(attendant1Id, attendant3Password);
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
    }
}