package io.github.intellijnews.plugin.ui.feed.item.util;

import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.util.WrapLayout;

import javax.swing.*;
import java.util.List;

public class HashTags extends JPanel {
    public HashTags(List<String> tags) {
        setLayout(new WrapLayout());
        for (String tag : tags) {
            JLabel label = new JLabel("#" + tag);
            label.setFont(Settings.TAG);
            add(label);
        }
        setBackground(Settings.ITEM_BACKGROUND);
    }
}
