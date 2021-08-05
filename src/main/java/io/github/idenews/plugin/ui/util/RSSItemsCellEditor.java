package io.github.idenews.plugin.ui.util;

import io.github.idenews.plugin.ui.feed.item.ItemPanel;

import javax.swing.*;
import java.awt.*;

public class RSSItemsCellEditor extends ComponentCellEditor {

    private ItemPanel panel;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel = (ItemPanel) value;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return panel;
    }
}
