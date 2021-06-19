package io.github.intellijnews.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import io.github.intellijnews.plugin.ui.Application;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class ApplicationWindowFactory implements ToolWindowFactory {

    @SneakyThrows
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Application application = new Application();
        Content content = factory.createContent(application, "IntelliJNews", false);
        toolWindow.getContentManager().addContent(content);
    }
}
