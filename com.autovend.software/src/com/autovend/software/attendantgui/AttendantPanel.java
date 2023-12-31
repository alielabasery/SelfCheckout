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

package com.autovend.software.attendantgui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import com.autovend.Barcode;
import com.autovend.PriceLookUpCode;
import com.autovend.devices.SupervisionStation;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.PreventPermitStation;
import com.autovend.software.controllers.AttendantLoginLogoutController;
import com.autovend.software.controllers.AttendantShutdownStationController;
import com.autovend.software.controllers.AttendentController;
import com.autovend.software.controllers.CheckoutController;
import com.autovend.software.controllers.GuiController;
import com.autovend.software.controllers.StartUpRoutineController;
import com.autovend.software.controllers.StartUpRoutineFailedException;
import com.autovend.software.gui.dlgSearchProduct;
import com.autovend.software.pojo.CartLineItem;
import com.autovend.software.pojo.CartLineItem.CODETYPE;
import com.autovend.software.utils.CodeUtils;

import Networking.NetworkController;
import data.DatabaseController;

public class AttendantPanel extends JPanel {
	
	//Variables relating to the AttendantPanel
	GuiController gc;
	SupervisionStation attendantStation;
	AttendentController attendantController;
	AttendantLoginLogoutController loginController;
	AttendantShutdownStationController shutdownController;
	StartUpRoutineController startupController;
	
	//Variables for testing button clicks
	public JButton logoutButton;
	public JButton addStationButton;
	public boolean testing = false;
	
	//Keeping track of unsupervised Stations and shutdown Stations
	private ArrayList<String> unsupervisedStations = new ArrayList<String>();
	private static ArrayList<String> shutdownStations = new ArrayList<String>();
	
	public Box stationsBox;
	public Box notificationsBox;
    int stationCounter = 0;
    
