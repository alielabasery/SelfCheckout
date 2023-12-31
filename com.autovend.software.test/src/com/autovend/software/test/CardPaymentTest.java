// Placeholder for Group 6: Names + UCID

package com.autovend.software.test;

import com.autovend.CreditCard;
import com.autovend.GiftCard;
import com.autovend.GiftCard.GiftCardInsertData;
import com.autovend.InvalidPINException;
import com.autovend.devices.CardReader;
import com.autovend.devices.SimulationException;
import com.autovend.external.CardIssuer;
import com.autovend.software.controllers.CardReaderController;
import com.autovend.software.controllers.CheckoutController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class CardPaymentTest {
    TestBank bankStub;
    CheckoutController controllerStub;
    CreditCard cardStub;
    CardReader cardReaderStub;
    CardReaderController readerControllerStub;
    
    GiftCard giftCardStub;
    
    private class TestBank extends CardIssuer {
        public boolean held;
        public boolean posted;
        public boolean noPostCall;
        public boolean noHoldCall;
        public boolean holdAuthorized;
        public boolean canPostTransaction;
        
        /**
         * Create a card provider.
         *
         * @param name The company's name.
         * @throws SimulationException If name is null.
         */
        public TestBank(String name) {
            super(name);
            noHoldCall=true;
            noPostCall=true;
        }
        public int authorizeHold(String cardNumber, BigDecimal amount) {
            if (holdAuthorized) {
                held=true;
                return 1;
            } else {
                held=false;
                return -1;
            }
        }
        public boolean postTransaction(String cardNumber, int holdNumber, BigDecimal actualAmount) {
            noPostCall=false;
            if (holdNumber == 1 && canPostTransaction) {
                this.posted = true;
                return true;
            } else {
                this.posted=false;
                return false;
            }
        }

        }

    @Before
    public void setup(){
        bankStub = new TestBank("TestBank");
        cardStub= new CreditCard(
                "Credit Card", "12345","Steve", "987","1337",true, true
        );
        giftCardStub = new GiftCard("Gift Card", "43110", "1234", Currency.getInstance("CAD"), new BigDecimal(100));
        controllerStub = new CheckoutController();
        cardReaderStub = new CardReader();
        readerControllerStub = new CardReaderController(cardReaderStub);
        controllerStub.registerPaymentController(readerControllerStub);
        readerControllerStub.disableDevice();
        readerControllerStub.setMainController(controllerStub);
    }

    @Test
    public void testSuccessfulTransaction(){
        assertTrue(cardReaderStub.isDisabled());
        readerControllerStub.enablePayment(bankStub, BigDecimal.ONE);
        assertFalse(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = true;
        bankStub.holdAuthorized = true;
        try {
            cardReaderStub.insert(cardStub, "1337");
        } catch (Exception ex){
            fail("Exception incorrectly thrown");
        }
        assertTrue(readerControllerStub.isPaying);
        assertTrue(bankStub.held);
        assertTrue(bankStub.posted);
        cardReaderStub.remove();
        assertFalse(readerControllerStub.isPaying);

        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(-1));
        assertTrue(cardReaderStub.isDisabled());
    }
    
    @Test
    public void testRepeatedBadPIN() {
    	assertTrue(cardReaderStub.isDisabled());
        readerControllerStub.enablePayment(bankStub, BigDecimal.ONE);
        assertFalse(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = true;
        bankStub.holdAuthorized = true;
        try {
            cardReaderStub.insert(cardStub, "1336");
        } catch (Exception ex){
        	cardReaderStub.remove();
           try {
			cardReaderStub.insert(cardStub, "1336");
		} catch (Exception e) {
			cardReaderStub.remove();
			try {
				cardReaderStub.insert(cardStub, "1336");
			} catch (Exception e1) {
				cardReaderStub.remove();
			}
			
			}
        }
        assertFalse(bankStub.held);
        assertTrue(bankStub.noPostCall);
        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(0));
        assertFalse(cardReaderStub.isDisabled());
    }

    @Test
    public void testPostFailed(){
        assertTrue(cardReaderStub.isDisabled());
        readerControllerStub.enablePayment(bankStub, BigDecimal.ONE);
        assertFalse(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = false;
        bankStub.holdAuthorized = true;
        try {
            cardReaderStub.insert(cardStub, "1337");
        } catch (Exception ex){
            fail("Exception incorrectly thrown");
        }
        assertTrue(readerControllerStub.isPaying);
        assertTrue(bankStub.held);
        assertFalse(bankStub.posted);
        cardReaderStub.remove();
        assertFalse(readerControllerStub.isPaying);
        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(0));
        assertTrue(cardReaderStub.isDisabled());
    }
    @Test
    public void testHoldFailed(){
        assertTrue(cardReaderStub.isDisabled());
        readerControllerStub.enablePayment(bankStub, BigDecimal.ONE);
        assertFalse(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = false;
        bankStub.holdAuthorized = false;
        try {
            cardReaderStub.insert(cardStub, "1337");

        } catch (Exception ex){
            fail("Exception incorrectly thrown");
        }
        assertFalse(bankStub.held);
        assertTrue(bankStub.noPostCall);
        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(0));

        assertTrue(cardReaderStub.isDisabled());
    }

    @Test
    public void testNotEnabled(){
        assertTrue(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = false;
        bankStub.holdAuthorized = false;
        try {
            cardReaderStub.insert(cardStub, "1337");
        } catch (Exception ex){

            fail("Exception incorrectly thrown");
        }
        assertTrue(bankStub.noHoldCall);
        assertTrue(bankStub.noPostCall);
        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(0));
        assertTrue(cardReaderStub.isDisabled());
    }
    @Test
    public void testSwipeFail(){
        assertTrue(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = true;
        bankStub.holdAuthorized = true;
        try {
            cardReaderStub.swipe(cardStub,new BufferedImage(10,10,10));
        } catch (Exception ex){
            fail("Exception incorrectly thrown");
        }
        assertTrue(bankStub.noHoldCall);
        assertTrue(bankStub.noPostCall);
        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(0));
        assertTrue(cardReaderStub.isDisabled());
    }

    @Test
    public void testTapFail(){
        assertTrue(cardReaderStub.isDisabled());
        bankStub.canPostTransaction = true;
        bankStub.holdAuthorized = true;
        try {
            cardReaderStub.tap(cardStub);
        } catch (Exception ex){

            fail("Exception incorrectly thrown");
        }
        assertTrue(bankStub.noHoldCall);
        assertTrue(bankStub.noPostCall);
        assertEquals(controllerStub.getRemainingAmount(), BigDecimal.valueOf(0));
        assertTrue(cardReaderStub.isDisabled());
    }

    /**
     * Since we also need to test whether PayByCard works in checkout controller, it
     * makes sense to do so here
     */


    @Test
    public void payByCardTestSuccess(){
        assertFalse(readerControllerStub.isPaying);
        controllerStub.cost=BigDecimal.ONE;
        controllerStub.payByCard(bankStub,BigDecimal.ONE);
        assertFalse(cardReaderStub.isDisabled());
        assertEquals(readerControllerStub.bank, bankStub);

    }



    @Test
    public void payByCardTestSystemProtectionLock(){
        assertTrue(cardReaderStub.isDisabled());
        controllerStub.cost=BigDecimal.ONE;
        controllerStub.systemProtectionLock=true;
        controllerStub.payByCard(bankStub,BigDecimal.ONE);
        assertTrue(cardReaderStub.isDisabled());
    }

    @Test
    public void payByCardTestBaggingLock(){
        assertTrue(cardReaderStub.isDisabled());
        controllerStub.cost=BigDecimal.ONE;
        controllerStub.baggingItemLock=true;
        controllerStub.payByCard(bankStub,BigDecimal.ONE);
        assertTrue(cardReaderStub.isDisabled());
    }

    @Test
    public void payByCardTestNullBank(){
        assertTrue(cardReaderStub.isDisabled());
        controllerStub.cost=BigDecimal.ONE;
        controllerStub.payByCard(null,BigDecimal.ONE);
        assertTrue(cardReaderStub.isDisabled());
    }

    @Test
    public void payByCardTestPayMoreThanOrderCost(){
        assertTrue(cardReaderStub.isDisabled());
        controllerStub.cost = BigDecimal.ZERO;
        controllerStub.payByCard(null,BigDecimal.ONE);
        assertTrue(cardReaderStub.isDisabled());

    }
    
    
    @Test
    public void payByGiftCardSuccessTest() throws InvalidPINException {
    	GiftCardInsertData data = null;
        assertTrue(cardReaderStub.isDisabled());
        readerControllerStub.enablePayment(null, new BigDecimal(47.85));
        assertFalse(cardReaderStub.isDisabled());
        try {
            cardReaderStub.insert(giftCardStub, "1234");
        } catch (Exception ex){
            fail("Exception incorrectly thrown");
        }
        assertTrue(readerControllerStub.isPaying);
        cardReaderStub.remove();
        assertFalse(readerControllerStub.isPaying);
        assertEquals(-47.85, controllerStub.getRemainingAmount().floatValue(), 0.01);
        assertTrue(cardReaderStub.isDisabled());
        
        
        data = giftCardStub.createCardInsertData("1234");
        double expected = 100-47.85;
        assertEquals(expected, data.getRemainingBalance().doubleValue(), 0.01);
    }
    
    @Test
    public void payByGiftCardInsufficentFunds() throws InvalidPINException {
    	controllerStub.cost = new BigDecimal(125);
        readerControllerStub.enablePayment(null, controllerStub.cost);

    	try {
            cardReaderStub.insert(giftCardStub, "1234");
        } catch (Exception ex){
            fail("Exception incorrectly thrown");
        }
    	
    	assertEquals(25, controllerStub.getRemainingAmount().floatValue(), 0.01);
    	
    	GiftCardInsertData data = giftCardStub.createCardInsertData("1234");
        double expected = 0;
        assertEquals(expected, data.getRemainingBalance().doubleValue(), 0.01);
    }
    
    @Test
    public void payByGiftCardNoFunds() throws IOException {
    	controllerStub.cost = new BigDecimal(125);
        readerControllerStub.enablePayment(null, controllerStub.cost);

        cardReaderStub.insert(giftCardStub, "1234");
    	
    	cardReaderStub.remove();
        cardReaderStub.insert(giftCardStub, "1234");

    	
    	assertEquals(25, controllerStub.getRemainingAmount().floatValue(), 0.01);
    	
    	GiftCardInsertData data = giftCardStub.createCardInsertData("1234");
        double expected = 0;
        assertEquals(expected, data.getRemainingBalance().doubleValue(), 0.01); 
    }
    
    @Test (expected = InvalidPINException.class)
    public void payByGiftCardIncorrectPIN() throws IOException {
    	cardReaderStub.insert(giftCardStub, "2222");
    }




    @After
    public void teardown(){
        bankStub=null;
        controllerStub=null;
        cardReaderStub=null;
        cardStub=null;
        readerControllerStub =null;
    }




}
