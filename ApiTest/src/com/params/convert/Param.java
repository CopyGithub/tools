package com.params.convert;

public class Param {

    enum ParamType {
        INT, STRING
    };

    String mName;
    String mValue;

    String mExpression;
    ParamType mType;

    public Param(ParamType type, String expression) {
        mExpression = expression;
        mType = type;
        parseValue();
    }

    public Param(ParamType type) {
        mType = type;
    }

    public void setExpression(String expression) {
        mExpression = expression;
        parseValue();
    }

    void parseValue() {
        int position = mExpression.indexOf(": ");
        mName = mExpression.substring(0, position);
        mValue = mExpression.substring(position + 2, mExpression.length());
    }

    public int toInt() {
        return Integer.valueOf(mValue);
    }

    @Override
    public String toString() {
        return mValue;
    }
}
