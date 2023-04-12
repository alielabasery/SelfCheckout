package com.autovend.software.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class CheckoutPanel extends JFrame {

    private JFrame frame;
    private JPanel contentPane;
    private final ButtonGroup buttonGroup = new ButtonGroup();

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
        frame = new JFrame();
        frame.getContentPane().setBackground(SystemColor.activeCaption);
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Checkout");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
        lblNewLabel.setBackground(SystemColor.desktop);
        lblNewLabel.setBounds(516, 11, 185, 58);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 67, 1264, 12);
        frame.getContentPane().add(separator);

        JButton btnFinishAndPay = new JButton("Continue To Payment Method");
        btnFinishAndPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnFinishAndPay.setFont(new Font("Arial", Font.BOLD, 21));
        btnFinishAndPay.setBounds(864, 330, 357, 83);
        frame.getContentPane().add(btnFinishAndPay);

        JRadioButton rdbtnNewRadioButton = new JRadioButton("Credit Card");
        buttonGroup.add(rdbtnNewRadioButton);
        rdbtnNewRadioButton.setSelected(true);
        rdbtnNewRadioButton.setBackground(SystemColor.inactiveCaption);
        rdbtnNewRadioButton.setFont(new Font("Arial", Font.PLAIN, 50));
        rdbtnNewRadioButton.setBounds(69, 281, 309, 64);
        frame.getContentPane().add(rdbtnNewRadioButton);

        JRadioButton rdbtnDebitCard = new JRadioButton("Debit Card");
        buttonGroup.add(rdbtnDebitCard);
        rdbtnDebitCard.setBackground(SystemColor.inactiveCaption);
        rdbtnDebitCard.setFont(new Font("Arial", Font.PLAIN, 50));
        rdbtnDebitCard.setBounds(457, 281, 309, 64);
        frame.getContentPane().add(rdbtnDebitCard);

        JRadioButton rdbtnGiftCard = new JRadioButton("Gift Card");
        buttonGroup.add(rdbtnGiftCard);
        rdbtnGiftCard.setBackground(SystemColor.inactiveCaption);
        rdbtnGiftCard.setFont(new Font("Arial", Font.PLAIN, 50));
        rdbtnGiftCard.setBounds(69, 414, 309, 64);
        frame.getContentPane().add(rdbtnGiftCard);

        JRadioButton rdbtnCash = new JRadioButton("Cash");
        buttonGroup.add(rdbtnCash);
        rdbtnCash.setBackground(SystemColor.inactiveCaption);
        rdbtnCash.setFont(new Font("Arial", Font.PLAIN, 50));
        rdbtnCash.setBounds(457, 414, 309, 64);
        frame.getContentPane().add(rdbtnCash);

        JLabel lblNewLabel_1 = new JLabel("Please choose from one of the following payment options: ");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 32));
        lblNewLabel_1.setBackground(SystemColor.activeCaption);
        lblNewLabel_1.setBounds(162, 90, 939, 58);
        frame.getContentPane().add(lblNewLabel_1);
    }
}