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

import com.autovend.PriceLookUpCode;
import com.autovend.external.ProductDatabases;
import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.products.Product;
import com.autovend.software.pojo.ProductDescriptionEntry;
import com.autovend.software.utils.CodeUtils;

import data.DatabaseController;
import models.FoundProductsTableModel;

public class dlgPLUProduct extends JDialog {
	String[] columnNames = {"Code", "Description"};
	public final JPanel contentPanel = new JPanel();
	public String selectedItemCode;
	public char selectedItemType;
	private JTextField txKeyword;
	private JTable table1;
	AddItemsPanel itempanel;
	
	public dlgPLUProduct(JFrame owner, String title, AddItemsPanel itempanel) {
		this.itempanel = itempanel;
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
			txKeyword = new JTextField();
			GridBagConstraints gbc_txKeyword = new GridBagConstraints();
			gbc_txKeyword.insets = new Insets(0, 0, 5, 5);
			gbc_txKeyword.fill = GridBagConstraints.HORIZONTAL;
			gbc_txKeyword.gridx = 0;
			gbc_txKeyword.gridy = 0;
			contentPanel.add(txKeyword, gbc_txKeyword);
			txKeyword.setColumns(10);
		}
		{
			JButton btnFind = new JButton("Search");
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String keyword = txKeyword.getText();
					try {
						
					PriceLookUpCode PLUcode = CodeUtils.stringPLUToPLU(keyword);
					Product search = DatabaseController.getProduct(PLUcode, 'P');
					if (search == null) {
						System.out.println("No item of this PLU could be found.");
				        ArrayList<ProductDescriptionEntry> foundProducts = new ArrayList<>();
				        if (search instanceof PLUCodedProduct) {
				        	foundProducts.add(new ProductDescriptionEntry(((PLUCodedProduct)search).getDescription(), 'P', ((PLUCodedProduct)search).getPLUCode()));
				        }
				        FoundProductsTableModel model = new FoundProductsTableModel(foundProducts, columnNames);
				        table1.setModel(model);
				        table1.clearSelection();
					}
					if (search instanceof PLUCodedProduct) {
						System.out.println("Found item"+".");
						PLUCodedProduct item = ProductDatabases.PLU_PRODUCT_DATABASE.get(PLUcode);
						if (!itempanel.cart.contains(item)) {
							itempanel.cart.add(item);
							int countindex = itempanel.cart.indexOf(item);
							itempanel.cartcount.add(countindex, 1);
						}
						else {
							int countindex = itempanel.cart.indexOf(item);
							int count = itempanel.cartcount.get(countindex);
							count++;
							itempanel.cartcount.remove(countindex);
							itempanel.cartcount.add(countindex, count);
						}
			        }
					}
					catch (Exception ex){
						Product search = null;
						System.out.println(itempanel.cart.contains(search));
						System.out.println("Invalid PLU.");
					}
					
					/**
			        List<Object> products = DatabaseController.findProductByDescription(keyword);

			        for (Object product: products) {
			            if (product instanceof BarcodedProduct) {
			                foundProducts.add(new ProductDescriptionEntry(((BarcodedProduct)product).getDescription(), 'B', ((BarcodedProduct)product).getBarcode()));
			            } else if (product instanceof PLUCodedProduct) {
			                foundProducts.add(new ProductDescriptionEntry(((PLUCodedProduct)product).getDescription(), 'P', ((PLUCodedProduct)product).getPLUCode()));
			            }
			        }
			        			        */
				}
			});
			GridBagConstraints gbc_btnFind = new GridBagConstraints();
			gbc_btnFind.insets = new Insets(0, 0, 5, 0);
			gbc_btnFind.gridx = 1;
			gbc_btnFind.gridy = 0;
			contentPanel.add(btnFind, gbc_btnFind);
		}
		{
			table1 = new JTable();
			GridBagConstraints gbc_table1 = new GridBagConstraints();
			gbc_table1.gridwidth = 2;
			gbc_table1.insets = new Insets(0, 0, 0, 5);
			gbc_table1.fill = GridBagConstraints.BOTH;
			gbc_table1.gridx = 0;
			gbc_table1.gridy = 1;
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
				        dlgPLUProduct.this.setVisible(false);
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
						for (int i = 0; i < itempanel.cart.size(); i++) {
							PLUCodedProduct plu = (PLUCodedProduct) itempanel.cart.get(i);
							System.out.println(plu.getDescription() + " " + itempanel.cartcount.get(i));
						}
				        dlgPLUProduct.this.setVisible(false);
				        dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}

}
