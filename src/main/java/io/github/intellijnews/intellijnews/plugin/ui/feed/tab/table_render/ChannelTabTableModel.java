package io.github.intellijnews.plugin.ui.feed.tab.table_render;

import io.github.intellijnews.plugin.ui.feed.tab.ChannelTab;
import io.github.intellijnews.plugin.ui.util.ComponentTableModel;

import java.awt.*;
import java.util.List;

public class ChannelTabTableModel extends ComponentTableModel {
    public ChannelTabTableModel(List<Component> items) {
        super(items, ChannelTab.class);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "Channels";
    }

}