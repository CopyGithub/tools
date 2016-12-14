package com.cashreward;

public class Param {

    enum ParamType {
        INT, STRING
    };

    String mName;
    String mValue;

    String mExpression;
    ParamType mType;

    public Param(ParamType type) {
        mType = type;
    }

    public int toInt() {
        return Integer.valueOf(mValue);
    }

    @Override
    public String toString() {
        return mValue;
    }
}
