package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    public View() {
        try {
            // Устанавливаем внешний вид и поведение нашего приложения
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }

    public void initEditor() {
        htmlTextPane.setContentType("text/html");

        JScrollPane scrollHtmlTextPane = new JScrollPane(htmlTextPane);
        tabbedPane.addTab("HTML", scrollHtmlTextPane);

        JScrollPane scrollPlainTextPane = new JScrollPane(plainTextPane);
        tabbedPane.addTab("Текст", scrollPlainTextPane);

        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));

        tabbedPane.setPreferredSize(new Dimension(250,250));

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Создаем панель меню
        // инициализация панели с помощью класса MenuHelper
        MenuHelper.initFileMenu(this, menuBar);
        MenuHelper.initEditMenu(this, menuBar);
        MenuHelper.initStyleMenu(this, menuBar);
        MenuHelper.initAlignMenu(this, menuBar);
        MenuHelper.initColorMenu(this, menuBar);
        MenuHelper.initFontMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);
        // Добавления меню в верхнюю часть панели
        getContentPane().add(menuBar, BorderLayout.NORTH);

    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        initGui();
        FrameListener listener = new FrameListener(this);
        addWindowListener(listener);
        setVisible(true);
    }

    public UndoListener getUndoListener() { return undoListener; }

    public void selectedTabChanged() {

    }
    // Функция отмены действия
    public void undo() {
        try {
            undoManager.undo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }
    // Функция возврата действия
    public void redo() {
        try {
            undoManager.redo();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

    }

    // Функция которая передает информацию можем ли мы отменить действие
    public boolean canUndo() {
        return undoManager.canUndo();
    }
    // Функция которая передает информацию можем ли мы вернуть действие
    public boolean canRedo() {
        return undoManager.canRedo();
    }
    // Функция сбрасывания всех правок в UndoManager
    public void resetUndo() {
        undoManager.discardAllEdits();
    }

    public void exit() {
        controller.exit();
    }
}
