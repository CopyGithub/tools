package com.cc.android.tools;

import java.util.ArrayList;

class LogManage {
    class LogInfo {
        private String time;
        private boolean error;
        private String message;
    }

    private static ArrayList<LogInfo> mMessages = null;

    protected static void append(String message) {
        // TODO
    }

    protected static void append(ArrayList<String> message) {
        // TODO
    }

    protected static ArrayList<LogInfo> show() {
        // TODO
        return mMessages;
    }

    protected static void clear() {
        // TODO
    }
}