package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.autovend.software.pojo.ProductDescriptionEntry;

public class FoundProductsTableModel extends AbstractTableModel {
    List<ProductDescriptionEntry> foundProducts;
    String[] columnNames;

    /**
     * Constructor for FoundProductsTableModel
     * @param foundProducts
     * 		The found products
     * @param columnNames
     * 		The column names
     */
    public FoundProductsTableModel(ArrayList<ProductDescriptionEntry> foundProducts, String[] columnNames) {
        this.foundProducts = foundProducts;
        this.columnNames = columnNames;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int findColumn(String columnName) {
        return super.findColumn(columnName);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Character.class;
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    @Override
    public void fireTableStructureChanged() {
        super.fireTableStructureChanged();
    }

    @Override
    public void fireTableRowsInserted(int firstRow, int lastRow) {
        super.fireTableRowsInserted(firstRow, lastRow);
    }

    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        super.fireTableRowsUpdated(firstRow, lastRow);
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }

    @Override
    public void fireTableChanged(TableModelEvent e) {
        super.fireTableChanged(e);
    }

    @Override
    public int getRowCount() {
        return foundProducts.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductDescriptionEntry product = foundProducts.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getCode();
            case 1:
                return product.getDescriptiption();
            case 2:
                return product.getType();
        }
        return null;
    }


}
