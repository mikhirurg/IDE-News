package io.github.ijnews.plugin.ui.feed;

import io.github.ijnews.logic.RSSChannel;
import io.github.ijnews.logic.RSSContainer;
import io.github.ijnews.logic.RSSItem;
import io.github.ijnews.parser.Parser;
import io.github.ijnews.plugin.ui.Application;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FeedPanel extends AbstractFeed {

    public FeedPanel(Application application, RSSContainer rssContainer) {
        super(application, getFeedItems(rssContainer));
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Parser parser = new Parser();
        FeedPanel feedPanel = new FeedPanel(null,
                RSSContainer.builder()
                        .channels(List.of(
                                parser.parse("https://rss.nytimes.com/services/xml/rss/nyt/US.xml"),
                                parser.parse("https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml"),
                                parser.parse("https://www.fontanka.ru/fontanka.rss"),
                                parser.parse("https://meduza.io/rss2/all"),
                                parser.parse("https://tvrain.ru/export/rss/all.xml"),
                                parser.parse("http://feeds.bbci.co.uk/news/england/rss.xml")
                        ))
                        .build()
        );

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setLayout(new BorderLayout());
            frame.add(feedPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public static List<RSSItem> getFeedItems(RSSContainer rssContainer) {
        return rssContainer.getChannels()
                .stream()
                .map(RSSChannel::getItems)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(RSSItem::getPubDate).reversed())
                .collect(Collectors.toList());
    }
}
