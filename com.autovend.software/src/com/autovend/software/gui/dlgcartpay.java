package com.autovend.software.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.pojo.ProductDescriptionEntry;

import data.DatabaseController;
import models.FoundProductsTableModel;

public class dlgcartpay extends JDialog {
		String[] columnNames = {"Code", "Description"};
		public final JPanel contentPanel = new JPanel();
		public String selectedItemCode;
		public char selectedItemType;
		private JTextField txKeyword;
		private JTable table1;

		public dlgcartpay(JFrame owner, String title, boolean pay) {
			setBounds(100, 100, 450, 300);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
			gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
			gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
			{
				table1 = new JTable();
				GridBagConstraints gbc_table1 = new GridBagConstraints();
				gbc_table1.gridwidth = 2;
				gbc_table1.insets = new Insets(0, 0, 0, 5);
				gbc_table1.fill = GridBagConstraints.BOTH;
				gbc_table1.gridx = 0;
				gbc_table1.gridy = 1;
				DefaultTableModel model = new DefaultTableModel();
		        table1.setModel(model);
		        table1.clearSelection();
				contentPanel.add(table1, gbc_table1);
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int selectedRow = table1.getSelectedRow();
					        if (selectedRow < 0) return;
					        selectedItemCode = table1.getModel().getValueAt(table1.getSelectedRow(), 0).toString();
					        selectedItemType = (char)table1.getModel().getValueAt(table1.getSelectedRow(), 2);
					        dlgcartpay.this.setVisible(false);
					        dispose();
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							selectedItemCode = null;

					        dlgcartpay.this.setVisible(false);
					        dispose();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		}

	}

