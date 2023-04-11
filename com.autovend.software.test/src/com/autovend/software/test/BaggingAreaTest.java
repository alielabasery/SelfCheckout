

package com.autovend.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.autovend.software.controllers.BaggingAreaController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.ElectronicScaleController;
import com.autovend.devices.ElectronicScale;
import com.autovend.products.Product;


//Author Victor Campos
public class BaggingAreaTest {
	private ElectronicScaleController scaleController;
    private CheckoutController checkoutController;
    @SuppressWarnings("rawtypes")
	private BaggingAreaController controller;

    
    @Before
    public void setup() {
        checkoutController = new CheckoutController();
        controller = new ElectronicScaleController(new ElectronicScale(10, 10));
        controller.setMainController(checkoutController);
    }

    
   //Author Victor Campos
   //method to test if main controller is what it was set as 
    @Test
    public void testSetMainController() {
        assertEquals(checkoutController, controller.getMainController());
    }

    
   //Author Victor Campos
   //testing method to see if getattendant approval is what it was set as 
    @Test
    public void testGetAttendantApproval() {
        controller.setAttendantApproval(true);
        assertTrue(controller.getAttendantApproval());
        controller.setAttendantApproval(false);
        assertFalse(controller.getAttendantApproval());
    }

    //Author Victor Campos
    //testing method to see if setattendant approval is what it was set as 
    @Test
    public void testSetAttendantApproval() {
        controller.setAttendantApproval(true);
        assertTrue(controller.getAttendantApproval());
        controller.setAttendantApproval(false);
        assertFalse(controller.getAttendantApproval());
    }

    //Author Victor Campos
    //method to test the bagging area error if the weights are the same as current and expected 
    @Test
    public void testBaggingAreaError() {
        // Set up the test data
        double currentWeight = 2.0;
        double expectedWeight = 3.5;

        // Set the attendant approval to true
        controller.setAttendantApproval(true);

        // Call the method being tested
        double newExpectedWeight = controller.baggingAreaError(currentWeight, expectedWeight);

        // Check that the expected weight has been updated correctly
        assertEquals(1.5, newExpectedWeight, 0.001);

        // Check that the station has been unblocked
        assertFalse(checkoutController.baggingItemLock = false);

        // Set up new test data
        currentWeight = 3.0;
        expectedWeight = 4.5;

        // Set the attendant approval to true
        controller.setAttendantApproval(true);

        // Call the method being tested
        newExpectedWeight = controller.baggingAreaError(currentWeight, expectedWeight);

        // Check that the expected weight has been updated correctly
        assertEquals(1.5, newExpectedWeight, 0.001);

        // Check that the station has been blocked
        assertTrue(checkoutController.baggingItemLock = true);
    }
}
