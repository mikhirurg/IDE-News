package io.github.intellijnews.plugin.ui.feed.item;

import io.github.intellijnews.logic.RSSItem;
import io.github.intellijnews.plugin.ui.util.ImagePanel;
import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.plugin.ui.feed.item.util.ChannelInfo;
import io.github.intellijnews.plugin.ui.feed.item.util.HashTags;
import io.github.intellijnews.plugin.ui.feed.item.util.RSSTextPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        channelInfo.add(new ImagePanel(item.getChannel().getImage()), BorderLayout.WEST);

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

}
