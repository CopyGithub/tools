package com.apitest;

import javax.swing.JTextArea;

class ComponentOperation {

    protected static void delLineOfJTextArea(JTextArea component) {
        int cur = component.getCaretPosition();
        String requestText = component.getText();
        int preN = requestText.substring(0, cur).lastIndexOf("\n");
        int endN = requestText.substring(cur, requestText.length()).indexOf("\n");
        component.replaceRange("", preN == -1 ? 0 : preN + 1,
                endN == -1 ? requestText.length() : endN + 1 + cur);
    }
}