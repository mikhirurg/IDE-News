package io.github.intellijnews.plugin.ui;

import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.parser.Parser;
import io.github.intellijnews.plugin.ui.feed.FeedPanel;
import io.github.intellijnews.plugin.ui.feed.channel_list.ChannelList;
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
    private final Parser parser = new Parser();

    public Application() throws ParserConfigurationException {
        container = getContainer();
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());

        JTabbedPane pane = new JTabbedPane();

        feed = new FeedPanel(container);
        channelList = new ChannelList(this, container);

        JPanel feedPanel = new JPanel();
        feedPanel.setLayout(new BorderLayout());
        feedPanel.add(feed, BorderLayout.CENTER);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> updateData());
        feedPanel.add(refresh, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add(channelList, BorderLayout.CENTER);
        JButton add = new JButton("+");
        add.addActionListener(e -> {
            String url = JOptionPane.showInputDialog("Enter RSS Channel URL: ");
            Settings.STORED_DATA.channels.add(url);
            Settings.saveChannels();
            updateData();
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

    public void updateData() {
        container = getContainer();
        feed.setItems(FeedPanel.getFeedItems(container));
        channelList.setItems(container);
    }

    public static void main(String[] args) {

    }

}
