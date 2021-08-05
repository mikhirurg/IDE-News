package io.github.idenews.plugin.ui.feed.item.util.table_render;

import io.github.idenews.plugin.ui.feed.item.util.RSSTextPane;
import io.github.idenews.plugin.ui.util.ComponentCellEditor;

import javax.swing.*;
import java.awt.*;

public class WordWrapCellEditor extends ComponentCellEditor {
    private RSSTextPane component;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        RSSTextPane area = (RSSTextPane) value;
        component = area;
        return area;
    }

    @Override
    public Object getCellEditorValue() {
        return component;
    }
}
