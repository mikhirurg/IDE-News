package io.github.intellijnews.plugin.ui;

import com.intellij.ide.BrowserUtil;
import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.plugin.ui.util.ComponentCellEditor;
import io.github.intellijnews.plugin.ui.util.ComponentTableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelList extends JPanel {

    private RSSContainer container;
    private ChannelTab.ChannelTabTableModel model;
    private Application application;

    public ChannelList(Application application, RSSContainer container) {
        this.container = container;
        this.application = application;
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());
        List<Component> channelTabs = new LinkedList<>();
        for (RSSChannel channel : container.getChannels()) {
            ChannelTab channelTab = new ChannelTab(channel);
            ChannelListMenu channelListMenu = new ChannelListMenu(channel);
            channelTab.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    showPopup(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    showPopup(e);
                }

                private void showPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        channelListMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
            channelTab.setComponentPopupMenu(channelListMenu);
            channelTabs.add(channelTab);
        }

        model = new ChannelTab.ChannelTabTableModel(channelTabs);
        JTable content = new JTable(model);
        content.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        content.setDefaultRenderer(ChannelTab.class, new ChannelTab.ChannelTabCellRenderer());
        content.setDefaultEditor(ChannelTab.class, new ChannelTab.ChannelTabCellEditor());
        content.setIntercellSpacing(new Dimension(20, 20));
        content.setShowGrid(false);

        JScrollPane scrollPane = new JScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setItems(RSSContainer container) {
        this.container = container;
        SwingWorker<Boolean, Void> swingWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                model.setItems(container.getChannels().stream().map(ChannelTab::new).collect(Collectors.toList()));
                return true;
            }

            @Override
            protected void done() {
                super.done();
                model.fireTableDataChanged();
            }
        };
        swingWorker.execute();
    }

    public class ChannelListMenu extends JPopupMenu {
        public ChannelListMenu(RSSChannel channel) {
            JMenuItem unsubscribe = new JMenuItem("Unsubscribe");
            JMenuItem openInWeb = new JMenuItem("Open in Web");
            add(unsubscribe);
            add(openInWeb);

            unsubscribe.addActionListener(e -> {
                container.getChannels().remove(channel);
                application.updateData();
            });

            openInWeb.addActionListener(e -> BrowserUtil.browse(channel.getLink()));
        }
    }

    public static class ChannelTab extends JPanel {
        private final RSSChannel channel;

        public ChannelTab(RSSChannel channel) {
            this.channel = channel;
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
            if (channel.getImage() != null) {
                channelInfo.add(new ImagePanel(channel.getImage()), BorderLayout.WEST);
            }

            ItemPanel.RSSTextPane title = new ItemPanel.RSSTextPane(Settings.CHANNEL_NAME, channel.getTitle(),
                    "normal", "bold");
            title.setFont(Settings.CHANNEL_NAME);
            channelInfo.add(title, BorderLayout.NORTH);
            ItemPanel.RSSTextPane channelDescription = new ItemPanel.RSSTextPane(Settings.CHANNEL_NAME,
                    channel.getDescription(), "italic", "normal");
            channelInfo.add(channelDescription, BorderLayout.CENTER);

            info.add(channelInfo);
            mainPanel.add(info, BorderLayout.CENTER);

            add(mainPanel, BorderLayout.CENTER);
        }

        private static class ChannelTabCellEditor extends ComponentCellEditor {
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

        private static class ChannelTabCellRenderer extends JPanel implements TableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                ChannelTab component = (ChannelTab) value;
                table.setRowHeight(row, Math.max(component.getPreferredSize().height + 20, 100));
                return component;
            }
        }

        private static class ChannelTabTableModel extends ComponentTableModel {
            public ChannelTabTableModel(List<Component> items) {
                super(items, ChannelTab.class);
            }

            @Override
            public String getColumnName(int columnIndex) {
                return "Channels";
            }

        }
    }
}
