package com.buczkowski.gherkintsrunner.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(name="Gherkin TS Runner", storages = {@Storage("GherkinTSRunner.xml")})
public class Config implements PersistentStateComponent<Config> {
    static final String DEFAULT_SCENARIO_REGEX = "Scenario:([a-zA-Z ]+)$";
    static final String DEFAULT_FEATURE_REGEX = "Feature:([a-zA-Z ]+)$";

    public String scenarioRegex;
    public String featureRegex;
    public String protractorCmdPath;
    public String protractorConfigJsPath;
    public String featuresDir;

    @Nullable
    @Override
    public Config getState() {
        return this;
    }

    @Override
    public void loadState(Config gherkinConfig) {
        // System.out.println("loadState");
        XmlSerializerUtil.copyBean(gherkinConfig, this);
    }

    @Nullable
    public static Config getInstance(Project project) {
        return ServiceManager.getService(project, Config.class);
    }

    public boolean isConfigFilled() {
        return !scenarioRegex.isEmpty()
                && !featureRegex.isEmpty()
                && !protractorCmdPath.isEmpty()
                && !protractorConfigJsPath.isEmpty()
                && !featuresDir.isEmpty();
    }

    public String getScenarioRegex() {
        return scenarioRegex;
    }

    public String getFeatureRegex() {
        return featureRegex;
    }

    public String getProtractorCmdPath() {
        return protractorCmdPath;
    }

    public String getProtractorConfigJsPath() {
        return protractorConfigJsPath;
    }

    public String getFeaturesDir() {
        return featuresDir;
    }

    public void setScenarioRegex(String scenarioRegex) {
        this.scenarioRegex = scenarioRegex;
    }

    public void setFeatureRegex(String featureRegex) {
        this.featureRegex = featureRegex;
    }

    public void setProtractorCmdPath(String protractorCmdPath) {
        this.protractorCmdPath = protractorCmdPath;
    }

    public void setProtractorConfigJsPath(String protractorConfigJsPath) {
        this.protractorConfigJsPath = protractorConfigJsPath;
    }

    public void setFeaturesDir(String featuresDir) {
        this.featuresDir = featuresDir;
    }
}
