package io.github.intellijnews.plugin.ui.feed;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.ui.components.JBScrollPane;
import io.github.intellijnews.logic.RSSItem;
import io.github.intellijnews.plugin.ui.Application;
import io.github.intellijnews.plugin.ui.Settings;
import io.github.intellijnews.plugin.ui.feed.item.ItemPanel;
import io.github.intellijnews.plugin.ui.util.RSSItemsCellEditor;
import io.github.intellijnews.plugin.ui.util.RSSItemsCellRenderer;
import io.github.intellijnews.plugin.ui.util.RSSItemsTableModel;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractFeed extends JPanel {
    private List<RSSItem> items;
    private RSSItemsTableModel model;
    private final Application application;

    public AbstractFeed(Application application, List<RSSItem> items) {
        this.items = items;
        this.application = application;
        buildGui();
    }

    @SneakyThrows
    private void buildGui() {
        setLayout(new BorderLayout());
        List<Component> data = new LinkedList<>();
        for (RSSItem feedItem : items) {
            data.add(new ItemPanel(feedItem));
        }

        model = new RSSItemsTableModel(data);
        createContent();
    }

    public void setItems(List<RSSItem> items) {
        this.items = items;
        final RSSItemsTableModel[] newModel = new RSSItemsTableModel[1];

        final Task.Backgroundable backgroundTask = new Task.Backgroundable(application.getProject(),
                "Updating feed") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setText("Updating feed");
                newModel[0] = new RSSItemsTableModel(items.stream().map(ItemPanel::new).collect(Collectors.toList()));
                SwingUtilities.invokeLater(() -> {
                    removeAll();
                    model = newModel[0];
                    createContent();
                    validate();
                });
            }
        };
        backgroundTask.queue();
    }

    private void createContent() {
        JTable content = new JTable(model);
        content.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        content.setDefaultRenderer(ItemPanel.class, new RSSItemsCellRenderer());
        content.setDefaultEditor(ItemPanel.class, new RSSItemsCellEditor());
        content.setIntercellSpacing(Settings.CONTENT_INTERCELL_SPACING);
        content.setShowGrid(false);

        JScrollPane scrollPane = new JBScrollPane(content);
        add(scrollPane, BorderLayout.CENTER);
    }

}
