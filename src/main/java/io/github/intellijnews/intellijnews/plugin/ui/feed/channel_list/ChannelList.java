package io.github.intellijnews.plugin.ui.feed.channel_list;

import com.intellij.ide.BrowserUtil;
import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.plugin.ui.Application;
import io.github.intellijnews.plugin.ui.feed.tab.ChannelTab;
import io.github.intellijnews.plugin.ui.feed.tab.table_render.ChannelTabCellEditor;
import io.github.intellijnews.plugin.ui.feed.tab.table_render.ChannelTabCellRenderer;
import io.github.intellijnews.plugin.ui.feed.tab.table_render.ChannelTabTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelList extends JPanel {

    private RSSContainer container;
    private ChannelTabTableModel model;
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
            ChannelTab channelTab = new ChannelTab(application, container, channel);
            channelTabs.add(channelTab);
        }

        model = new ChannelTabTableModel(channelTabs);
        JTable content = new JTable(model);
        content.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        content.setDefaultRenderer(ChannelTab.class, new ChannelTabCellRenderer());
        content.setDefaultEditor(ChannelTab.class, new ChannelTabCellEditor());
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
                model.setItems(container.getChannels().stream().map(e -> new ChannelTab(application, container, e)).collect(Collectors.toList()));
                return true;
            }

            @Override
            protected void done() {
                super.done();
                SwingUtilities.invokeLater(() -> {
                    model.fireTableDataChanged();
                    application.validate();
                });
            }
        };
        swingWorker.execute();
    }

    public static class ChannelListMenu extends JPopupMenu {
        public ChannelListMenu(Application application, RSSContainer container, RSSChannel channel) {
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

}
