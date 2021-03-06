package com.buczkowski.gherkintsrunner;

import com.buczkowski.gherkintsrunner.settings.Config;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GherkinIconRenderer extends GutterIconRenderer {
    public static final Icon SCENARIO_ICON = AllIcons.RunConfigurations.TestState.Run;
    public static final Icon FEATURE_ICON = AllIcons.RunConfigurations.TestState.Run_run;

    private Project project;
    private Icon icon;
    private String fileName;
    private int line;

    public GherkinIconRenderer(Project project, String fileName) {
        this.project = project;
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GherkinIconRenderer)) {
            return false;
        }

        GherkinIconRenderer gherkinIconRenderer = (GherkinIconRenderer) o;
        return gherkinIconRenderer.icon.equals(icon) && gherkinIconRenderer.line == line;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + icon.hashCode();
        result = 31 * result + line;
        return result;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return icon;
    }

    @Nullable
    public AnAction getClickAction() {
        return new AnAction() {
            @Override
            public void actionPerformed(AnActionEvent e) {
                callProtractor();
            }
        };
    }

    @Override
    public boolean isNavigateAction() {
        return true;
    }

    public String getTooltipText() {
        return "Run " + (icon == SCENARIO_ICON ? "Scenario" : "Feature");
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setLine(int i) {
        line = i;
    }

    private GeneralCommandLine getProtractorRunCommand(@NotNull Config config) {
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath(config.getProtractorCmdPath());
        commandLine.setWorkDirectory(project.getBasePath());
        commandLine.addParameter(config.getProtractorConfigJsPath());

        StringBuilder specArg = new StringBuilder().append("--specs=").append(config.getFeaturesDirPath()).append("/").append(fileName);
        if(icon == SCENARIO_ICON) {
            specArg.append(":").append(line + 1);
        }

        commandLine.addParameter(specArg.toString());

        return commandLine;
    }

    private void callProtractor() {
        try {
            Config config = Config.getInstance(project);

            if (config == null) {
                return;
            }

            GeneralCommandLine command = getProtractorRunCommand(config);
            Process p = command.createProcess();

            if (project != null) {
                ToolWindowManager manager = ToolWindowManager.getInstance(project);
                String id = "Gherkin Runner";
                TextConsoleBuilderFactory factory = TextConsoleBuilderFactory.getInstance();
                TextConsoleBuilder builder = factory.createBuilder(project);
                ConsoleView view = builder.getConsole();

                ColoredProcessHandler handler = new ColoredProcessHandler(p, command.getPreparedCommandLine());
                handler.startNotify();
                view.attachToProcess(handler);

                ToolWindow window = manager.getToolWindow(id);
                Icon cucumberIcon = IconLoader.findIcon("/resources/icons/cucumber.png");

                if (window == null) {
                    window = manager.registerToolWindow(id, true, ToolWindowAnchor.BOTTOM);
                    window.setIcon(cucumberIcon);
                }

                ContentFactory cf = window.getContentManager().getFactory();
                Content c = cf.createContent(view.getComponent(), "Run " + (window.getContentManager().getContentCount() + 1), true);

                window.getContentManager().addContent(c);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
