package io.github.intellijnews.plugin.ui.feed.tab.table_render;

import io.github.intellijnews.plugin.ui.feed.tab.ChannelTab;
import io.github.intellijnews.plugin.ui.util.ComponentCellEditor;

import javax.swing.*;
import java.awt.*;

public class ChannelTabCellEditor extends ComponentCellEditor {
    private ChannelTab component;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {
        component = (ChannelTab) value;
        return component;
    }

    @Override
    public Object getCellEditorValue() {
        return component;
    }
}
