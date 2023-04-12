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
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.MembershipCardController;

import models.FoundProductsTableModel;

public class AddItemsPanel extends JPanel {
    JFrame itemframe;
    JPanel itempanel;
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
	List<Object> cart;
	List<Integer> cartcount;
    public AddItemsPanel() {
        itemframe = new JFrame();
        itempanel = new JPanel();
        itempanel.setPreferredSize(new Dimension(1280, 720));
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
        
        plubutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dlgPLUProduct dlg = new dlgPLUProduct(itemframe, "Add By PLU Code.");
            	dlg.setVisible(true);
            }
        });
        
        browsebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dlgSearchProduct dlg = new dlgSearchProduct(itemframe, "Add By Browsing.");
            	dlg.setVisible(true);
            }
        });
        
        cartbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dlgcartpay dlg = new dlgcartpay(itemframe, "Cart", false);
            	dlg.setVisible(true);
            }
        });
        
        paybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dlgcartpay dlg = new dlgcartpay(itemframe, "Cart", true);
            	dlg.setVisible(true);
            }
        });
        
        add(itemlabel);
        add(plubutton);
        add(browsebutton);
        add(cartbutton);
        add(paybutton);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Items");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        AddItemsPanel panel = new AddItemsPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
