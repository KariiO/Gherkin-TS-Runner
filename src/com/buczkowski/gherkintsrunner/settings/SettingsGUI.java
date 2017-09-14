package com.buczkowski.gherkintsrunner.settings;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;

import static com.buczkowski.gherkintsrunner.settings.Config.DEFAULT_FEATURE_REGEX;
import static com.buczkowski.gherkintsrunner.settings.Config.DEFAULT_SCENARIO_REGEX;

public class SettingsGUI {
    private JTextField featureRegexTextField;
    private JTextField scenarioRegexTextField;
    private JPanel rootPanel;
    private JTextField protractorCmdTextField;
    private JTextField protractorConfigJsTextField;
    private JButton cmdPathButton;
    private JButton configPathButton;
    private JTextField featuresDirPathTextField;
    private JButton configFeaturesDirButton;
    private Config mConfig;

    JPanel getRootPanel() {
        return rootPanel;
    }

    void createUI(Project project) {
        mConfig = Config.getInstance(project);
        featureRegexTextField.setText(mConfig.getFeatureRegex());
        scenarioRegexTextField.setText(mConfig.getScenarioRegex());
        protractorCmdTextField.setText(mConfig.getProtractorCmdPath());
        protractorConfigJsTextField.setText(mConfig.getProtractorConfigJsPath());
        featuresDirPathTextField.setText(mConfig.getFeaturesDir());

        cmdPathButton.addActionListener(e -> protractorCmdTextField.setText(openFileChooser(project, true)));
        configPathButton.addActionListener(e -> protractorConfigJsTextField.setText(openFileChooser(project, true)));
        configFeaturesDirButton.addActionListener(e -> featuresDirPathTextField.setText(openFileChooser(project, false)));
    }

    private String openFileChooser(Project project, boolean isFile) {
        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(isFile, !isFile, false, false, false, false);
        VirtualFile file = FileChooser.chooseFile(fileChooserDescriptor, getRootPanel(),project, null);

        return file == null ? "" : file.getPath();
    }

    boolean isModified() {
        boolean modified;
        modified = !featureRegexTextField.getText().equals(mConfig.getFeatureRegex());
        modified |= !scenarioRegexTextField.getText().equals(mConfig.getScenarioRegex());
        modified |= !protractorCmdTextField.getText().equals(mConfig.getProtractorCmdPath());
        modified |= !protractorConfigJsTextField.getText().equals(mConfig.getProtractorConfigJsPath());
        modified |= !featuresDirPathTextField.getText().equals(mConfig.getFeaturesDir());
        return modified;
    }

    void apply() {
        if(featureRegexTextField.getText().isEmpty())
            featureRegexTextField.setText(DEFAULT_FEATURE_REGEX);

        if(scenarioRegexTextField.getText().isEmpty())
            scenarioRegexTextField.setText(DEFAULT_SCENARIO_REGEX);

        mConfig.setFeatureRegex(featureRegexTextField.getText());
        mConfig.setScenarioRegex(scenarioRegexTextField.getText());
        mConfig.setProtractorCmdPath(protractorCmdTextField.getText());
        mConfig.setProtractorConfigJsPath(protractorConfigJsTextField.getText());
        mConfig.setFeaturesDir(featuresDirPathTextField.getText());
    }

    void reset() {
        featureRegexTextField.setText(mConfig.getFeatureRegex());
        scenarioRegexTextField.setText(mConfig.getScenarioRegex());
        protractorCmdTextField.setText(mConfig.getProtractorCmdPath());
        protractorConfigJsTextField.setText(mConfig.getProtractorConfigJsPath());
        featuresDirPathTextField.setText(mConfig.getFeaturesDir());
    }
}
