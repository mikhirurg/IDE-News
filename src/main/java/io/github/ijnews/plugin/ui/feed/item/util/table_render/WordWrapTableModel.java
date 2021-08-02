package io.github.ijnews.plugin.ui.feed.item.util.table_render;

import io.github.ijnews.plugin.ui.feed.item.util.RSSTextPane;
import io.github.ijnews.plugin.ui.util.ComponentTableModel;

public class WordWrapTableModel extends ComponentTableModel {

    private final RSSTextPane[][] data;
    private final String[] headers;

    public WordWrapTableModel(RSSTextPane[][] data, String[] headers) {
        super(null, RSSTextPane.class);
        this.data = data;
        this.headers = headers;
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }

    @Override
    public int getRowCount() {
        return (data == null) ? 0 : data.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (data == null) ? null : data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int columnIndex, int rowIndex) {
        return false;
    }
}
