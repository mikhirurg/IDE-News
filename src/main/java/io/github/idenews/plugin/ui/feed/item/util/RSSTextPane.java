package io.github.idenews.plugin.ui.feed.item.util;

import com.intellij.ide.BrowserUtil;
import io.github.idenews.plugin.ui.Settings;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

public class RSSTextPane extends JEditorPane {
    public RSSTextPane(Font font, String text, String style, String weight) {
        setContentType("text/html");
        setText("<html><body style=\"font-family: " +
                font.getFamily() + "; " +
                "font-style: " + style + "; " +
                "font-size: " + font.getSize() + "pt; " +
                "font-weight: " + weight + "; " +
                "color: " + "rgb(" + Settings.FONT_COLOR.getRed() + ", " +
                Settings.FONT_COLOR.getGreen() + ", " +
                Settings.FONT_COLOR.getBlue() + ");background:#3c3f41;background-color:transparent \">"+
//                "\">"+
                text +
                "</body></html>");
        setEditable(false);
        setBackground(Color.getColor("#3c3f41"));

        addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                BrowserUtil.browse(e.getURL());
            }
        });

    }
}
