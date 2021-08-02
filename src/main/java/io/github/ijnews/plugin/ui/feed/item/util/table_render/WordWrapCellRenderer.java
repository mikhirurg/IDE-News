package io.github.ijnews.plugin.ui.feed.item.util.table_render;

import io.github.ijnews.plugin.ui.Settings;
import io.github.ijnews.plugin.ui.feed.item.util.RSSTextPane;
import io.github.ijnews.plugin.ui.util.ComponentCellRenderer;

import javax.swing.*;
import java.awt.*;

public class WordWrapCellRenderer extends ComponentCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        RSSTextPane pane = (RSSTextPane) value;
        table.setRowHeight(row, Math.max(pane.getPreferredSize().height + Settings.TABLE_ROW_SPACING,
                Settings.TABLE_ROW_MAX_HEIGHT));
        return pane;
    }
}
