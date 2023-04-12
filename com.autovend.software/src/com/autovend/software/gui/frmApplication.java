package com.autovend.software.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class frmApplication extends JFrame {

	public JPanel contentPane;

	public frmApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {30, 100, 100};
		gbl_contentPane.rowHeights = new int[] {62, 62, 62};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		Component verticalStrut = Box.createVerticalStrut(100);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		
		Component horizontalStrut = Box.createHorizontalStrut(100);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.anchor = GridBagConstraints.WEST;
		gbc_horizontalStrut.fill = GridBagConstraints.VERTICAL;
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 1;
		contentPane.add(horizontalStrut, gbc_horizontalStrut);
		
		JButton btnFindByName = new JButton("Find Product By Name");
		btnFindByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlgSearchProduct dialog = new dlgSearchProduct(frmApplication.this, "Find Product");
                dialog.setModal(true);
                dialog.setSize(800, 400);
                dialog.setVisible(true);

                if (dialog.selectedItemCode != null) {
                    System.out.println(String.format("Item %s [%c] will be added to the cart", dialog.selectedItemCode, dialog.selectedItemType));
                } else {
                    System.out.println("User cancelled search");
                }
			}
		});
		GridBagConstraints gbc_btnFindByName = new GridBagConstraints();
		gbc_btnFindByName.anchor = GridBagConstraints.WEST;
		gbc_btnFindByName.fill = GridBagConstraints.VERTICAL;
		gbc_btnFindByName.insets = new Insets(0, 0, 5, 5);
		gbc_btnFindByName.gridx = 2;
		gbc_btnFindByName.gridy = 1;
		contentPane.add(btnFindByName, gbc_btnFindByName);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(100);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.fill = GridBagConstraints.BOTH;
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 3;
		gbc_horizontalStrut_3.gridy = 1;
		contentPane.add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		Component verticalStrut_1 = Box.createVerticalStrut(100);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_1.gridx = 2;
		gbc_verticalStrut_1.gridy = 2;
		contentPane.add(verticalStrut_1, gbc_verticalStrut_1);
	}

}
