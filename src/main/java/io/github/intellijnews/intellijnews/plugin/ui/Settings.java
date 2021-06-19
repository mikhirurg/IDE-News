package io.github.intellijnews.plugin.ui;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@State(name = "Channels", storages = {@Storage("RSSChannels.xml")})
public class Settings implements PersistentStateComponent<Settings.State> {
    //public static Supplier<Color> ITEM_BACKGROUND = UIUtil::getButtonSelectColor;
    public static Color ITEM_BACKGROUND = new Color(185, 207, 230);
    public static Font CHANNEL_NAME = new Font("Arial", Font.BOLD + Font.ITALIC, 15);
    public static Font TAG = new Font("Arial", Font.BOLD, 13);
    public static Font ARTICLE = new Font("Arial", Font.PLAIN, 12);
    public static Font INFO = new Font("Arial", Font.PLAIN, 13);

    public static State STORED_DATA;

    @Nullable
    @Override
    public State getState() {
        return STORED_DATA;
    }

    @Override
    public void loadState(@NotNull State state) {
        STORED_DATA = state;
    }


    public static class State {
        public static Set<String> channels = new HashSet<>();
    }
}
