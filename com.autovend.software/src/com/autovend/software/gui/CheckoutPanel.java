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
package com.autovend.software.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Enumeration;

import javax.swing.*;

public class CheckoutPanel extends JPanel {
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextField textField;
    private final ButtonGroup tapValue = new ButtonGroup();
    private final ButtonGroup chipValue = new ButtonGroup();
    private final ButtonGroup cardUsed = new ButtonGroup();

    /**
     * Create the application.
     */
    public CheckoutPanel() {
        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        JLabel lblNewLabel = new JLabel("Checkout");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        add(separator);

        JRadioButton creditCard = new JRadioButton("Credit/Debit Card");
        buttonGroup.add(creditCard);
        creditCard.setSelected(true);
        creditCard.setBackground(SystemColor.inactiveCaption);
        creditCard.setFont(new Font("Arial", Font.PLAIN, 50));
        creditCard.setBounds(301, 155, 495, 114);
        add(creditCard);

        JRadioButton giftCard = new JRadioButton("Gift Card");
        buttonGroup.add(giftCard);
        giftCard.setBackground(SystemColor.inactiveCaption);
        giftCard.setFont(new Font("Arial", Font.PLAIN, 50));
        giftCard.setBounds(301, 283, 495, 114);
        add(giftCard);

        JRadioButton cash = new JRadioButton("Cash");
        buttonGroup.add(cash);
        cash.setBackground(SystemColor.inactiveCaption);
        cash.setFont(new Font("Arial", Font.PLAIN, 50));
        cash.setBounds(301, 410, 495, 114);
        add(cash);

        JLabel lblNewLabel_1 = new JLabel("Please choose from one of the following payment options: ");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 32));
        lblNewLabel_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_1.setBounds(96, 90, 939, 58);
        add(lblNewLabel_1);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setEditable(false);
        totalAmount.setBounds(1087, 314, 86, 58);
        add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        add(lblTotal_1);

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
        add(btnFinishAndPay);
    }

    /**
     * Initialize the contents of the frame.
     */

    private void proceedToGiftCardPaymentMethod() {
        JLabel lblNewLabel = new JLabel("Payment");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setEditable(false);
        totalAmount.setBounds(1087, 314, 86, 58);
        add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        add(lblTotal_1);

        JTextField cardNumber = new JTextField();
        cardNumber.setFont(new Font("Arial", Font.BOLD, 30));
        cardNumber.setColumns(10);
        cardNumber.setBounds(404, 190, 400, 49);
        add(cardNumber);

        JTextField amount = new JTextField();
        amount.setFont(new Font("Arial", Font.BOLD, 30));
        amount.setColumns(10);
        amount.setBounds(404, 450, 400, 49);
        add(amount);

        JTextField currency = new JTextField();
        currency.setFont(new Font("Arial", Font.BOLD, 30));
        currency.setColumns(10);
        currency.setBounds(404, 360, 400, 49);
        add(currency);

        JTextField pinNumber = new JTextField();
        pinNumber.setFont(new Font("Arial", Font.BOLD, 30));
        pinNumber.setColumns(10);
        pinNumber.setBounds(404, 270, 400, 49);
        add(pinNumber);

        JLabel lblNewLabel_2 = new JLabel("Card Number");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2.setBounds(263, 193, 131, 49);
        add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Currency");
        lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(300, 363, 94, 49);
        add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_1_1 = new JLabel("Amount");
        lblNewLabel_2_1_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_1.setBounds(313, 453, 73, 49);
        add(lblNewLabel_2_1_1);

        JLabel lblNewLabel_2_1_2 = new JLabel("Pin Number");
        lblNewLabel_2_1_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_2.setBounds(284, 273, 110, 49);
        add(lblNewLabel_2_1_2);

        JLabel lblNewLabel_2_3 = new JLabel("Card Type");
        lblNewLabel_2_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_3.setBounds(291, 103, 103, 49);
        add(lblNewLabel_2_3);

        JTextField txtGiftCard = new JTextField();
        txtGiftCard.setEditable(false);
        txtGiftCard.setHorizontalAlignment(SwingConstants.CENTER);
        txtGiftCard.setText("Gift Card");
        txtGiftCard.setFont(new Font("Arial", Font.BOLD, 30));
        txtGiftCard.setColumns(10);
        txtGiftCard.setBounds(404, 100, 400, 49);
        add(txtGiftCard);

        JButton btnFinishAndPay = new JButton("Finalize Payment");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cardNumber.getText() == "") {
                    cardNumber.setText("Enter Card Number!");
                } else if (pinNumber.getText() == "") {
                    pinNumber.setText("Enter Pin Number!");
                } else if (currency.getText() == "") {
                    currency.setText("Enter Currency type!");
                } else if (amount.getText() == "") {
                    amount.setText("Enter Amount!");
                } else {
                    String type = txtGiftCard.getText();
                    String number = cardNumber.getText();
                    String pin = pinNumber.getText();
                    Currency currencyValue = Currency.getInstance(currency.getText());
                    BigDecimal amountValue = new BigDecimal(amount.getText());
                }
            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 35));
        btnFinishAndPay.setBounds(430, 570, 357, 83);
        add(btnFinishAndPay);
    }


    private void proceedToCreditDebitPaymentMethod() {
        JLabel lblNewLabel = new JLabel("Payment");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 537, 1264, 12);
        add(separator_1);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setOrientation(SwingConstants.VERTICAL);
        separator_1_1.setBounds(1014, 67, 21, 471);
        add(separator_1_1);

        JLabel lblTotal = new JLabel("Total");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal.setBackground(Color.BLACK);
        lblTotal.setBounds(1075, 176, 131, 58);
        add(lblTotal);

        JLabel lblBalance = new JLabel("Amount");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 42));
        lblBalance.setBackground(Color.BLACK);
        lblBalance.setBounds(1045, 234, 167, 58);
        add(lblBalance);

        JTextField totalAmount = new JTextField();
        totalAmount.setEditable(false);
        totalAmount.setBounds(1087, 314, 86, 58);
        add(totalAmount);
        totalAmount.setColumns(10);

        JLabel lblTotal_1 = new JLabel("$");
        lblTotal_1.setFont(new Font("Arial", Font.BOLD, 42));
        lblTotal_1.setBackground(Color.BLACK);
        lblTotal_1.setBounds(1043, 314, 34, 58);
        add(lblTotal_1);

        JButton btnFinishAndPay = new JButton("Finalize Payment");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 35));
        btnFinishAndPay.setBounds(430, 570, 357, 83);
        add(btnFinishAndPay);

        JTextField cardNumber = new JTextField();
        cardNumber.setFont(new Font("Arial", Font.BOLD, 30));
        cardNumber.setColumns(10);
        cardNumber.setBounds(404, 158, 400, 49);
        add(cardNumber);

        JTextField cvv = new JTextField();
        cvv.setFont(new Font("Arial", Font.BOLD, 30));
        cvv.setColumns(10);
        cvv.setBounds(404, 279, 400, 49);
        add(cvv);

        JTextField cardholderName = new JTextField();
        cardholderName.setFont(new Font("Arial", Font.BOLD, 30));
        cardholderName.setColumns(10);
        cardholderName.setBounds(404, 218, 400, 49);
        add(cardholderName);

        JTextField pinNumber = new JTextField();
        pinNumber.setFont(new Font("Arial", Font.BOLD, 30));
        pinNumber.setColumns(10);
        pinNumber.setBounds(404, 339, 400, 49);
        add(pinNumber);

        JLabel lblNewLabel_2 = new JLabel("Card Number");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2.setBounds(263, 158, 131, 49);
        add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Full Name");
        lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1.setBounds(291, 221, 103, 49);
        add(lblNewLabel_2_1);

        JLabel lblNewLabel_2_1_1 = new JLabel("CVV");
        lblNewLabel_2_1_1.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_1.setBounds(343, 282, 51, 49);
        add(lblNewLabel_2_1_1);

        JLabel lblNewLabel_2_1_2 = new JLabel("Pin Number");
        lblNewLabel_2_1_2.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_2.setBounds(273, 342, 121, 49);
        add(lblNewLabel_2_1_2);

        JLabel lblNewLabel_2_1_3 = new JLabel("Tap Enabled");
        lblNewLabel_2_1_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_3.setBounds(263, 402, 131, 49);
        add(lblNewLabel_2_1_3);

        JLabel lblNewLabel_2_1_4 = new JLabel("Has Chip");
        lblNewLabel_2_1_4.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_1_4.setBounds(291, 466, 91, 49);
        add(lblNewLabel_2_1_4);

        JRadioButton rdbtnNewRadioButton = new JRadioButton("Yes");
        rdbtnNewRadioButton.setSelected(true);
        tapValue.add(rdbtnNewRadioButton);
        rdbtnNewRadioButton.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNewRadioButton.setBounds(404, 405, 185, 49);
        add(rdbtnNewRadioButton);

        JRadioButton rdbtnNo = new JRadioButton("No");
        tapValue.add(rdbtnNo);
        rdbtnNo.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNo.setBounds(619, 405, 185, 49);
        add(rdbtnNo);

        JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Yes");
        rdbtnNewRadioButton_2.setSelected(true);
        chipValue.add(rdbtnNewRadioButton_2);
        rdbtnNewRadioButton_2.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNewRadioButton_2.setBounds(404, 466, 185, 49);
        add(rdbtnNewRadioButton_2);

        JRadioButton rdbtnNo_1 = new JRadioButton("No");
        chipValue.add(rdbtnNo_1);
        rdbtnNo_1.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnNo_1.setBounds(619, 466, 185, 49);
        add(rdbtnNo_1);

        JRadioButton rdbtnCredit = new JRadioButton("Credit");
        rdbtnCredit.setSelected(true);
        cardUsed.add(rdbtnCredit);
        rdbtnCredit.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnCredit.setBounds(404, 102, 185, 49);
        add(rdbtnCredit);

        JRadioButton rdbtnDebit = new JRadioButton("Debit");
        cardUsed.add(rdbtnDebit);
        rdbtnDebit.setFont(new Font("Arial", Font.BOLD, 25));
        rdbtnDebit.setBounds(619, 102, 185, 49);
        add(rdbtnDebit);

        JLabel lblNewLabel_2_3 = new JLabel("Card Type");
        lblNewLabel_2_3.setFont(new Font("Arial", Font.BOLD, 20));
        lblNewLabel_2_3.setBounds(291, 103, 103, 49);
        add(lblNewLabel_2_3);
    }
}