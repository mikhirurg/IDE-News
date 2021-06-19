package io.github.intellijnews.plugin.ui.util;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ComponentCellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component component = (Component) value;
        table.setRowHeight(row, Math.max(component.getPreferredSize().height + 20, 100));
        return component;
    }
}
