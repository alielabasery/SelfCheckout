// Placeholder for Group 6: Names + UCID

package com.autovend.software.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddItemTest.class, AddOwnBagsTest.class, LowInkPaperTest.class, PurchaseBagsTest.class,
		TestPrintReceipt.class, CardPaymentTest.class, TestMembershipCardController.class, 
		BillPaymentControllerTest.class, BillDispenserControllerTest.class, AddItemByBrowsingTest.class, AddItemByPLUTest.class,
		BaggingAreaTest.class, AdjustBanknotesTest.class, AttendantLoginLogoutTest.class, RemoveItemTest.class, SelectLanguageControllerTest.class,
		shutdownStationTesting.class, TestMembershipCardController.class})
public class AllTests {
}
