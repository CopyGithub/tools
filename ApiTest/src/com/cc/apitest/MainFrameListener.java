package com.cc.apitest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;

class MainFrameListener implements MouseListener, KeyListener {
    private boolean ctrl = false;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (17 == e.getKeyCode()) {
            ctrl = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (17 == keyCode) {
            ctrl = true;
        } else if (68 == keyCode) {
            if (ctrl) {
                ComponentOperation.delLineOfJTextArea((JTextArea) e.getComponent());
            }
        }
    }
}