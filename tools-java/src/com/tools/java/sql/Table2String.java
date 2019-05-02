package com.tools.java.sql;

import java.lang.reflect.Field;

public class Table2String {
    private String split = "\t";

    public String toFieldName(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        String toString = "";
        for (Field field : fields) {
            toString += String.format("%s%s", field.getName(), split);
        }
        return toString.substring(0, toString.length() - split.length()) + "\n";
    }

    public String toString(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        String toString = "";
        for (Field field : fields) {
            field.setAccessible(true);
            toString += String.format("%s%s", field.get(object), split);
        }
        return toString.substring(0, toString.length() - split.length()) + "\n";
    }
}
