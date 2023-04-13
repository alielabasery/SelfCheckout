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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.autovend.IllegalDigitException;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.MembershipCardController;

public class MembershipDetailsPanel extends JPanel {
	MembershipCardController mcc;
	GuiController gc;
	JLabel membershipByTypingLabel;
	JTextField IDField;
	JLabel IDLabel;
	JButton button;
	JButton backButton;
	JLabel failLabel;
	String userID;
	JButton zero;
	JButton one;
	JButton two;
	JButton three;
	JButton four;
	JButton five;
	JButton six;
	JButton seven;
	JButton eight;
	JButton nine;
	private String vkeyboard = "";
	
	public MembershipDetailsPanel(GuiController gc, MembershipCardController mcc, CheckoutController controller) {
        this.gc = gc;
        this.mcc = mcc;
      
        setPreferredSize(new Dimension(1280, 720));
        setLayout(null);
        
        membershipByTypingLabel = new JLabel("Please enter your membership below:");
        membershipByTypingLabel.setBounds(450, 150, 750, 150);
        membershipByTypingLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        
        //Used for entering membership number 
        IDField = new JTextField();
        IDField.setBounds(540, 200, 200, 100);
        IDField.setHorizontalAlignment(JTextField.CENTER); // set the horizontal alignment
        IDLabel = new JLabel("Membership Number:");
        IDField.setBounds(540, 270, 200, 25);
        IDField.setHorizontalAlignment(JLabel.CENTER); // set the horizontal alignment
        
        //Used when the user enters a valid membership number 
        button = new JButton("Connect");
        button.setBackground(Color.decode("#ade89b"));
        button.setForeground(Color.BLACK);
        button.setBorder(new LineBorder(Color.BLACK, 1, true));
        button.setBounds(590, 300, 100, 20);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        backButton = new JButton("Back");
        backButton.setBounds(590, 330, 100, 20);
        backButton.setBackground(Color.decode("#ade89b"));
        backButton.setForeground(Color.BLACK);
        backButton.setBorder(new LineBorder(Color.BLACK, 1, true));
        backButton.setOpaque(true);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //To go to the previous panel 
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gc.membershipScreen(controller);
            }
        });

        add(backButton);
       
        
        failLabel = new JLabel("The membership number entered was invalid");
        failLabel.setBounds(510, 365, 300, 20);
        failLabel.setForeground(Color.RED);
        
        
        //Buttons for virtual keyboard 
        zero = new JButton("0");
        zero.setBounds(630, 460, 20, 20);
        zero.setBackground(Color.decode("#ade89b"));
        zero.setForeground(Color.BLACK);
        zero.setBorder(new LineBorder(Color.BLACK, 1, true));
        zero.setOpaque(true);
        zero.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		vkeyboard += "0";
        		IDField.setText(vkeyboard);
        	}
        });
        
        one = new JButton("1");
        one.setBounds(610, 440, 20, 20);
        one.setBackground(Color.decode("#ade89b"));
        one.setForeground(Color.BLACK);
        one.setBorder(new LineBorder(Color.BLACK, 1, true));
        one.setOpaque(true);
        one.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		vkeyboard += "1";
        		IDField.setText(vkeyboard);	
        	}
        });
        
        two = new JButton("2");
        two.setBounds(630, 440, 20, 20);
        two.setBackground(Color.decode("#ade89b"));
        two.setForeground(Color.BLACK);
        two.setBorder(new LineBorder(Color.BLACK, 1, true));
        two.setOpaque(true);
        two.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		vkeyboard += "2";
        		IDField.setText(vkeyboard);
        	}
        });
        
		three = new JButton("3");
		three.setBounds(650, 440, 20, 20);
		three.setBackground(Color.decode("#ade89b"));
		three.setForeground(Color.BLACK);
		three.setBorder(new LineBorder(Color.BLACK, 1, true));
		three.setOpaque(true);
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vkeyboard += "3";
				IDField.setText(vkeyboard);
			}
		});

		four = new JButton("4");
		four.setBounds(610, 420, 20, 20);
		four.setBackground(Color.decode("#ade89b"));
		four.setForeground(Color.BLACK);
		four.setBorder(new LineBorder(Color.BLACK, 1, true));
		four.setOpaque(true);
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vkeyboard += "4";
				IDField.setText(vkeyboard);
				
			}
		});

		five = new JButton("5");
		five.setBounds(630, 420, 20, 20);
		five.setBackground(Color.decode("#ade89b"));
		five.setForeground(Color.BLACK);
		five.setBorder(new LineBorder(Color.BLACK, 1, true));
		five.setOpaque(true);
		five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vkeyboard += "5";
				IDField.setText(vkeyboard);
				
			}
		});

		six = new JButton("6");
		six.setBounds(650, 420, 20, 20);
		six.setBackground(Color.decode("#ade89b"));
		six.setForeground(Color.BLACK);
		six.setBorder(new LineBorder(Color.BLACK, 1, true));
		six.setOpaque(true);
		six.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vkeyboard += "6";
				IDField.setText(vkeyboard);
				
			}
		});

		seven = new JButton("7");
		seven.setBounds(610, 400, 20, 20);
		seven.setBackground(Color.decode("#ade89b"));
		seven.setForeground(Color.BLACK);
		seven.setBorder(new LineBorder(Color.BLACK, 1, true));
		seven.setOpaque(true);
		seven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vkeyboard += "7";
				IDField.setText(vkeyboard);
				
			}
		});

		eight = new JButton("8");
		eight.setBounds(630, 400, 20, 20);
		eight.setBackground(Color.decode("#ade89b"));
		eight.setForeground(Color.BLACK);
		eight.setBorder(new LineBorder(Color.BLACK, 1, true));
		eight.setOpaque(true);
		eight.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			vkeyboard += "8";
			IDField.setText(vkeyboard);
		}
		});
		
		nine = new JButton("9");
		nine.setBounds(650, 400, 20, 20);
		nine.setBackground(Color.decode("#ade89b"));
		nine.setForeground(Color.BLACK);
		nine.setBorder(new LineBorder(Color.BLACK, 1, true));
		nine.setOpaque(true);
		nine.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			vkeyboard += "9";
			IDField.setText(vkeyboard);
		}
		});
		
		//On submit
		 button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                userID = IDField.getText();
	                try {
	                	//If it's valid
	                    Boolean success = mcc.isValid(userID);
	                    if (success) { gc.addItemsScreen(); }
	                    remove(failLabel);
	                    revalidate();
	                    repaint();
	                   
	                } catch (IllegalDigitException s) {
	                	//Otherwise reset text field and prompt error message
	                    IDField.setText("");
	                    vkeyboard="";
	                    add(failLabel);
	                    revalidate();
	                    repaint();
	                    
	                }
	            }
	        });
	    //Adding it to the panel
        add(zero);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        add(six);
        add(seven);
        add(eight);
        add(nine);
        add(membershipByTypingLabel);
        add(IDField);
        add(IDLabel);
        add(button);
    }
}