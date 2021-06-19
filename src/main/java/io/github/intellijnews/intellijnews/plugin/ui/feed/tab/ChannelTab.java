package io.github.intellijnews.plugin.ui.feed.tab;

import io.github.intellijnews.logic.RSSChannel;
import io.github.intellijnews.logic.RSSContainer;
import io.github.intellijnews.plugin.ui.Application;
import io.github.intellijnews.plugin.ui.ImagePanel;
import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.plugin.ui.feed.channel_list.ChannelList;
import io.github.intellijnews.plugin.ui.feed.item.util.RSSTextPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChannelTab extends JPanel {
    private final RSSChannel channel;
    private Application application;
    private RSSContainer container;

    public ChannelTab(Application application, RSSContainer container, RSSChannel channel) {
        this.application = application;
        this.container = container;
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

        RSSTextPane title = new RSSTextPane(Settings.CHANNEL_NAME, channel.getTitle(),
                "normal", "bold");
        title.setFont(Settings.CHANNEL_NAME);
        channelInfo.add(title, BorderLayout.NORTH);
        RSSTextPane channelDescription = new RSSTextPane(Settings.CHANNEL_NAME,
                channel.getDescription(), "italic", "normal");
        channelInfo.add(channelDescription, BorderLayout.CENTER);

        info.add(channelInfo);
        mainPanel.add(info, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        ChannelList.ChannelListMenu channelListMenu = new ChannelList.ChannelListMenu(application, container, channel);
        addMouseListener(new MouseAdapter() {
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
        setComponentPopupMenu(channelListMenu);
    }

}
