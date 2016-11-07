package com.cc.apitest;

import java.io.File;

import javax.swing.tree.DefaultTreeCellRenderer;

class MyTreeCell extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;
    private File file = null;

    protected File getFile() {
        return file;
    }

    protected void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return getText();
    }
}
