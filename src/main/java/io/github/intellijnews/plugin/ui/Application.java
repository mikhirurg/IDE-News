package io.github.intellijnews.plugin.ui;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.parser.Parser;
import io.github.intellijnews.plugin.ui.feed.FeedPanel;
import io.github.intellijnews.plugin.ui.feed.channel_list.ChannelList;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class Application extends JPanel {
    private FeedPanel feed;
    private ChannelList channelList;
    private RSSContainer container;
    private final Project project;
    private final Parser parser = new Parser();
    private boolean isUpdating = false;

    public Application(@NotNull Project project) throws ParserConfigurationException {
        this.project = project;
        final Task.Backgroundable backgroundTask = new Task.Backgroundable(project,
                "Starting application") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                container = getContainer();
                SwingUtilities.invokeLater(() -> {
                    removeAll();
                    buildGui();
                    validate();
                });
            }
        };
        backgroundTask.queue();
    }

    private void buildGui() {
        setLayout(new BorderLayout());

        JTabbedPane pane = new JTabbedPane();

        feed = new FeedPanel(this, container);
        channelList = new ChannelList(this, container);

        JPanel feedPanel = new JPanel();
        feedPanel.setLayout(new BorderLayout());
        feedPanel.add(feed, BorderLayout.CENTER);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> {
            if (!isUpdating) {
                updateData("Updating data");
            }
        });
        feedPanel.add(refresh, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add(channelList, BorderLayout.CENTER);
        JButton add = new JButton("+");
        add.addActionListener(e -> {
            String url = JOptionPane.showInputDialog("Enter RSS Channel URL: ");
            if (url != null) {
                Settings.STORED_DATA.channels.add(url);
                Settings.saveChannels();
                updateData("Subscribing channel");
            }
        });
        listPanel.add(add, BorderLayout.NORTH);

        pane.addTab("Feed", feedPanel);
        pane.addTab("Channels", listPanel);
        add(pane, BorderLayout.CENTER);
    }

    public RSSContainer getContainer() {
        return RSSContainer.builder()
                .channels(Settings.STORED_DATA.channels.stream()
                        .map(channel -> {
                            try {
                                RSSChannel rssChannel = parser.parse(channel);
                                if (rssChannel == null) {
                                    return null;
                                }
                                if (rssChannel.getItems().size() > 0) {
                                    rssChannel.getItems().removeIf(item -> item.getPubDate() == null);
                                }
                                return rssChannel;
                            } catch (IOException | SAXException ignored) {
                                // TODO: add logging
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public void updateData(String title) {
        isUpdating = true;
        container = getContainer();
        feed.setItems(FeedPanel.getFeedItems(container));
        channelList.setItems(title, container);
        isUpdating = false;
    }

    public static void main(String[] args) {

    }

    public Project getProject() {
        return project;
    }
}
