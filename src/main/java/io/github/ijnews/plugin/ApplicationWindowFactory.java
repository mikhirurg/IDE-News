package io.github.ijnews.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.ijnews.plugin.ui.Application;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class ApplicationWindowFactory implements ToolWindowFactory {

    @SneakyThrows
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Application application = new Application(project);
        Content content = factory.createContent(application, "IJ-News", false);
        toolWindow.getContentManager().addContent(content);
    }
}
