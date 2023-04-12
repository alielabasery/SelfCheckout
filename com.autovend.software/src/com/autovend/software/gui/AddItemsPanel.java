package com.autovend.software.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.autovend.devices.SimulationException;
import com.autovend.software.controllers.MembershipCardController;

public class AddItemsPanel extends JPanel {
    JFrame itemframe;
    JPanel itempanel;
    JLabel itemlabel;
    JButton plubutton;
    JButton browsebutton;
    public AddItemsPanel() {
        itemframe = new JFrame();
        itempanel = new JPanel();
        itempanel.setPreferredSize(new Dimension(1280, 720));
        setLayout(null);
        
        itemlabel = new JLabel("How Do You Want To Add Items?:");
        itemlabel.setBounds(450, 150, 750, 150);
        itemlabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        
        plubutton = new JButton("By PLU Scanning");
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
        
        plubutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("it works");  
            }
        });
        
        browsebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("it also works");  
            }
        });
        
        add(itemlabel);
        add(plubutton);
        add(browsebutton);
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
