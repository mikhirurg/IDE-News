package io.github.intellijnews.plugin.ui.util;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class ComponentTableModel extends AbstractTableModel {
    private List<Component> items;

    private final Class columnClass;

    public ComponentTableModel(List<Component> items, Class columnClass) {
        this.items = items;
        this.columnClass = columnClass;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "Feed";
    }

    @Override
    public int getRowCount() {
        return (items == null) ? 0 : items.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (items == null) ? null : items.get(rowIndex);
    }

    @Override
    public boolean isCellEditable(int columnIndex, int rowIndex) {
        return true;
    }

    public void setItems(List<Component> items) {
        this.items = items;
    }
}
