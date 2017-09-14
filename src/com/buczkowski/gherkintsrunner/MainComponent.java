package com.buczkowski.gherkintsrunner;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;


public class MainComponent implements ApplicationComponent {
    MessageBusConnection connection;
    @Override
    public void initComponent() {
        MessageBus bus = ApplicationManager.getApplication().getMessageBus();
        connection = bus.connect();

        ProjectManager.getInstance().addProjectManagerListener(new ProjectManagerListener() {
            @Override
            public void projectOpened(Project project) {
                connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new GherkinFileEditorManagerListener(project));
            }
        });

    }

    @Override
    public void disposeComponent() {
        connection.disconnect();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Gherkin TS Runner";
    }
}
