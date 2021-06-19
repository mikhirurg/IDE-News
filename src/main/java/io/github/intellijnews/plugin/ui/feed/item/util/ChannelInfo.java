package io.github.intellijnews.plugin.ui.feed.item.util;

import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.plugin.ui.feed.item.util.RSSTextPane;
import io.github.intellijnews.plugin.ui.feed.item.util.table_render.WordWrapCellEditor;
import io.github.intellijnews.plugin.ui.feed.item.util.table_render.WordWrapCellRenderer;
import io.github.intellijnews.plugin.ui.feed.item.util.table_render.WordWrapTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChannelInfo {
    private static Map<RSSChannel, JFrame> FRAMES = new HashMap<>();

    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;

    public static JFrame getFrame(RSSChannel channel, Component component) {
        if (FRAMES.containsKey(channel)) {
            return FRAMES.get(channel);
        } else {
            JFrame frame = buildGui(channel, component);
            FRAMES.put(channel, frame);
            return frame;
        }
    }

    private static JFrame buildGui(RSSChannel channel, Component component) {
        JFrame frame = new JFrame(channel.getTitle());
        frame.setLocationRelativeTo(component);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {
                "Name",
                "Data"
        };

        List<String[]> list = new LinkedList<>();

        if (channel.getLink() != null) {
            list.add(new String[]{"Title: ", channel.getTitle()});
        }
        if (channel.getDescription() != null) {
            list.add(new String[]{"Description: ", channel.getDescription()});
        }
        if (channel.getLanguage() != null) {
            list.add(new String[]{"Language: ", channel.getLanguage()});
        }
        if (channel.getRating() != null) {
            list.add(new String[]{"Rating", channel.getRating()});
        }
        if (channel.getCopyright() != null) {
            list.add(new String[]{"Copyright", channel.getCopyright()});
        }
        if (channel.getPubDate() != null) {
            list.add(new String[]{"Publication date", channel.getPubDate().toString()});
        }
        if (channel.getLastBuildDate() != null) {
            list.add(new String[]{"Last build date", channel.getLastBuildDate().toString()});
        }
        if (channel.getCategory() != null) {
            list.add(new String[]{"Categories", String.join(", ", channel.getCategory())});
        }
        if (channel.getDocs() != null) {
            list.add(new String[]{"Docs", channel.getDocs()});
        }
        if (channel.getManagingEditor() != null) {
            list.add(new String[]{"Managing editor", channel.getManagingEditor()});
        }
        if (channel.getWebMaster() != null) {
            list.add(new String[]{"Webmaster", channel.getWebMaster()});
        }

        RSSTextPane[][] data = convertToWrapAreas(list);
        JTable content = new JTable(new WordWrapTableModel(data, columnNames));
        content.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        content.setDefaultRenderer(RSSTextPane.class, new WordWrapCellRenderer());
        content.setDefaultEditor(JTextArea.class, new WordWrapCellEditor());
        content.setBackground(Settings.ITEM_BACKGROUND);
        content.setShowGrid(false);

        panel.add(content, BorderLayout.CENTER);

        frame.add(new JScrollPane(panel));
        frame.pack();

        return frame;
    }

    private static RSSTextPane[][] convertToWrapAreas(List<String[]> data) {
        RSSTextPane[][] areas = new RSSTextPane[data.size()][data.get(0).length];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).length; j++) {
                areas[i][j] = new RSSTextPane(Settings.ARTICLE, data.get(i)[j], "normal", "normal");
            }
        }
        return areas;
    }

}
