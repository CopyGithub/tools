package com.cc.apitest;

import java.io.File;

import javax.swing.tree.DefaultTreeCellRenderer;

class MyTreeCell extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;
    private File mFile = null;

    protected File getFile() {
        return mFile;
    }

    protected void setFile(File file) {
        mFile = file;
    }

    @Override
    public String toString() {
        return getText();
    }
}
