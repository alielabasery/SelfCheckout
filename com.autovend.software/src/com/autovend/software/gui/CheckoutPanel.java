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

        JLabel lblNewLabel_1 = new JLabel("Please choose from one of the following payment options: ");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 32));
        lblNewLabel_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_1.setBounds(96, 90, 939, 58);
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
        totalAmount.setEditable(false);
        totalAmount.setBounds(1087, 314, 86, 58);
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
                    //Cash selected
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
        totalAmount.setEditable(false);
        totalAmount.setBounds(1087, 314, 86, 58);
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
                    String type = txtGiftCard.getText();
                    String number = cardNumber.getText();
                    String pin = pinNumber.getText();
                    Currency currencyValue = Currency.getInstance(currency.getText());
                    BigDecimal amountValue = new BigDecimal(amount.getText());
                    giftCard = new GiftCard(type, number, pin, currencyValue, amountValue);
                    //totalAmount.setText("10000");
                    GiftCardInsertData giftCardInsertData;
                    try {
                        giftCardInsertData = giftCard.createCardInsertData(pin);
                        BigDecimal balance = giftCardInsertData.getRemainingBalance();
                        BigDecimal total = new BigDecimal(totalAmount.getText());
                        int compare = balance.compareTo(total);
                        if (compare >= 0) {
                            giftCardInsertData.deduct(total);
                            currency.setText("Accepted");
                        } else {
                            currency.setText("Declined");
                        }
                    } catch (InvalidPINException e1) {
                        pinNumber.setText("Incorrect Pin Number!");
                    } catch (ChipFailureException e1) {
                        cardNumber.setText("Chip failure!");
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
        totalAmount.setEditable(false);
        totalAmount.setBounds(1087, 314, 86, 58);
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
                        cvv.setText("Went through");
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
}
