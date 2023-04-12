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
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.autovend.products.BarcodedProduct;
import com.autovend.products.PLUCodedProduct;
import com.autovend.software.pojo.ProductDescriptionEntry;

import data.DatabaseController;
import models.FoundProductsTableModel;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class dlgSearchProduct extends JDialog {
	String[] columnNames = {"Code", "Description"};
	public final JPanel contentPanel = new JPanel();
	public String selectedItemCode;
	public char selectedItemType;
	private JTextField txKeyword;
	private JTable table1;

	public dlgSearchProduct(JFrame owner, String title) {
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
			JButton btnFind = new JButton("Find");
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String keyword = txKeyword.getText();
			        List<Object> products = DatabaseController.findProductByDescription(keyword);

			        ArrayList<ProductDescriptionEntry> foundProducts = new ArrayList<>();
			        for (Object product: products) {
			            if (product instanceof BarcodedProduct) {
			                foundProducts.add(new ProductDescriptionEntry(((BarcodedProduct)product).getDescription(), 'B', ((BarcodedProduct)product).getBarcode()));
			            } else if (product instanceof PLUCodedProduct) {
			                foundProducts.add(new ProductDescriptionEntry(((PLUCodedProduct)product).getDescription(), 'P', ((PLUCodedProduct)product).getPLUCode()));
			            }
			        }
			        FoundProductsTableModel model = new FoundProductsTableModel(foundProducts, columnNames);
			        table1.setModel(model);
			        table1.clearSelection();
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
				        dlgSearchProduct.this.setVisible(false);
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

				        dlgSearchProduct.this.setVisible(false);
				        dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
