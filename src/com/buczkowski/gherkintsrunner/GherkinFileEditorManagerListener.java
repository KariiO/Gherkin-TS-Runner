package com.buczkowski.gherkintsrunner;

import com.buczkowski.gherkintsrunner.settings.Config;
import com.buczkowski.gherkintsrunner.settings.GherkinDocumentListener;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GherkinFileEditorManagerListener implements FileEditorManagerListener {
    public static String SCENARIO_REGEX;
    public static String FEATURE_REGEX;
    List<VirtualFile> gherkinFiles = new ArrayList<>();
    List<VirtualFile> openedGherkinFiles = new ArrayList<>();
    private Config config;

    public GherkinFileEditorManagerListener(Project project) {
        config = Config.getInstance(project);

        if(config == null) {
            return;
        }

        if(!config.isConfigFilled()) {
            PluginManager.getLogger().warn("Gherkin TS Runner require to fill every setting to work properly!");
            return;
        }

        SCENARIO_REGEX = config.getScenarioRegex();
        FEATURE_REGEX = config.getFeatureRegex();
    }

    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        if(!config.isConfigFilled()) {
            PluginManager.getLogger().warn("Gherkin TS Runner require to fill every setting to work properly!");
            return;
        }

        VirtualFile oldFile = event.getOldFile();
        VirtualFile newFile = event.getNewFile();
        String oldFileName = "No_old_file";
        String newFileName = "No_new_file";

        if (oldFile != null) {
            oldFileName = oldFile.getName();
        }
        if (newFile != null) {
            newFileName = newFile.getName();
        }

        // System.out.println("[Selection Changed] Old file: " + oldFileName + " -> New file: " + newFileName);

        if (newFile == null) {
            return;
        }

        String newFileExtension = newFile.getExtension();

        if (!Objects.equals(newFileExtension, "feature")) {
            return;
        }

        if (gherkinFiles.stream().noneMatch(f -> f.equals(newFile))) {
            gherkinFiles.add(newFile);
        }

        // System.out.println("Gherkin files:");
/*        for (VirtualFile f : gherkinFiles) {
            System.out.println(f.getName());
        }*/

        Editor rootEditor = event.getManager().getSelectedTextEditor();

        if (rootEditor == null) {
            return;
        }

        Document rootDocument = rootEditor.getDocument();

        if (openedGherkinFiles.stream().noneMatch(f -> f.equals(newFile))) {
            // System.out.println("[First time open] File: " + newFileName);
            openedGherkinFiles.add(newFile);
            GherkinIconUtils gherkinIconUtils = new GherkinIconUtils(newFileName);
            gherkinIconUtils.generateGherkinRunIcons(rootDocument, rootEditor);
        }

        rootDocument.addDocumentListener(new GherkinDocumentListener(newFileName, rootEditor));
    }

}
