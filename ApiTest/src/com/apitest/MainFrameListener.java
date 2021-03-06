package com.apitest;

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
        if (KeyEvent.VK_CONTROL == e.getKeyCode()) {
            ctrl = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (KeyEvent.VK_CONTROL == keyCode) {
            ctrl = true;
        } else if (KeyEvent.VK_D == keyCode) {
            if (ctrl) {
                ComponentOperation.delLineOfJTextArea((JTextArea) e.getComponent());
            }
        }
    }
}