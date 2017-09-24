package com.buczkowski.gherkintsrunner.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;

import javax.swing.*;

public class SettingsGUI {
    private JTextField featureRegexTextField;
    private JTextField scenarioRegexTextField;
    private JPanel rootPanel;
    private TextFieldWithBrowseButton protractorCmdTextField;
    private TextFieldWithBrowseButton protractorConfigJsTextField;
    private TextFieldWithBrowseButton featuresDirPathTextField;

    private Config mConfig;

    JPanel getRootPanel() {
        return rootPanel;
    }

    void createUI(Project project) {
        mConfig = Config.getInstance(project);

        if(mConfig == null)
            return;

        featureRegexTextField.setText(mConfig.getFeatureRegex());
        scenarioRegexTextField.setText(mConfig.getScenarioRegex());
        protractorCmdTextField.setText(mConfig.getProtractorCmdPath());
        protractorConfigJsTextField.setText(mConfig.getProtractorConfigJsPath());
        featuresDirPathTextField.setText(mConfig.getFeaturesDirPath());

        protractorCmdTextField.addBrowseFolderListener(
                "Select Your Protractor Cmd File",
                "Specifies the protractor cmd file that command will be executed on", null,
                FileChooserDescriptorFactory.createSingleFileDescriptor("cmd"));

        protractorConfigJsTextField.addBrowseFolderListener(
                "Select Your Protractor Config File",
                "Specifies the protractor config file that will be passed to execution command", null,
                FileChooserDescriptorFactory.createSingleFileDescriptor("js"));

        featuresDirPathTextField.addBrowseFolderListener(
                "Select Your Features Directory",
                "Specifies the directory that contains all files with .feature extension", null,
                FileChooserDescriptorFactory.createSingleFolderDescriptor());
    }

    boolean isModified() {
        boolean modified;
        modified = !featureRegexTextField.getText().equals(mConfig.getFeatureRegex());
        modified |= !scenarioRegexTextField.getText().equals(mConfig.getScenarioRegex());
        modified |= !protractorCmdTextField.getText().equals(mConfig.getProtractorCmdPath());
        modified |= !protractorConfigJsTextField.getText().equals(mConfig.getProtractorConfigJsPath());
        modified |= !featuresDirPathTextField.getText().equals(mConfig.getFeaturesDirPath());
        return modified;
    }

    void apply() {
        mConfig.setFeatureRegex(featureRegexTextField.getText());
        mConfig.setScenarioRegex(scenarioRegexTextField.getText());
        mConfig.setProtractorCmdPath(protractorCmdTextField.getText());
        mConfig.setProtractorConfigJsPath(protractorConfigJsTextField.getText());
        mConfig.setFeaturesDirPath(featuresDirPathTextField.getText());
    }

    void reset() {
        featureRegexTextField.setText(mConfig.getFeatureRegex());
        scenarioRegexTextField.setText(mConfig.getScenarioRegex());
        protractorCmdTextField.setText(mConfig.getProtractorCmdPath());
        protractorConfigJsTextField.setText(mConfig.getProtractorConfigJsPath());
        featuresDirPathTextField.setText(mConfig.getFeaturesDirPath());
    }
}
