package com.javarush.task.task32.task3209.listeners;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

// Класс следит за правками которые можно вернуть или отменить
public class UndoListener implements UndoableEditListener {

    private UndoManager undoManager;
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        undoManager.addEdit(e.getEdit());
    }

    public UndoListener(UndoManager undoManager) {
        this.undoManager = undoManager;
    }
}
