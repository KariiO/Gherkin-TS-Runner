package com.buczkowski.gherkintsrunner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GherkinRunner implements ProgramRunner {
    @NotNull
    @Override
    public String getRunnerId() {
        return "GherkinRunner";
    }

    @Override
    public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
        return true;
    }

    @Nullable
    @Override
    public RunnerSettings createConfigurationData(ConfigurationInfoProvider configurationInfoProvider) {
        return null;
    }

    @Override
    public void checkConfiguration(RunnerSettings runnerSettings, @Nullable ConfigurationPerRunnerSettings configurationPerRunnerSettings) throws RuntimeConfigurationException {

    }

    @Override
    public void onProcessStarted(RunnerSettings runnerSettings, ExecutionResult executionResult) {

    }

    @Nullable
    @Override
    public SettingsEditor getSettingsEditor(Executor executor, RunConfiguration runConfiguration) {
        return null;
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {

    }

    @Override
    public void execute(@NotNull ExecutionEnvironment executionEnvironment, @Nullable Callback callback) throws ExecutionException {

    }
}
