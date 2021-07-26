package io.github.intellijnews.plugin.ui.feed.channel_list;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.plugin.ui.Application;
import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.plugin.ui.feed.tab.ChannelTab;
import io.github.intellijnews.plugin.ui.feed.tab.table_render.ChannelTabCellEditor;
import io.github.intellijnews.plugin.ui.feed.tab.table_render.ChannelTabCellRenderer;
import io.github.intellijnews.plugin.ui.feed.tab.table_render.ChannelTabTableModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelList extends JPanel {

    private RSSContainer container;
    private ChannelTabTableModel model;
    private final Application application;

    public ChannelList(Application application, RSSContainer container) {
        this.container = container;
        this.application = application;
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());
        List<Component> channelTabs = new LinkedList<>();
        for (RSSChannel channel : container.getChannels()) {
            ChannelTab channelTab = new ChannelTab(application, channel);
            channelTabs.add(channelTab);
        }

        model = new ChannelTabTableModel(channelTabs);
        createContent();
    }

    public void setItems(String title, RSSContainer container) {
        this.container = container;
        final ChannelTabTableModel[] newModel = new ChannelTabTableModel[1];
        final Task.Backgroundable backgroundTask = new Task.Backgroundable(application.getProject(),
                title) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setText(title);
                newModel[0] = new ChannelTabTableModel(container.getChannels().stream()
                        .map(e -> new ChannelTab(application, e)).collect(Collectors.toList()));
                SwingUtilities.invokeLater(() -> {
                    removeAll();
                    model = newModel[0];
                    createContent();
                    validate();
                });
            }
        };
        backgroundTask.queue();
        /*SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                newModel[0] = new ChannelTabTableModel(container.getChannels().stream()
                        .map(e -> new ChannelTab(application, e)).collect(Collectors.toList()));
                return true;
            }

            @Override
            protected void done() {
                removeAll();
                model = newModel[0];
                createContent();
                validate();
            }
        };
        worker.execute();*/
    }

    private void createContent() {
        JTable content = new JTable(model);
        content.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        content.setDefaultRenderer(ChannelTab.class, new ChannelTabCellRenderer());
        content.setDefaultEditor(ChannelTab.class, new ChannelTabCellEditor());
        content.setIntercellSpacing(new Dimension(20, 20));
        content.setShowGrid(false);
        JScrollPane scrollPane = new JScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static class ChannelListMenu extends JPopupMenu {
        public ChannelListMenu(Application application, RSSChannel channel) {
            JMenuItem unsubscribe = new JMenuItem("Unsubscribe");
            JMenuItem openInWeb = new JMenuItem("Open in Browser");
            add(unsubscribe);
            add(openInWeb);

            unsubscribe.addActionListener(e -> {
                Settings.STORED_DATA.channels.remove(channel.getRssUrl());
                application.updateData("Unsubscribing channel");
                Settings.saveChannels();
            });

            openInWeb.addActionListener(e -> BrowserUtil.browse(channel.getLink()));
        }
    }

}
