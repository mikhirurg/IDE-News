package io.github.idenews.plugin.ui.feed.item.util;

import io.github.idenews.plugin.ui.Settings;
import io.github.idenews.util.WrapLayout;

import javax.swing.*;
import java.util.List;

public class HashTags extends JPanel {
    public HashTags(List<String> tags) {
        setLayout(new WrapLayout());
        for (String tag : tags) {
            JLabel label = new JLabel("#" + tag);
            label.setFont(Settings.TAG);
            label.setForeground(Settings.FONT_COLOR);
            add(label);
        }
//        setBackground(Settings.ITEM_BACKGROUND);
    }
}