    /**
     * 
     * Adds all the elements to the screen, and sets up button actions
     * 
     * @param gc - To manage all GUI screens
     * @param attendantStation - SupervisionStation the panel will be using
     * @param attendantController - AttendantController to control the actions of attendant
     * @param a - to Control the Attendant Login Logout Actions
     */
	public AttendantPanel(GuiController gc, SupervisionStation attendantStation, 
			AttendentController attendantController, AttendantLoginLogoutController a) {
		this.gc = gc;
		this.attendantStation = attendantStation;
		this.attendantController = attendantController;
		this.loginController = a;
		this.shutdownController = new AttendantShutdownStationController(attendantStation, a);
		this.startupController = new StartUpRoutineController(attendantStation, a);
		
		createNotificationHelper();
		
		setPreferredSize(new Dimension(1280, 720));
		setLayout(null);
		
		Panel stationsPanel = new Panel();
		stationsPanel.setBounds(469, 52, 801, 587);
		stationsPanel.setLayout(new BorderLayout(0, 0));
		add(stationsPanel);
		
		stationsBox = Box.createVerticalBox();
		JScrollPane stationsScrollPane = new JScrollPane(stationsBox);
		stationsScrollPane.setPreferredSize(new Dimension(801, 587));
		stationsPanel.add(stationsScrollPane, BorderLayout.NORTH);
		
		updateStationsOnScreen(null);
		
		JLabel stationsLabel = new JLabel("Supervised Stations");
		stationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		stationsLabel.setBounds(469, 10, 183, 33);
		add(stationsLabel);
		
		/*
		 * Clicking Logout Button will switch back to the login screen
		 */
		logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logoutButton.setBounds(1185, 20, 85, 21);
		logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gc.attendantLoginScreen();
            }
        });
		add(logoutButton);
		
		/*
		 * Adding a new station looks for all stations and see's which of them are unsupervised to add
		 */
		addStationButton = new JButton("Add New Station");
		addStationButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addStationButton.setBounds(801, 661, 156, 27);
		addStationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	Object[] choices = unsupervisedStations.toArray();
            	
            	String input;
            	if (!testing) {
            		input = (String) JOptionPane.showInputDialog(null, "Choose an unsupervised Station from below: ",
                			"Add Station", JOptionPane.QUESTION_MESSAGE, null, choices, unsupervisedStations.get(0));
            	} else {
            		input = "Station 1";
            	}
            	
            	if (input != null) {
            		CheckoutController checkoutController = NetworkController.getCheckoutStationController(input);
            		if (checkoutController != null) {
            			try {
            				attendantStation.add(checkoutController.getSelfCheckoutStation());
            			}catch (Exception IllegalStateException) {
            				if (!testing) JOptionPane.showMessageDialog(null, "That Station is already being supervised");
            			}
            			unsupervisedStations.remove(input);
            			updateStationsOnScreen(input);
            		}
            	}
            }
        });
		add(addStationButton);
		
		JLabel notificationsLabel = new JLabel("Notifications");
		notificationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		notificationsLabel.setBounds(10, 10, 183, 33);
		add(notificationsLabel);
		
		Panel notificationsPanel = new Panel();
		notificationsPanel.setBounds(10, 52, 453, 587);
		notificationsPanel.setLayout(new BorderLayout(0, 0));
		add(notificationsPanel);
		
		notificationsBox = Box.createVerticalBox();
		JScrollPane notificationsScrollPanel = new JScrollPane(notificationsBox);
		notificationsScrollPanel.setPreferredSize(new Dimension(801, 587));
		notificationsPanel.add(notificationsScrollPanel, BorderLayout.NORTH);
	}
	
	/**
	 * 
	 * This function takes a station Name and uses that name to add a station to supervise
	 * on the screen
	 * 
	 * @param stationName Adding a station on screen with the given name Station Name
	 */
	public void updateStationsOnScreen(String stationName) {
		if (stationName == null) {
			List<String> stationNames = NetworkController.getCheckoutStationNames();
	
			for (int i = 0; i < stationNames.size(); i++) {
				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
				if (checkoutController != null) {
					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
						addStation(stationNames.get(i));
					} else {
						unsupervisedStations.add(stationNames.get(i));
					}
				}
			}
		} else {
			addStation(stationName);
		}
	}
	
	/**
	 * Adds the station to the screen with given name, and refreshes screen
	 * @param name
	 */
	public void addStation(String name) {
		Component[] temp = getStationPanels(name);
		stationsBox.add(temp[0]);
		stationsBox.add(temp[1]);
		updateScreen();
	}
	
	/**
	 * Adds the notification to the screen with given name, and refreshes screen
	 * @param name
	 */
	public void addNotification(String description) {
		Component[] temp = getNotificationPanels(description);
		notificationsBox.add(temp[0]);
		notificationsBox.add(temp[1]);
		updateScreen();
	}
	
	/**
	 * 
	 * Removes the specified Panels and Separators from screen
	 * this is ran because they are now unsupervised for the attendant
	 * 
	 * @param panel - Panel to be removed
	 * @param separator - Separator to be removed
	 */
	public void removeStation(JPanel panel, JSeparator separator) {
		stationsBox.remove(panel);
		stationsBox.remove(separator);
		updateScreen();
	}
	
	/**
	 * 
	 * Removes the specified Panels and Separators from screen
	 * this is ran because they are now confirmed from the attendant
	 * 
	 * @param panel - Panel to be removed
	 * @param separator - Separator to be removed
	 */
	public void removeNotification(JPanel panel, JSeparator separator) {
		notificationsBox.remove(panel);
		notificationsBox.remove(separator);
		updateScreen();
		notificationsBox.repaint();
	}
	
	private void updateScreen() {
		gc.validateAttendantScreen();
		repaint();
	}
	
	public Component[] getStationPanels(String name) {
		JPanel panel = new JPanel();
		JSeparator separator = new JSeparator();
		
		JLabel label = new JLabel(name);
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton bttn = new JButton("Shutdown " + name);
		panel.add(bttn);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn2 = new JButton("Prevent " + name);
		panel.add(bttn2);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn3 = new JButton("Add Item - " + name);
		panel.add(bttn3);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn4 = new JButton("Remove Item - " + name);
		panel.add(bttn4);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn5 = new JButton("Unsupervise " + name);
		panel.add(bttn5);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		JButton bttn6 = new JButton("Check Order - " + name);
		panel.add(bttn6);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		bttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
        		if (checkoutController != null) {
        			if (bttn.getText().equals("Shutdown " + name)) {
        				shutdownController.shutdownStation(checkoutController.getSelfCheckoutStation(), false);
        				shutdownStations.add(name);
            			bttn.setText("Reboot " + name);
            			bttn2.setEnabled(false);
            			bttn3.setEnabled(false);
            			bttn4.setEnabled(false);
            			updateScreen();
        			}
        			else if (bttn.getText().equals("Reboot " + name)) {
//        				NetworkController.removeCheckoutStation(name);
        				CheckoutController newController;
						try {
							newController = startupController.runStartUpRoutine(checkoutController.getSelfCheckoutStation(), name, true);
							NetworkController.registerCheckoutStation(name, newController);
	        				shutdownStations.remove(name);
	            			bttn.setText("Shutdown " + name);
	            			bttn2.setEnabled(true);
	            			bttn3.setEnabled(true);
	            			bttn4.setEnabled(true);
	            			updateScreen();
						} catch (StartUpRoutineFailedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        				
        			}
        		}
            }
        });
		
		//Permit Station and Prevent Station Implementation doesn't do anything
		bttn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
            	PreventPermitStation prevent = new PreventPermitStation();
        		if (checkoutController != null) {
        			if (bttn2.getText().equals("Prevent " + name)) {
        				prevent.suspend(checkoutController);
        				bttn2.setText("Permit " + name);
        			}
        			else if (bttn2.getText().equals("Permit " + name)) {
        				bttn2.setText("Prevent " + name);
        				prevent.unsuspend(checkoutController);
            			updateScreen();
        			}
        		}
            }
        });
		
		bttn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		dlgSearchProduct test = new dlgSearchProduct(null, null);
        		test.initalize(test, name);
            }
        });
		
		bttn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
            	ArrayList<CartLineItem> order = checkoutController.getCart().getCartLineItems();
            	String[] choices = new String[order.size()];
            	for (int i = 0; i < order.size(); i++) {
            		choices[i] = order.get(i).getProductCode() + " :" + order.get(i).getDescription();
            	}
            	
            	String input = (String) JOptionPane.showInputDialog(null, "Choose an item from customer cart below: ",
            			"Remove Item", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
            	
            	if (input != null) {
            		
            		int indexOfItem = 0;
                	for (int i = 0; i < order.size(); i++) {
                		if (order.get(i).getDescription().equals(input.split(":")[1])) {
                			indexOfItem = i;
                			break;
                		}
                	}
            		
            		if (checkoutController != null) {
            			CartLineItem item = order.get(indexOfItem);
        				checkoutController.removeItem(item);
            		}
            	}
            }
        });
		
		bttn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
        		if (checkoutController != null) {
        			unsupervisedStations.add(name);
        			attendantStation.remove(checkoutController.getSelfCheckoutStation());
        			removeStation(panel, separator);
        		}
            }
        });
		
		bttn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
            	ArrayList<CartLineItem> order = checkoutController.getCart().getCartLineItems();
            	Object[][] rows = new Object[order.size()][4];
            	for (int i = 0; i < order.size(); i++) {
            		rows[i][0] = order.get(i).getProductCode();
            		rows[i][1] = order.get(i).getDescription();
            		rows[i][2] = order.get(i).getPrice().setScale(2, RoundingMode.HALF_EVEN);
            		rows[i][3] = (int)order.get(i).getQuantity();
            	}
        		String[] cols = {"ID", "Description", "Price", "Quantity"};
        		JTable table = new JTable(rows, cols);
        		table.setDefaultEditor(Object.class, null);
        		JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });
				
		if (shutdownStations.contains(name)) {
			bttn.doClick();
		}
		
		Component temp[] = {panel, separator};
		return temp;
	}
	
	public Component[] getNotificationPanels(String description) {
		JPanel panel = new JPanel();
		JSeparator separator = new JSeparator();
		
		JLabel lblNewLabel_1 = new JLabel(description);
		panel.add(lblNewLabel_1);
		panel.add(Box.createRigidArea(new Dimension(100, 0)));
		
		JButton addStationButton = new JButton("Confirm");
		addStationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	removeNotification(panel, separator);
            }
        });
		panel.add(addStationButton);
		panel.add(Box.createRigidArea(new Dimension(25, 0)));
		
		Component[] temp = {panel, separator};
		
		return temp;
	}
	
	private void createNotificationHelper() {
		JFrame testFrame = new JFrame();
		testFrame.setTitle("Attendant Notifications Triggers");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setSize(new Dimension(500, 175));
		JPanel testPanel = new JPanel();
		testFrame.getContentPane().add(testPanel);
		testPanel.setLayout(new GridLayout(7, 1, 0, 0));
		
		JButton btnNewButton = new JButton("Create Ink Low Notification");
		btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' is Low on Ink");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton);
		
		JButton btnNewButton_5 = new JButton("Create Paper Low Notification");
		btnNewButton_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' is Low on Paper");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton_5);
		
		JButton btnNewButton_4 = new JButton("Create Weight Discrepancy Notification");
		btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' has Weight Discrepancy");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton_4);
		
		JButton btnNewButton_6 = new JButton("Create Bills Low Notification");
		btnNewButton_6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' is Low on Bills");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("Create Coins Low Notification");
		btnNewButton_7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' is Low on Coins");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("Create Bag Confirmation Notification");
		btnNewButton_8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' Customer Bag Confirmation");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton_8);
		
		JButton btnNewButton_1 = new JButton("Create No Bag Request Notification");
		btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	List<String> stationNames = NetworkController.getCheckoutStationNames();
            	ArrayList<String> supervisedStations = new ArrayList<String>();
    			for (int i = 0; i < stationNames.size(); i++) {
    				CheckoutController checkoutController = NetworkController.getCheckoutStationController(stationNames.get(i));
    				if (checkoutController != null) {
    					if (attendantStation.supervisedStations().contains(checkoutController.getSelfCheckoutStation())) {
    						supervisedStations.add(stationNames.get(i));
    					}
    				}
    			}
    			if (supervisedStations.size() == 0) {
    				JOptionPane.showMessageDialog(null, "No Stations are being supervised");
    			} else {
	    			Object[] choices = supervisedStations.toArray();
	    			
	    			String input = (String) JOptionPane.showInputDialog(null, "Choose a Station for the event trigger",
	            			"Pick One Station", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
	            	
	            	if (input != null) {
	            		addNotification("Station '" + input + "' is Requesting No Bags");
	            	}
    			}
            }
        });
		testPanel.add(btnNewButton_1);
		testFrame.setVisible(true);
	}
	
	public static void addItemToStation(dlgSearchProduct test, String name) {
		CheckoutController checkoutController = NetworkController.getCheckoutStationController(name);
		if (test.selectedItemType == 'P') {
			PriceLookUpCode code = CodeUtils.stringPLUToPLU(test.selectedItemCode);
			PLUCodedProduct product = (PLUCodedProduct) DatabaseController.getProduct(code, test.selectedItemType);
			checkoutController.addItem(new CartLineItem(product.getPLUCode().toString(), CODETYPE.PLU,
					product.getPrice(), product.isPerUnit(), product.getDescription(), 0.0, 1.50, 1));
		} else if (test.selectedItemType == 'B') {
			Barcode code = CodeUtils.stringBarcodeToBarcode(test.selectedItemCode);
			BarcodedProduct product = (BarcodedProduct) DatabaseController.getProduct(code, test.selectedItemType);
			checkoutController.addItem(new CartLineItem(product.getBarcode().toString(), CODETYPE.BARCODE,
					product.getPrice(), product.isPerUnit(), product.getDescription(), product.getExpectedWeight(), 1.50, 1));
		}
	}
}