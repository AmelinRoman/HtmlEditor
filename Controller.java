package com.javarush.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.Option;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public void init() {
        createNewDocument();
    }

    public void exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);

        view.setController(controller);
        view.init();
        controller.init();
    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("Новый документ");
        currentFile = null;
    }
    public void openDocument() {
        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());

        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());
            try (FileReader reader = new FileReader(currentFile)) {
                new HTMLEditorKit().read(reader, document, 0);
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
            view.resetUndo();
        }
    }
    public void saveDocument() {
        if (currentFile == null) {
            saveDocumentAs();
        } else if (currentFile != null){
            view.selectHtmlTab();
            try (FileWriter writer = new FileWriter(currentFile)) {
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }
    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());
        if (chooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();

            view.setTitle(currentFile.getName());

            try (FileWriter writer = new FileWriter(currentFile)) {
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }


    public HTMLDocument getDocument() {
        return document;
    }

    public void setPlainText(String text) {
        resetDocument();
        try {
            new HTMLEditorKit().read(new StringReader(text), document, 0);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText() {
        StringWriter writer = new StringWriter();
        try {
            new HTMLEditorKit().write(writer, document, 0, document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return writer.toString();
    }

    public void resetDocument() {
        if (document != null) { document.removeUndoableEditListener(view.getUndoListener()); }
            document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
            document.addUndoableEditListener(view.getUndoListener());
            view.update();
    }
}
