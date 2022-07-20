package io.github.idenews.plugin.ui.util;

import io.github.idenews.logic.RSSImage;
import io.github.idenews.plugin.ui.Settings;
import io.github.idenews.plugin.ui.feed.FeedPanel;
import io.github.idenews.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImagePanel extends JPanel {

    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 100;

    private static Image NO_IMAGE_ICON;

    static {
        try {
            BufferedImage image = ImageIO.read(FeedPanel.class.getResourceAsStream("/img/NO_IMAGE.jpg"));
            Pair<Double, Double> bounds = getBounds(image.getWidth(), image.getHeight());
            NO_IMAGE_ICON = image.getScaledInstance(
                    (int) bounds.getFirst().doubleValue(),
                    (int) bounds.getSecond().doubleValue(),
                    Image.SCALE_DEFAULT
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final RSSImage image;
    private Image img;
    private int startX;
    private int startY;

    public ImagePanel(RSSImage image) {
        this.image = image;

        Settings.workerService.submit(() -> {
            if (image != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new URL(image.getUrl()));
                    Pair<Double, Double> bounds = getBounds(bufferedImage.getWidth(), bufferedImage.getHeight());
                    img = bufferedImage.getScaledInstance(
                            (int) bounds.getFirst().doubleValue(),
                            (int) bounds.getSecond().doubleValue(),
                            Image.SCALE_SMOOTH
                    );
                } catch (IOException | NullPointerException ignored) {
                    img = NO_IMAGE_ICON;
                }
            } else {
                img = NO_IMAGE_ICON;
            }
            startX = IMAGE_WIDTH / 2 - img.getWidth(null) / 2;
            startY = IMAGE_HEIGHT / 2 - img.getHeight(null) / 2;

            img = cutImage(img);
            SwingUtilities.invokeLater(() -> {
                removeAll();
                buildGui();
                validate();
            });
        });
    }

    private void buildGui() {
        setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        if (image != null) {
            setToolTipText(image.getDescription());
        }
    }

    private static Pair<Double, Double> getBounds(int imageWidth, int imageHeight) {
        double a = (double) imageWidth / IMAGE_WIDTH;
        double b = (double) imageHeight / IMAGE_HEIGHT;

        if (a > b) {
            return new Pair<>(imageWidth / a, imageHeight / a);
        } else {
            return new Pair<>(imageWidth / b, imageHeight / b);
        }
    }

    private BufferedImage cutImage(Image image) {
        BufferedImage newImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Graphics2D g2d = (Graphics2D) newImage.getGraphics();
        g2d.setRenderingHints(hints);
        Color color = g2d.getColor();
        g2d.setColor(Color.white);
        g2d.fill(new Rectangle(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT));
        g2d.setColor(color);

        Area outer = new Area(new Rectangle(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT));
        Ellipse2D.Double inner = new Ellipse2D.Double(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        outer.subtract(new Area(inner));
        g2d.drawImage(image, startX, startY, null);

        color = g2d.getColor();
//        g2d.setColor(Settings.ITEM_BACKGROUND);
        g2d.fill(outer);
        g2d.setColor(color);
        return newImage;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, null);
    }
}
