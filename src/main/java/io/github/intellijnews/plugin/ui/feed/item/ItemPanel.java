package io.github.intellijnews.plugin.ui;

import com.intellij.ide.BrowserUtil;
import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSItem;
import io.github.intellijnews.plugin.ui.util.ComponentCellEditor;
import io.github.intellijnews.plugin.ui.util.ComponentCellRenderer;
import io.github.intellijnews.plugin.ui.util.ComponentTableModel;
import io.github.intellijnews.util.WrapLayout;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ItemPanel extends JPanel {
    private final RSSItem item;

    public ItemPanel(RSSItem item) {
        this.item = item;
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JPanel channelInfo = new JPanel();
        channelInfo.setLayout(new BorderLayout());
        if (item.getChannel().getImage() != null) {
            channelInfo.add(new ImagePanel(item.getChannel().getImage()), BorderLayout.WEST);
        }

        RSSTextPane title = new RSSTextPane(Settings.CHANNEL_NAME, item.getChannel().getTitle(),
                "normal", "bold");
        title.setFont(Settings.CHANNEL_NAME);
        channelInfo.add(title, BorderLayout.CENTER);

        JPanel button = new JPanel();
        button.setLayout(new BorderLayout());
        JLabel label = new JLabel("...", JLabel.CENTER);
        label.setFont(Settings.CHANNEL_NAME);
        label.setPreferredSize(new Dimension(100, 100));
        button.add(label, BorderLayout.CENTER);
        button.setBackground(Settings.ITEM_BACKGROUND);
        channelInfo.add(button, BorderLayout.EAST);
        channelInfo.setBackground(Settings.ITEM_BACKGROUND);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = ChannelInfo.getFrame(item.getChannel(), mainPanel);
                frame.setVisible(true);
            }
        });

        JPanel articleInfo = new JPanel();
        articleInfo.setLayout(new BoxLayout(articleInfo, BoxLayout.X_AXIS));

        boolean addArticle = false;
        if (item.getAuthor() != null) {
            articleInfo.add(new RSSTextPane(Settings.ARTICLE, item.getAuthor(), "normal", "normal"));
            addArticle = true;
        }

        if (item.getCategory().size() > 0) {
            articleInfo.add(new HashTags(item.getCategory()));
            addArticle = true;
        }

        articleInfo.setBackground(Settings.ITEM_BACKGROUND);

        info.add(channelInfo);

        if (addArticle) {
            info.add(articleInfo);
        }

        info.setBackground(Settings.ITEM_BACKGROUND);
        mainPanel.add(info, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(new RSSTextPane(Settings.ARTICLE, item.getTitle(), "normal", "bold"));
        content.add(new RSSTextPane(Settings.ARTICLE, item.getDescription(), "italic", "normal"));
        content.setBackground(Settings.ITEM_BACKGROUND);
        mainPanel.add(content, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.add(new RSSTextPane(Settings.ARTICLE, item.getPubDate().toString(), "normal", "light"),
                BorderLayout.EAST);
        bottom.setBackground(Settings.ITEM_BACKGROUND);

        mainPanel.add(bottom, BorderLayout.SOUTH);
        mainPanel.setBackground(Settings.ITEM_BACKGROUND);

        add(mainPanel);
    }

    static class RSSTextPane extends JEditorPane {
        public RSSTextPane(Font font, String text, String style, String weight) {
            setContentType("text/html");
            setText("<html><body style=\"font-family: " +
                    font.getFamily() + "; " +
                    "font-style: " + style + "; " +
                    "font-size: " + font.getSize() + "pt; " +
                    //"color: white; " +
                    "font-weight: " + weight + "\">" +
                    text +
                    "</body></html>");
            setEditable(false);
            setBackground(Settings.ITEM_BACKGROUND);

            addHyperlinkListener(e -> {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    BrowserUtil.browse(e.getURL());
                }
            });

        }
    }

    static class HashTags extends JPanel {
        public HashTags(List<String> tags) {
            setLayout(new WrapLayout());
            for (String tag : tags) {
                JLabel label = new JLabel("#" + tag);
                label.setFont(Settings.TAG);
                //label.setForeground(Color.white);
                add(label);
            }
            setBackground(Settings.ITEM_BACKGROUND);
        }
    }

    public static class ChannelInfo {
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

        static class WordWrapCellRenderer extends ComponentCellRenderer {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                RSSTextPane pane = (RSSTextPane) value;
                table.setRowHeight(row, Math.max(pane.getPreferredSize().height + 20, 70));
                return pane;
            }
        }

        static class WordWrapCellEditor extends ComponentCellEditor {
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

        static class WordWrapTableModel extends ComponentTableModel {

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
    }

}
