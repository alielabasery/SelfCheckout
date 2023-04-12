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
    JPanel plupanel;
    JLabel plulabel;
    JPanel browsepanel;
    JLabel browselabel;
    JButton plubutton;
    JButton browsebutton;
	JTextField PLUField;
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
