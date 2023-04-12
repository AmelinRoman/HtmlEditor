package com.javarush.task.task32.task3209;

import javax.swing.text.html.HTMLDocument;
import java.io.File;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;

    }

    public void init() {

    }

    public static void main(String[] args) {
        View newView = new View();
        Controller newController = new Controller(newView);
        newView.setController(newController);
        newView.init();
        newController.init();
    }

    public void exit() {
        System.exit(0);
    }
}
