package io.github.intellijnews.plugin.ui.feed.tab.table_render;

import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.plugin.ui.feed.tab.ChannelTab;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ChannelTabCellRenderer extends JPanel implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        ChannelTab component = (ChannelTab) value;
        table.setRowHeight(row, Math.max(component.getPreferredSize().height + Settings.TABLE_ROW_SPACING,
                Settings.CHANNEL_TAB_MAX_HEIGHT));
        return component;
    }
}
