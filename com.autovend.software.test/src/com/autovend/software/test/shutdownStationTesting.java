package com.autovend.software.test;

import org.junit.Before;
import org.junit.BeforeClass;

import com.autovend.devices.SupervisionStation;
import com.autovend.software.controllers.AttendantLoginLogoutController;
import com.autovend.software.controllers.AttendantShutdownStationController;

public class shutdownStationTesting {
	
	private AttendantShutdownStationController attendantShutdownStationController;
	private AttendantLoginLogoutController attendantLoginLogoutController;
	private SupervisionStation supervisionStation;
	
	private final static String attendantID = "001";
    private final static String attendantPassword = "Password123@";


	@BeforeClass
    public static void onlyOnce() {
        AttendantLoginLogoutController.idAndPasswords.put(attendantID, attendantPassword); 
    }

    @Before
    public void setup() {
        attendantLoginLogoutController = new AttendantLoginLogoutController();
        supervisionStation = new SupervisionStation();
        attendantShutdownStationController = new AttendantShutdownStationController(
        		supervisionStation, attendantLoginLogoutController);
    }
}
