package io.github.idenews.plugin.ui.util;

import io.github.idenews.plugin.ui.feed.item.ItemPanel;

import java.awt.*;
import java.util.List;

public class RSSItemsTableModel extends ComponentTableModel {

    public RSSItemsTableModel(List<Component> items) {
        super(items, ItemPanel.class);
    }

}
