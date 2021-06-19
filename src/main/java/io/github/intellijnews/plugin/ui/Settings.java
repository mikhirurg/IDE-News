package io.github.intellijnews.plugin.ui;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

public class Settings {
    public static Color ITEM_BACKGROUND = new Color(185, 207, 230);
    public static Font CHANNEL_NAME = new Font("Arial", Font.BOLD + Font.ITALIC, 15);
    public static Font TAG = new Font("Arial", Font.BOLD, 13);
    public static Font ARTICLE = new Font("Arial", Font.PLAIN, 12);
    public static Font INFO = new Font("Arial", Font.PLAIN, 13);

    public static State STORED_DATA;

    static {
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
