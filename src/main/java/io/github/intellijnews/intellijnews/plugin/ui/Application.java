package io.github.intellijnews.plugin.ui;

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
    private FeedPanel feedPanel;
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

        feedPanel = new FeedPanel(this, container);
        channelList = new ChannelList(this, container);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(channelList, BorderLayout.CENTER);

        JButton add = new JButton("+");
        add.addActionListener(e -> {
            String url = JOptionPane.showInputDialog("Enter RSS Channel URL: ");
            Settings.State.channels.add(url);

            updateData();
        });

        panel.add(add, BorderLayout.NORTH);

        pane.addTab("FEED", feedPanel);
        pane.addTab("CHANNELS", panel);
        add(pane, BorderLayout.CENTER);
    }

    public RSSContainer getContainer() {
        return RSSContainer.builder()
                .channels(Settings.State.channels.stream()
                        .map(channel -> {
                            try {
                                return parser.parse(channel);
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
        feedPanel.setItems(FeedPanel.getFeedItems(container));
        channelList.setItems(container);
    }

    public static void main(String[] args) {

    }

}
