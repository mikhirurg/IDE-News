package io.github.intellijnews.plugin.ui.util;

import io.github.intellijnews.plugin.ui.feed.item.ItemPanel;
import io.github.intellijnews.plugin.ui.util.ComponentTableModel;

import java.awt.*;
import java.util.List;

public class RSSItemsTableModel extends ComponentTableModel {

    public RSSItemsTableModel(List<Component> items) {
        super(items, ItemPanel.class);
    }

}
