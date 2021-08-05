package io.github.idenews.plugin.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Settings {
    public static Color ITEM_BACKGROUND;
    public static Color FONT_COLOR;

    public static Font CHANNEL_NAME = new Font("Arial", Font.BOLD + Font.ITALIC, 15);
    public static Font TAG = new Font("Arial", Font.BOLD, 13);
    public static Font ARTICLE = new Font("Arial", Font.PLAIN, 12);
    public static Font INFO = new Font("Arial", Font.PLAIN, 13);

    public static Dimension CONTENT_INTERCELL_SPACING = new Dimension(20, 20);

    public static int TABLE_ROW_SPACING = 20;
    public static int TABLE_ROW_MAX_HEIGHT = 20;
    public static int CHANNEL_TAB_MAX_HEIGHT = 20;

    public static int WORKER_THREADS_NUM = 20;
    public static ExecutorService workerService = Executors.newFixedThreadPool(WORKER_THREADS_NUM);


    public static State STORED_DATA;

    private enum LAF {
        DARCULA,
        INTELLIJ,
        OTHER
    }

    private static LAF checkLaF() {
        String laf = UIManager.getLookAndFeel().toString();

        if (laf.contains("com.intellij.ide.ui.laf.IntelliJLaf")) {
            return LAF.INTELLIJ;
        } else if (laf.contains("com.intellij.ide.ui.laf.darcula.DarculaLaf")) {
            return LAF.DARCULA;
        }

        return LAF.OTHER;
    }

    static {

        LAF laf = checkLaF();

        switch (laf) {
            case INTELLIJ:
            case OTHER:
                ITEM_BACKGROUND = new Color(185, 207, 230);
                FONT_COLOR = Color.BLACK;
                break;
            case DARCULA:
                ITEM_BACKGROUND = new Color(149, 149, 149);
                FONT_COLOR = Color.WHITE;
                break;
        }

        STORED_DATA = new State();
        String dir = System.getProperty("user.home");
        Path path = Path.of(dir).resolve("RSSReader/channels");
        if (Files.exists(path)) {
            try {
                STORED_DATA.load(new FileInputStream(path.toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createDirectory(Path.of(dir).resolve("RSSReader"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveChannels() {
        String dir = System.getProperty("user.home");
        Path path = Path.of(dir).resolve("RSSReader/channels");

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(Path.of(dir).resolve("RSSReader"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            STORED_DATA.save(new FileOutputStream(path.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class State {
        public Set<String> channels = new LinkedHashSet<>();

        public void load(InputStream stream) throws IOException {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    channels.add(line);
                }
            }

        }

        public void save(OutputStream outputStream) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                for (String line : channels) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }
}
