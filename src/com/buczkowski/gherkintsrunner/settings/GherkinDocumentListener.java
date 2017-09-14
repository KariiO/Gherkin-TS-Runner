package com.buczkowski.gherkintsrunner.settings;

import com.buczkowski.gherkintsrunner.GherkinIconUtils;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;

public class GherkinDocumentListener implements DocumentListener {
    private String fileName;
    private Editor editor;
    private GherkinIconUtils gherkinIconUtils;

    public GherkinDocumentListener(String fileName, Editor editor) {
        this.fileName = fileName;
        this.editor = editor;
        this.gherkinIconUtils = new GherkinIconUtils(fileName);
    }

    @Override
    public void beforeDocumentChange(DocumentEvent documentEvent) {
    }

    @Override
    public void documentChanged(DocumentEvent documentEvent) {
        try {
            // System.out.println("[Document Changed] " + fileName);
            // Remove old run icons before add new ones
            gherkinIconUtils.removeGherkinRunIcons(editor);
            // Generate new run icons
            gherkinIconUtils.generateGherkinRunIcons(documentEvent.getDocument(), editor);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
