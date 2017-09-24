package com.buczkowski.gherkintsrunner.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GherkinConfigurable implements SearchableConfigurable {
    private SettingsGUI settingsGUI;
    private final Project project;

    public GherkinConfigurable(@NotNull Project project) {
        this.project = project;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsGUI = new SettingsGUI();
        settingsGUI.createUI(project);
        return settingsGUI.getRootPanel();
    }

    @Override
    public void disposeUIResources() {
        settingsGUI = null;
    }


    @NotNull
    @Override
    public String getId() {
        return "settings.gherkinrunner";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Gherkin TS Runner Settings";
    }

    @Override
    public boolean isModified() {
        return settingsGUI.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        settingsGUI.apply();
    }

    @Override
    public void reset() {
        settingsGUI.reset();
    }
}
