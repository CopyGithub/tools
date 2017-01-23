package com.apitest;

import javax.swing.JTextArea;

class ComponentOperation {

    /**
     * 删除一行</br>
     * 删除两个{@code \n}之间的内容
     * 
     * @param component
     */
    protected static void delLineOfJTextArea(JTextArea component) {
        int cur = component.getCaretPosition();
        String requestText = component.getText();
        int preN = requestText.substring(0, cur).lastIndexOf("\n");
        int endN = requestText.substring(cur, requestText.length()).indexOf("\n");
        component.replaceRange("", preN == -1 ? 0 : preN + 1,
                endN == -1 ? requestText.length() : endN + 1 + cur);
    }
}