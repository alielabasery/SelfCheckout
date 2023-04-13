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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.autovend.devices.SimulationException;
import com.autovend.devices.TouchScreen;
import com.autovend.software.controllers.AddItemByPLUController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.MembershipCardController;

import models.FoundProductsTableModel;

public class AddItemsPanel extends JPanel {
    GuiController gc;
    JLabel itemlabel;
    JPanel plupanel;
    JLabel plulabel;
    JPanel browsepanel;
    JLabel browselabel;
    JButton plubutton;
    JButton browsebutton;
	JTextField PLUField;
	JButton cartbutton;
	JButton paybutton;
	CheckoutController controller;
	ArrayList<Object> cart;
	ArrayList<Integer> cartcount;
	AddItemsPanel itempanel;
	public TouchScreen touchScreen;
	public AddItemByPLUController addItemByPLUController;
    public AddItemsPanel(GuiController gc, CheckoutController controller) {
    	cart = new ArrayList<Object>();
    	cartcount = new ArrayList<Integer>();
        this.gc = gc;
        this.controller = controller;
        //CheckoutController cc = new CheckoutController("Station 1", this.gc.station);
        //touchScreen = new TouchScreen();
        //addItemByPLUController = new AddItemByPLUController(touchScreen);
        //addItemByPLUController.setMainController(cc);
        //addItemByPLUController.addProducts();
        //touchScreen.register(addItemByPLUController);

        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);

        itemlabel = new JLabel("How Do You Want To Add Items?:");
        itemlabel.setBounds(450, 150, 750, 150);
        itemlabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

        plubutton = new JButton("By PLU Code");
        plubutton.setBackground(Color.decode("#ade89b"));
        plubutton.setForeground(Color.BLACK);
        plubutton.setBorder(new LineBorder(Color.BLACK, 1, true));
        plubutton.setBounds(590, 300, 150, 20);
        plubutton.setOpaque(true);
        plubutton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        browsebutton = new JButton("By Browsing");
        browsebutton.setBackground(Color.decode("#ade89b"));
        browsebutton.setForeground(Color.BLACK);
        browsebutton.setBorder(new LineBorder(Color.BLACK, 1, true));
        browsebutton.setBounds(390, 300, 150, 20);
        browsebutton.setOpaque(true);
        browsebutton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cartbutton = new JButton("Show Cart");
        cartbutton.setBackground(Color.decode("#ad589b"));
        cartbutton.setForeground(Color.BLACK);
        cartbutton.setBorder(new LineBorder(Color.BLACK, 1, true));
        cartbutton.setBounds(390, 500, 150, 20);
        cartbutton.setOpaque(true);
        cartbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        paybutton = new JButton("Checkout");
        paybutton.setBackground(Color.decode("#ad589b"));
        paybutton.setForeground(Color.BLACK);
        paybutton.setBorder(new LineBorder(Color.BLACK, 1, true));
        paybutton.setBounds(590, 500, 150, 20);
        paybutton.setOpaque(true);
        paybutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    	itempanel = this;

        plubutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dlgPLUProduct dlg = new dlgPLUProduct(new JFrame(), "Add By PLU Code.", itempanel);
                dlg.setVisible(true);
            }
        });

        browsebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dlgSearchProduct dlg = new dlgSearchProduct(new JFrame(), "Add By Browsing.");
                dlg.setVisible(true);
            }
        });

        cartbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dlgcartpay dlg = new dlgcartpay(new JFrame(), "Cart", false);
                dlg.setVisible(true);
            }
        });

        paybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { gc.checkoutScreen(); }
        });

        add(itemlabel);
        add(plubutton);
        add(browsebutton);
        add(cartbutton);
        add(paybutton);
    }
}
