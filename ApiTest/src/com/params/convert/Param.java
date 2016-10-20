package com.params.convert;

public class Param{
    
    enum ParamType{INT, STRING};
    
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
    
    public void setExpression(String expression){
        mExpression = expression;
        parseValue();
    }
    
    void parseValue() {
        if(mType ==  ParamType.INT) {
            int position = mExpression.indexOf(": ");
            mName = mExpression.substring(0, position);
            mValue = mExpression.substring(position + 2, mExpression.length()); 
        } else if(mType ==  ParamType.STRING) {
            int position = mExpression.indexOf(": \"");
            mName = mExpression.substring(0, position);
            mValue = mExpression.substring(position + 3, mExpression.length() - 1); 
        }
    }
    
    public int toInt() {
        return Integer.valueOf(mValue);
    }
    
    public String toString() {
        return mValue;
    }
} 
