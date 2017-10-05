package com.buczkowski.gherkintsrunner;

import com.buczkowski.gherkintsrunner.settings.Config;
import com.buczkowski.gherkintsrunner.settings.GherkinDocumentListener;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GherkinFileEditorManagerListener implements FileEditorManagerListener {
    public static String SCENARIO_REGEX;
    public static String FEATURE_REGEX;
    private Config config;
    private List<String> openedGherkinFiles = new ArrayList<>();
    private List<GherkinDocumentListener> documentListeners = new ArrayList<>();

    public GherkinFileEditorManagerListener(Project project) {
        config = Config.getInstance(project);

        if (config == null)
            return;

        SCENARIO_REGEX = config.getScenarioRegex();
        FEATURE_REGEX = config.getFeatureRegex();
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        if (!isFeatureFile(file))
            return;

        String pathOldFile = getRelativePath(file.getPath());
        if (openedGherkinFiles.stream().anyMatch(f -> f.equals(pathOldFile))) {
            openedGherkinFiles.remove(pathOldFile);

            Optional<GherkinDocumentListener> optionalListener = documentListeners.stream().filter(l -> Objects.equals(l.getFileName(), pathOldFile)).findAny();
            optionalListener.ifPresent(gherkinDocumentListener -> documentListeners.remove(gherkinDocumentListener));
        }
    }

    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        VirtualFile fileNew = event.getNewFile();
        Editor rootEditor = event.getManager().getSelectedTextEditor();
        if (rootEditor == null) {
            return;
        }

        Document rootDocument = rootEditor.getDocument();
        if (fileNew != null) {
            if (!isFeatureFile(fileNew))
                return;

            String pathNewFile = getRelativePath(fileNew.getPath());

            if (openedGherkinFiles.stream().noneMatch(f -> f.equals(pathNewFile))) {
                openedGherkinFiles.add(pathNewFile);

                GherkinIconUtils gherkinIconUtils = new GherkinIconUtils(pathNewFile);
                gherkinIconUtils.generateGherkinRunIcons(rootDocument, rootEditor);

                GherkinDocumentListener listener = new GherkinDocumentListener(pathNewFile, rootEditor);
                rootDocument.addDocumentListener(listener);

                documentListeners.add(listener);
            }
        }
    }

    private String getRelativePath(String absolutePath) {
        String base = config.getFeaturesDirPath();

        return new File(base).toURI().relativize(new File(absolutePath).toURI()).getPath();
    }

    private boolean isFeatureFile(VirtualFile file) {
        return Objects.equals(file.getExtension(), "feature");
    }

}
