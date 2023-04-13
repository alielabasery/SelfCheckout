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
package com.autovend.software.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.*;
import javax.swing.*;
import com.autovend.GiftCard.*;
import com.autovend.*;
import com.autovend.external.CardIssuer;

public class CheckoutPanel extends JPanel {
    private JFrame frame;
    private JPanel contentPane;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextField textField;
    private final ButtonGroup tapValue = new ButtonGroup();
    private final ButtonGroup chipValue = new ButtonGroup();
    private final ButtonGroup cardUsed = new ButtonGroup();
    private boolean alreadyDisplay = false;
    private CreditCard creditCard;
    private DebitCard debitCard;
    private GiftCard giftCard;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CheckoutPanel window = new CheckoutPanel();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CheckoutPanel() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        if (!alreadyDisplay) {
            frame = new JFrame();
            frame.getContentPane().setBackground(SystemColor.activeCaption);
            frame.setBounds(100, 100, 1280, 720);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(null);
        }
        alreadyDisplay = true;

        JLabel lblNewLabel = new JLabel("Checkout");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        frame.getContentPane().add(separator);

        JRadioButton creditCard = new JRadioButton("Credit/Debit Card");
        buttonGroup.add(creditCard);
        creditCard.setSelected(true);
        creditCard.setBackground(SystemColor.inactiveCaption);
        creditCard.setFont(new Font("Arial", Font.PLAIN, 50));
        creditCard.setBounds(301, 155, 495, 114);
        frame.getContentPane().add(creditCard);

        JRadioButton giftCard = new JRadioButton("Gift Card");
        buttonGroup.add(giftCard);
        giftCard.setBackground(SystemColor.inactiveCaption);
        giftCard.setFont(new Font("Arial", Font.PLAIN, 50));
        giftCard.setBounds(301, 283, 495, 114);
        frame.getContentPane().add(giftCard);

        JRadioButton cash = new JRadioButton("Cash");
        buttonGroup.add(cash);
        cash.setBackground(SystemColor.inactiveCaption);
        cash.setFont(new Font("Arial", Font.PLAIN, 50));
        cash.setBounds(301, 410, 495, 114);
        frame.getContentPane().add(cash);

