package com.buczkowski.gherkintsrunner;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;

import javax.swing.*;

import static com.buczkowski.gherkintsrunner.GherkinFileEditorManagerListener.FEATURE_REGEX;
import static com.buczkowski.gherkintsrunner.GherkinFileEditorManagerListener.SCENARIO_REGEX;

public class GherkinIconUtils {
    private String fileName;

    public GherkinIconUtils(String fileName) {
        this.fileName = fileName;
    }

    public void removeGherkinRunIcons(Editor rootEditor) {
        RangeHighlighter[] highlighters = rootEditor.getMarkupModel().getAllHighlighters();
        for (RangeHighlighter highlighter : highlighters) {

            if (highlighter.getGutterIconRenderer() instanceof GherkinIconRenderer) {
                rootEditor.getMarkupModel().removeHighlighter(highlighter);
            }
        }
    }

    public void generateGherkinRunIcons(Document rootDocument, Editor rootEditor) {
        for (int i = 0; i < rootDocument.getLineCount(); i++) {
            int startOffset = rootDocument.getLineStartOffset(i);
            int endOffset = rootDocument.getLineEndOffset(i);

            String lineText = rootDocument.getText(new TextRange(startOffset, endOffset)).trim();

            Icon icon;
            if (lineText.matches(SCENARIO_REGEX)) {
                icon = GherkinIconRenderer.SCENARIO_ICON;
            } else if (lineText.matches(FEATURE_REGEX)) {
                icon = GherkinIconRenderer.FEATURE_ICON;
            } else {
                // System.out.println();
                 continue;
            }
            GherkinIconRenderer gherkinIconRenderer = new GherkinIconRenderer(rootEditor.getProject(), fileName);
            gherkinIconRenderer.setLine(i);
            gherkinIconRenderer.setIcon(icon);

            RangeHighlighter rangeHighlighter = createRangeHighlighter(rootDocument, rootEditor, i, i, new TextAttributes());
            rangeHighlighter.setGutterIconRenderer(gherkinIconRenderer);
        }
    }

    private RangeHighlighter createRangeHighlighter(Document document, Editor editor, int fromLine, int toLine, TextAttributes attributes) {
        int lineStartOffset = document.getLineStartOffset(Math.max(0, fromLine));
        int lineEndOffset = document.getLineEndOffset(Math.max(0, toLine));

        return editor.getMarkupModel().addRangeHighlighter(
                lineStartOffset, lineEndOffset, 3333, attributes, HighlighterTargetArea.LINES_IN_RANGE
        );
    }
}
