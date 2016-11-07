package com.cc.apitest;

import javax.swing.JTextArea;

class ComponentOperation {

    protected static void delLineOfJTextArea(JTextArea component) {
        int cur = component.getCaretPosition();
        String requestText = component.getText();
        int preN = requestText.substring(0, cur).lastIndexOf("\n");
        int endN = requestText.substring(cur, requestText.length()).indexOf("\n");
        component.replaceRange("", preN == -1 ? 0 : preN,
                endN == -1 ? requestText.length() : endN + cur);
    }
}