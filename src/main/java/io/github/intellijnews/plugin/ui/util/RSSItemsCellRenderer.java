package io.github.intellijnews.plugin.ui.util;

import io.github.intellijnews.plugin.ui.feed.item.ItemPanel;
import io.github.intellijnews.plugin.ui.util.ComponentCellRenderer;

import javax.swing.*;
import java.awt.*;

public class RSSItemsCellRenderer extends ComponentCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        ItemPanel panel = (ItemPanel) value;
        table.setRowHeight(row, Math.max(panel.getPreferredSize().height + 20, 100));
        return panel;
    }
}