        JLabel lblNewLabel_1 = new JLabel("Choose one of the following payment options: ");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 32));
        lblNewLabel_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_1.setBounds(185, 90, 939, 58);
        frame.getContentPane().add(lblNewLabel_1);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        frame.getContentPane().add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        frame.getContentPane().add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        frame.getContentPane().add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        frame.getContentPane().add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setFont(new Font("Arial", Font.PLAIN, 35));
        totalAmount.setEditable(false);
        totalAmount.setBounds(1075, 314, 131, 58);
        frame.getContentPane().add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        frame.getContentPane().add(lblTotal_1);

        JButton btnFinishAndPay = new JButton("Continue To Payment Method");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNewLabel.setVisible(false);
                lblNewLabel_1.setVisible(false);
                creditCard.setVisible(false);
                cash.setVisible(false);
                giftCard.setVisible(false);
                btnFinishAndPay.setVisible(false);

                String selectedButton = null;
                Enumeration<AbstractButton> allRadioButtons = buttonGroup.getElements();
                while(allRadioButtons.hasMoreElements()) {
                    JRadioButton button = (JRadioButton) allRadioButtons.nextElement();
                    if (button.isSelected()) {
                        selectedButton = button.getText();
                    }
                }

                if (selectedButton == "Credit/Debit Card") {
                    proceedToCreditDebitPaymentMethod();
                } else if (selectedButton == "Gift Card") {
                    proceedToGiftCardPaymentMethod();
                } else {
                    proceedToCashPaymentMethod();
                }
            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 21));
        btnFinishAndPay.setBounds(369, 572, 357, 83);
        frame.getContentPane().add(btnFinishAndPay);
    }

    private void proceedToGiftCardPaymentMethod() {
//		frame = new JFrame();
//		frame.getContentPane().setBackground(SystemColor.activeCaption);
//		frame.setBounds(100, 100, 1280, 720);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Payment");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        frame.getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        frame.getContentPane().add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        frame.getContentPane().add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        frame.getContentPane().add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        frame.getContentPane().add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setFont(new Font("Arial", Font.PLAIN, 35));
        totalAmount.setEditable(false);
        totalAmount.setBounds(1075, 314, 131, 58);
        frame.getContentPane().add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        frame.getContentPane().add(lblTotal_1);

        JTextField cardNumber = new JTextField();
        cardNumber.setFont(new Font("Arial", Font.BOLD, 30));
        cardNumber.setColumns(10);
        cardNumber.setBounds(404, 190, 400, 49);
        frame.getContentPane().add(cardNumber);

        JTextField amount = new JTextField();
        amount.setFont(new Font("Arial", Font.BOLD, 30));
        amount.setColumns(10);
        amount.setBounds(404, 450, 400, 49);
        frame.getContentPane().add(amount);

        JTextField currency = new JTextField();
        currency.setFont(new Font("Arial", Font.BOLD, 30));
        currency.setColumns(10);
        currency.setBounds(404, 360, 400, 49);
        frame.getContentPane().add(currency);

        JTextField pinNumber = new JTextField();
        pinNumber.setFont(new Font("Arial", Font.BOLD, 30));
        pinNumber.setColumns(10);
        pinNumber.setBounds(404, 270, 400, 49);
        frame.getContentPane().add(pinNumber);

        JLabel lblNewLabel_2 = new JLabel("Card Number");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2.setBounds(263, 193, 131, 49);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Currency");
        lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(300, 363, 94, 49);
        frame.getContentPane().add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_1_1 = new JLabel("Amount");
        lblNewLabel_2_1_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_1.setBounds(313, 453, 73, 49);
        frame.getContentPane().add(lblNewLabel_2_1_1);

        JLabel lblNewLabel_2_1_2 = new JLabel("Pin Number");
        lblNewLabel_2_1_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_2.setBounds(284, 273, 110, 49);
        frame.getContentPane().add(lblNewLabel_2_1_2);

        JLabel lblNewLabel_2_3 = new JLabel("Card Type");
        lblNewLabel_2_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_3.setBounds(291, 103, 103, 49);
        frame.getContentPane().add(lblNewLabel_2_3);

        JTextField txtGiftCard = new JTextField();
        txtGiftCard.setEditable(false);
        txtGiftCard.setHorizontalAlignment(SwingConstants.CENTER);
        txtGiftCard.setText("Gift Card");
        txtGiftCard.setFont(new Font("Arial", Font.BOLD, 30));
        txtGiftCard.setColumns(10);
        txtGiftCard.setBounds(404, 100, 400, 49);
        frame.getContentPane().add(txtGiftCard);

        JButton btnFinishAndPay = new JButton("Finalize Payment");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cardNumber.getText().equals("")) {
                    cardNumber.setText("Enter Card Number!");
                } else if (pinNumber.getText().equals("")) {
                    pinNumber.setText("Enter Pin Number!");
                } else if (currency.getText().equals("")) {
                    currency.setText("Enter Currency type!");
                } else if (amount.getText().equals("")) {
                    amount.setText("Enter Amount!");
                } else {
                    try {
                        String type = txtGiftCard.getText();
                        String number = cardNumber.getText();
                        String pin = pinNumber.getText();
                        Currency currencyValue = Currency.getInstance(currency.getText());
                        BigDecimal amountValue = new BigDecimal(amount.getText());
                        giftCard = new GiftCard(type, number, pin, currencyValue, amountValue);
                        totalAmount.setText("10000");
                        GiftCardInsertData giftCardInsertData;
                        giftCardInsertData = giftCard.createCardInsertData(pin);
                        BigDecimal balance = giftCardInsertData.getRemainingBalance();
                        BigDecimal total = new BigDecimal(totalAmount.getText());
                        int compare = balance.compareTo(total);
                        if (compare >= 0) {
                            giftCardInsertData.deduct(total);
//                            lblNewLabel_2_1_1.setVisible(false);
//                            lblNewLabel_2_1_2.setVisible(false);
//                            lblNewLabel_2_3.setVisible(false);
//                            lblNewLabel_2_1.setVisible(false);
//                            lblNewLabel_2.setVisible(false);

                            Component[] components = frame.getContentPane().getComponents();
                            for (Component component : components) {
                                component.setVisible(false);
                            }
                            //btnReturn.setVisible(false);
//                            btnFinishAndPay.setVisible(false);
//                            txtGiftCard.setVisible(false);
//                            lblNewLabel.setVisible(false);
//                            cardNumber.setVisible(false);
//                            amount.setVisible(false);
//                            currency.setVisible(false);
//                            pinNumber.setVisible(false);
                            successfulPaymentScreen();
                        } else {
                        	amount.setText("Not enough money");
                        }
                    } catch (InvalidPINException e1) {
                        pinNumber.setText("Incorrect Pin Number!");
                    } catch (ChipFailureException e1) {
                        cardNumber.setText("Chip failure!");
                    } catch (IllegalArgumentException e1) {
                        currency.setText("Incorrect Currency type!");
                    }
                }
            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 35));
        btnFinishAndPay.setBounds(525, 571, 357, 83);
        frame.getContentPane().add(btnFinishAndPay);

        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNewLabel_2_1_1.setVisible(false);
                lblNewLabel_2_1_2.setVisible(false);
                lblNewLabel_2_3.setVisible(false);
                lblNewLabel_2_1.setVisible(false);
                lblNewLabel_2.setVisible(false);
                btnReturn.setVisible(false);
                btnFinishAndPay.setVisible(false);
                txtGiftCard.setVisible(false);
                lblNewLabel.setVisible(false);
                cardNumber.setVisible(false);
                amount.setVisible(false);
                currency.setVisible(false);
                pinNumber.setVisible(false);
                initialize();
            }
        });
        btnReturn.setFont(new Font("Arial", Font.BOLD, 35));
        btnReturn.setBounds(98, 571, 357, 83);
        frame.getContentPane().add(btnReturn);
    }


    private void proceedToCreditDebitPaymentMethod() {
        JLabel lblNewLabel = new JLabel("Payment");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        frame.getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        frame.getContentPane().add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        frame.getContentPane().add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        frame.getContentPane().add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        frame.getContentPane().add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setFont(new Font("Arial", Font.PLAIN, 35));
        totalAmount.setEditable(false);
        totalAmount.setBounds(1075, 314, 131, 58);
        frame.getContentPane().add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        frame.getContentPane().add(lblTotal_1);

        JTextField cardNumber = new JTextField();
        cardNumber.setFont(new Font("Arial", Font.BOLD, 30));
        cardNumber.setColumns(10);
        cardNumber.setBounds(404, 158, 400, 49);
        frame.getContentPane().add(cardNumber);

        JTextField cvv = new JTextField();
        cvv.setFont(new Font("Arial", Font.BOLD, 30));
        cvv.setColumns(10);
        cvv.setBounds(404, 279, 400, 49);
        frame.getContentPane().add(cvv);

        JTextField cardholderName = new JTextField();
        cardholderName.setFont(new Font("Arial", Font.BOLD, 30));
        cardholderName.setColumns(10);
        cardholderName.setBounds(404, 218, 400, 49);
        frame.getContentPane().add(cardholderName);

        JTextField pinNumber = new JTextField();
        pinNumber.setFont(new Font("Arial", Font.BOLD, 30));
        pinNumber.setColumns(10);
        pinNumber.setBounds(404, 339, 400, 49);
        frame.getContentPane().add(pinNumber);

        JLabel lblNewLabel_2 = new JLabel("Card Number");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2.setBounds(263, 158, 131, 49);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Full Name");
        lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(291, 221, 103, 49);
        frame.getContentPane().add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_1_1 = new JLabel("CVV");
        lblNewLabel_2_1_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_1.setBounds(343, 282, 51, 49);
        frame.getContentPane().add(lblNewLabel_2_1_1);

        JLabel lblNewLabel_2_1_2 = new JLabel("Pin Number");
        lblNewLabel_2_1_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_2.setBounds(273, 342, 121, 49);
        frame.getContentPane().add(lblNewLabel_2_1_2);

        JLabel lblNewLabel_2_1_3 = new JLabel("Tap Enabled");
        lblNewLabel_2_1_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_3.setBounds(263, 402, 131, 49);
        frame.getContentPane().add(lblNewLabel_2_1_3);

        JLabel lblNewLabel_2_1_4 = new JLabel("Has Chip");
        lblNewLabel_2_1_4.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_4.setBounds(291, 466, 91, 49);
        frame.getContentPane().add(lblNewLabel_2_1_4);

        JRadioButton rdbtnNewRadioButton = new JRadioButton("Yes");
        rdbtnNewRadioButton.setSelected(true);
        tapValue.add(rdbtnNewRadioButton);
        rdbtnNewRadioButton.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNewRadioButton.setBounds(404, 405, 185, 49);
        frame.getContentPane().add(rdbtnNewRadioButton);

        JRadioButton rdbtnNo = new JRadioButton("No");
        tapValue.add(rdbtnNo);
        rdbtnNo.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNo.setBounds(619, 405, 185, 49);
        frame.getContentPane().add(rdbtnNo);

        JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Yes");
        rdbtnNewRadioButton_2.setSelected(true);
        chipValue.add(rdbtnNewRadioButton_2);
        rdbtnNewRadioButton_2.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNewRadioButton_2.setBounds(404, 466, 185, 49);
        frame.getContentPane().add(rdbtnNewRadioButton_2);

        JRadioButton rdbtnNo_1 = new JRadioButton("No");
        chipValue.add(rdbtnNo_1);
        rdbtnNo_1.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNo_1.setBounds(619, 466, 185, 49);
        frame.getContentPane().add(rdbtnNo_1);

        JRadioButton rdbtnCredit = new JRadioButton("Credit");
        rdbtnCredit.setSelected(true);
        cardUsed.add(rdbtnCredit);
        rdbtnCredit.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnCredit.setBounds(404, 102, 185, 49);
        frame.getContentPane().add(rdbtnCredit);

        JRadioButton rdbtnDebit = new JRadioButton("Debit");
        cardUsed.add(rdbtnDebit);
        rdbtnDebit.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnDebit.setBounds(619, 102, 185, 49);
        frame.getContentPane().add(rdbtnDebit);

        JLabel lblNewLabel_2_3 = new JLabel("Card Type");
        lblNewLabel_2_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_3.setBounds(291, 103, 103, 49);
        frame.getContentPane().add(lblNewLabel_2_3);


        JButton btnFinishAndPay = new JButton("Finalize Payment");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cardNumber.getText().equals("")) {
                    cardNumber.setText("Enter card number!");
                } else if (cardholderName.getText().equals("")) {
                    cardholderName.setText("Enter full name!");
                } else if (cvv.getText().equals("")) {
                    cvv.setText("Enter cvv number!");
                } else if (pinNumber.getText().equals("")) {
                    pinNumber.setText("Enter pin number!");
                } else {
                    String type = null;
                    String number = cardNumber.getText();
                    String cardHolder = cardholderName.getText();
                    String cvvValue = cvv.getText();
                    String pinValue = pinNumber.getText();
                    boolean isTapEnabled = false;
                    boolean hasChip = false;

                    Enumeration<AbstractButton> allCardRadioButtons = cardUsed.getElements();
                    while(allCardRadioButtons.hasMoreElements()) {
                        JRadioButton button = (JRadioButton) allCardRadioButtons.nextElement();
                        if (button.isSelected()) {
                            type = button.getText();
                        }
                    }

                    Enumeration<AbstractButton> allTapRadioButtons = tapValue.getElements();
                    while(allTapRadioButtons.hasMoreElements()) {
                        JRadioButton button = (JRadioButton) allTapRadioButtons.nextElement();
                        if (button.isSelected()) {
                            if(button.getText() == "Yes") {
                                isTapEnabled = true;
                            } else {
                                isTapEnabled = false;
                            }
                        }
                    }

                    Enumeration<AbstractButton> allChipRadioButtons = chipValue.getElements();
                    while(allChipRadioButtons.hasMoreElements()) {
                        JRadioButton button = (JRadioButton) allChipRadioButtons.nextElement();
                        if (button.isSelected()) {
                            if(button.getText() == "Yes") {
                                hasChip = true;
                            } else {
                                hasChip = false;
                            }
                        }
                    }

                    CardIssuer cardIssuer = new CardIssuer("Bank");
                    Date date = new Date();
                    Calendar expiry = Calendar.getInstance();
                    expiry.setTime(date);
                    expiry.add(Calendar.MONTH, 6);
                    BigDecimal amount = BigDecimal.valueOf(1000);
                    //totalAmount.setText("10000");
                    BigDecimal amountDue = new BigDecimal(totalAmount.getText());

                    cardIssuer.addCardData(number, cardHolder, expiry, cvvValue, amount);
                    int hold = cardIssuer.authorizeHold(number, amountDue);

                    if(cardIssuer.postTransaction(number, hold, amountDue)) {
//                        lblNewLabel_2_1_1.setVisible(false);
//                        lblNewLabel_2_1_2.setVisible(false);
//                        lblNewLabel_2_3.setVisible(false);
//                        lblNewLabel_2_1.setVisible(false);
//                        lblNewLabel_2.setVisible(false);
//                        btnReturn.setVisible(false);
//                        btnFinishAndPay.setVisible(false);
//                        rdbtnDebit.setVisible(false);
//                        rdbtnCredit.setVisible(false);
//                        rdbtnNewRadioButton.setVisible(false);
//                        rdbtnNo.setVisible(false);
//                        rdbtnNo_1.setVisible(false);
//                        rdbtnNewRadioButton_2.setVisible(false);
//                        lblNewLabel.setVisible(false);
//                        cardNumber.setVisible(false);
//                        lblNewLabel_2_1_3.setVisible(false);
//                        lblNewLabel_2_1_4.setVisible(false);
//                        pinNumber.setVisible(false);
//                        cvv.setVisible(false);
//                        cardholderName.setVisible(false);
                        Component[] components = frame.getContentPane().getComponents();
                        for (Component component : components) {
                            component.setVisible(false);
                        }
                        successfulPaymentScreen();
                    } else {
                        cvv.setText("didnt go through");
                    }
                    if (type.equals("Credit")) {
                        creditCard = new CreditCard(type, number, cardHolder, cvvValue, pinValue, isTapEnabled, hasChip);
                    } else {
                        debitCard = new DebitCard(type, number, cardHolder, cvvValue, pinValue, isTapEnabled, hasChip);
                    }
                }
            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 35));
        btnFinishAndPay.setBounds(525, 571, 357, 83);
        frame.getContentPane().add(btnFinishAndPay);

        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNewLabel_2_1_1.setVisible(false);
                lblNewLabel_2_1_2.setVisible(false);
                lblNewLabel_2_3.setVisible(false);
                lblNewLabel_2_1.setVisible(false);
                lblNewLabel_2.setVisible(false);
                btnReturn.setVisible(false);
                btnFinishAndPay.setVisible(false);
                rdbtnDebit.setVisible(false);
                rdbtnCredit.setVisible(false);
                rdbtnNewRadioButton.setVisible(false);
                rdbtnNo.setVisible(false);
                rdbtnNo_1.setVisible(false);
                rdbtnNewRadioButton_2.setVisible(false);
                lblNewLabel.setVisible(false);
                cardNumber.setVisible(false);
                lblNewLabel_2_1_3.setVisible(false);
                lblNewLabel_2_1_4.setVisible(false);
                pinNumber.setVisible(false);
                cvv.setVisible(false);
                cardholderName.setVisible(false);
                initialize();
            }
        });
        btnReturn.setFont(new Font("Arial", Font.BOLD, 35));
        btnReturn.setBounds(98, 571, 357, 83);
        frame.getContentPane().add(btnReturn);
    }

    private void proceedToCashPaymentMethod(){
        JLabel lblNewLabel = new JLabel("Payment");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        frame.getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        frame.getContentPane().add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        frame.getContentPane().add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        frame.getContentPane().add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        frame.getContentPane().add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setFont(new Font("Arial", Font.PLAIN, 35));
        totalAmount.setEditable(false);
        totalAmount.setBounds(1075, 314, 131, 58);
        frame.getContentPane().add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        frame.getContentPane().add(lblTotal_1);

        JLabel lblNewLabel_2_3 = new JLabel("Cash");
        lblNewLabel_2_3.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel_2_3.setBounds(352, 90, 103, 49);
        frame.getContentPane().add(lblNewLabel_2_3);

        JLabel label_13 = new JLabel("Coins");
        label_13.setFont(new Font("Arial", Font.BOLD, 35));
        label_13.setBounds(779, 90, 103, 49);
        frame.getContentPane().add(label_13);

        JComboBox<Integer> quantityBills100 = new JComboBox<Integer>();
        quantityBills100.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityBills100.setFont(new Font("Arial", Font.BOLD, 35));
        quantityBills100.setBounds(278, 150, 256, 49);
        frame.getContentPane().add(quantityBills100);

        JComboBox<Integer> quantityBills50 = new JComboBox<Integer>();
        quantityBills50.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityBills50.setFont(new Font("Arial", Font.BOLD, 35));
        quantityBills50.setBounds(278, 210, 256, 49);
        frame.getContentPane().add(quantityBills50);

        JComboBox<Integer> quantityBills20 = new JComboBox<Integer>();
        quantityBills20.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityBills20.setFont(new Font("Arial", Font.BOLD, 35));
        quantityBills20.setBounds(278, 270, 256, 49);
        frame.getContentPane().add(quantityBills20);

        JComboBox<Integer> quantityBills10 = new JComboBox<Integer>();
        quantityBills10.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityBills10.setFont(new Font("Arial", Font.BOLD, 35));
        quantityBills10.setBounds(278, 330, 256, 49);
        frame.getContentPane().add(quantityBills10);

        JComboBox<Integer> quantityBills5 = new JComboBox<Integer>();
        quantityBills5.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityBills5.setFont(new Font("Arial", Font.BOLD, 35));
        quantityBills5.setBounds(278, 390, 256, 49);
        frame.getContentPane().add(quantityBills5);

        JComboBox<Integer> quantityBills1 = new JComboBox<Integer>();
        quantityBills1.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityBills1.setFont(new Font("Arial", Font.BOLD, 35));
        quantityBills1.setBounds(278, 450, 256, 49);
        frame.getContentPane().add(quantityBills1);

        JLabel label_7 = new JLabel("$100");
        label_7.setFont(new Font("Arial", Font.BOLD, 30));
        label_7.setBounds(200, 152, 68, 49);
        frame.getContentPane().add(label_7);

        JLabel label_8 = new JLabel("$50");
        label_8.setFont(new Font("Arial", Font.BOLD, 30));
        label_8.setBounds(217, 212, 51, 49);
        frame.getContentPane().add(label_8);

        JLabel label_9 = new JLabel("$20");
        label_9.setFont(new Font("Arial", Font.BOLD, 30));
        label_9.setBounds(217, 272, 51, 49);
        frame.getContentPane().add(label_9);

        JLabel label_10 = new JLabel("$5");
        label_10.setFont(new Font("Arial", Font.BOLD, 30));
        label_10.setBounds(229, 392, 39, 49);
        frame.getContentPane().add(label_10);

        JLabel label_11 = new JLabel("$1");
        label_11.setFont(new Font("Arial", Font.BOLD, 30));
        label_11.setBounds(229, 452, 39, 49);
        frame.getContentPane().add(label_11);

        JLabel label_12 = new JLabel("$10");
        label_12.setFont(new Font("Arial", Font.BOLD, 30));
        label_12.setBounds(217, 332, 51, 49);
        frame.getContentPane().add(label_12);

        JComboBox<Integer> quantityCoins50 = new JComboBox<Integer>();
        quantityCoins50.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityCoins50.setFont(new Font("Arial", Font.BOLD, 35));
        quantityCoins50.setBounds(701, 150, 256, 49);
        frame.getContentPane().add(quantityCoins50);

        JComboBox<Integer> quantityCoins25 = new JComboBox<Integer>();
        quantityCoins25.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityCoins25.setFont(new Font("Arial", Font.BOLD, 35));
        quantityCoins25.setBounds(701, 210, 256, 49);
        frame.getContentPane().add(quantityCoins25);

        JComboBox<Integer> quantityCoins20 = new JComboBox<Integer>();
        quantityCoins20.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityCoins20.setFont(new Font("Arial", Font.BOLD, 35));
        quantityCoins20.setBounds(701, 270, 256, 49);
        frame.getContentPane().add(quantityCoins20);

        JComboBox<Integer> quantityCoins10 = new JComboBox<Integer>();
        quantityCoins10.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityCoins10.setFont(new Font("Arial", Font.BOLD, 35));
        quantityCoins10.setBounds(701, 330, 256, 49);
        frame.getContentPane().add(quantityCoins10);

        JComboBox<Integer> quantityCoins1 = new JComboBox<Integer>();
        quantityCoins1.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityCoins1.setFont(new Font("Arial", Font.BOLD, 35));
        quantityCoins1.setBounds(701, 450, 256, 49);
        frame.getContentPane().add(quantityCoins1);

        JComboBox<Integer> quantityCoins5 = new JComboBox<Integer>();
        quantityCoins5.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5}));
        quantityCoins5.setFont(new Font("Arial", Font.BOLD, 35));
        quantityCoins5.setBounds(701, 390, 256, 49);
        frame.getContentPane().add(quantityCoins5);

        JLabel label_1 = new JLabel("50¢");
        label_1.setFont(new Font("Arial", Font.BOLD, 30));
        label_1.setBounds(640, 152, 51, 49);
        frame.getContentPane().add(label_1);

        JLabel label_2 = new JLabel("25¢");
        label_2.setFont(new Font("Arial", Font.BOLD, 30));
        label_2.setBounds(640, 208, 51, 49);
        frame.getContentPane().add(label_2);

        JLabel label_3 = new JLabel("20¢");
        label_3.setFont(new Font("Arial", Font.BOLD, 30));
        label_3.setBounds(640, 270, 51, 49);
        frame.getContentPane().add(label_3);

        JLabel label_4 = new JLabel("10¢");
        label_4.setFont(new Font("Arial", Font.BOLD, 30));
        label_4.setBounds(640, 330, 51, 49);
        frame.getContentPane().add(label_4);

        JLabel label_5 = new JLabel("5¢");
        label_5.setFont(new Font("Arial", Font.BOLD, 30));
        label_5.setBounds(650, 392, 39, 49);
        frame.getContentPane().add(label_5);

        JLabel label_6 = new JLabel("1¢");
        label_6.setFont(new Font("Arial", Font.BOLD, 30));
        label_6.setBounds(652, 452, 39, 49);
        frame.getContentPane().add(label_6);

        JButton btnFinishAndPay = new JButton("Finalize Payment");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amountPaid = (int)quantityBills100.getSelectedItem() * 100
                        + (int)quantityBills50.getSelectedItem() * 50
                        + (int)quantityBills20.getSelectedItem() * 20
                        + (int)quantityBills10.getSelectedItem() * 10
                        + (int)quantityBills5.getSelectedItem() * 5
                        + (int)quantityBills1.getSelectedItem() * 1
                        + (int)quantityCoins50.getSelectedItem() * 0.50
                        + (int)quantityCoins25.getSelectedItem() * 0.25
                        + (int)quantityCoins20.getSelectedItem() * 0.20
                        + (int)quantityCoins10.getSelectedItem() * 0.10
                        + (int)quantityCoins5.getSelectedItem() * 0.05
                        + (int)quantityCoins1.getSelectedItem() * 0.01;
                BigDecimal cash = BigDecimal.valueOf(amountPaid);
                BigDecimal total = new BigDecimal(totalAmount.getText());
                if (cash.compareTo(total) >= 0) {
                    //complete
//                    lblNewLabel_2_3.setVisible(false);
//                    btnReturn.setVisible(false);
//                    btnFinishAndPay.setVisible(false);
//                    lblNewLabel.setVisible(false);
//                    label_1.setVisible(false);
//                    label_2.setVisible(false);
//                    label_3.setVisible(false);
//                    label_4.setVisible(false);
//                    label_5.setVisible(false);
//                    label_6.setVisible(false);
//                    label_7.setVisible(false);
//                    label_8.setVisible(false);
//                    label_9.setVisible(false);
//                    label_10.setVisible(false);
//                    label_11.setVisible(false);
//                    label_12.setVisible(false);
//                    label_13.setVisible(false);
//                    quantityBills100.setVisible(false);
//                    quantityBills50.setVisible(false);
//                    quantityBills20.setVisible(false);
//                    quantityBills10.setVisible(false);
//                    quantityBills5.setVisible(false);
//                    quantityBills1.setVisible(false);
//                    quantityCoins50.setVisible(false);
//                    quantityCoins25.setVisible(false);
//                    quantityCoins20.setVisible(false);
//                    quantityCoins10.setVisible(false);
//                    quantityCoins5.setVisible(false);
//                    quantityCoins1.setVisible(false);
                    Component[] components = frame.getContentPane().getComponents();
                    for (Component component : components) {
                        component.setVisible(false);
                    }
                    successfulPaymentScreen();
                } else {
                    //incomplete
                }
            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 35));
        btnFinishAndPay.setBounds(525, 571, 357, 83);
        frame.getContentPane().add(btnFinishAndPay);

        JButton btnReturn = new JButton("Return");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblNewLabel_2_3.setVisible(false);
                btnReturn.setVisible(false);
                btnFinishAndPay.setVisible(false);
                lblNewLabel.setVisible(false);
                label_1.setVisible(false);
                label_2.setVisible(false);
                label_3.setVisible(false);
                label_4.setVisible(false);
                label_5.setVisible(false);
                label_6.setVisible(false);
                label_7.setVisible(false);
                label_8.setVisible(false);
                label_9.setVisible(false);
                label_10.setVisible(false);
                label_11.setVisible(false);
                label_12.setVisible(false);
                label_13.setVisible(false);
                quantityBills100.setVisible(false);
                quantityBills50.setVisible(false);
                quantityBills20.setVisible(false);
                quantityBills10.setVisible(false);
                quantityBills5.setVisible(false);
                quantityBills1.setVisible(false);
                quantityCoins50.setVisible(false);
                quantityCoins25.setVisible(false);
                quantityCoins20.setVisible(false);
                quantityCoins10.setVisible(false);
                quantityCoins5.setVisible(false);
                quantityCoins1.setVisible(false);
                initialize();
            }
        });
        btnReturn.setFont(new Font("Arial", Font.BOLD, 35));
        btnReturn.setBounds(98, 571, 357, 83);
        frame.getContentPane().add(btnReturn);
    }

    private void successfulPaymentScreen() {
        JLabel lblNewLabel = new JLabel("Transaction Complete");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(376, 11, 395, 58);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        frame.getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        frame.getContentPane().add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        frame.getContentPane().add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        frame.getContentPane().add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        frame.getContentPane().add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setText("0.00");
        totalAmount.setFont(new Font("Arial", Font.PLAIN, 35));
        totalAmount.setEditable(false);
        totalAmount.setBounds(1075, 314, 131, 58);
        frame.getContentPane().add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        frame.getContentPane().add(lblTotal_1);

        JButton btnReturn = new JButton("Close Checkout GUI");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnReturn.setFont(new Font("Arial", Font.BOLD, 35));
        btnReturn.setBounds(366, 571, 440, 83);
        frame.getContentPane().add(btnReturn);

        JLabel lblYourPaymentMethod = new JLabel("Your payment method");
        lblYourPaymentMethod.setForeground(Color.GREEN);
        lblYourPaymentMethod.setFont(new Font("Arial", Font.BOLD, 45));
        lblYourPaymentMethod.setBackground(Color.BLACK);
        lblYourPaymentMethod.setBounds(308, 122, 522, 74);
        frame.getContentPane().add(lblYourPaymentMethod);

        JLabel lblHasBeenAccepted = new JLabel("Has been accepted");
        lblHasBeenAccepted.setForeground(Color.GREEN);
        lblHasBeenAccepted.setFont(new Font("Arial", Font.BOLD, 45));
        lblHasBeenAccepted.setBackground(Color.BLACK);
        lblHasBeenAccepted.setBounds(343, 195, 423, 65);
        frame.getContentPane().add(lblHasBeenAccepted);

        JLabel lblTotalPaidToday = new JLabel("Total Paid Today: $");
        lblTotalPaidToday.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotalPaidToday.setBackground(Color.BLACK);
        lblTotalPaidToday.setBounds(308, 329, 389, 58);
        frame.getContentPane().add(lblTotalPaidToday);

        JTextField totalPaidToday = new JTextField();
        totalPaidToday.setFont(new Font("Arial", Font.PLAIN, 35));
        totalPaidToday.setEditable(false);
        totalPaidToday.setColumns(10);
        totalPaidToday.setBounds(707, 329, 131, 58);
        frame.getContentPane().add(totalPaidToday);
    }
}
