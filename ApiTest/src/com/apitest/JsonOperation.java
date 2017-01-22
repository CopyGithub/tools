package com.apitest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class JsonOperation {

    /**
     * 对一个疑似json的String进行排序和格式化，如果不是json，则原样返回
     * 
     * @param string
     * @return
     */
    protected static String sortJs(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            return toString(jsonObject, 0);
        } catch (JSONException e) {
            return string;
        }
    }

    /**
     * 对json进行排序后输出格式化的内容
     * 
     * @param json
     * @param indentFactor
     *            文本整体缩进的空格数*4
     * @return
     */
    private static String toString(JSONObject json, int indentFactor) {
        String result = "";
        ArrayList<String> keys = new ArrayList<>(json.keySet());
        Collections.sort(keys);
        int length = keys.size();
        if (length == 0) {
            return "{}";
        }
        result += "{\n";
        int i;
        for (i = 0; i < length; i++) {
            String key = keys.get(i);
            Object value = json.get(key);
            result += space(indentFactor + 1) + quote(key) + " : ";
            result += toString(value, indentFactor + 1);
            result += (i < length - 1) ? "," : "";
            result += "\n";
        }
        result += space(indentFactor) + "}";
        return result;
    }

    /**
     * 判断值类型来转化成{@link String}
     * 
     * @param value
     * @param indentFactor
     *            文本整体缩进的空格数*4
     * @return
     */
    private static String toString(Object value, int indentFactor) {
        String result = "";
        if (value == null || value.equals(null)) {
            result += "null";
        } else if (value instanceof JSONString) {
            Object o;
            try {
                o = ((JSONString) value).toJSONString();
            } catch (Exception e) {
                throw new JSONException(e);
            }
            result += o != null ? o.toString() : quote(value.toString());
        } else if (value instanceof Number || value instanceof Boolean) {
            result += String.valueOf(value);
        } else if (value instanceof Enum<?>) {
            result += quote(((Enum<?>) value).name());
        } else if (value instanceof JSONObject) {
            result += toString((JSONObject) value, indentFactor);
        } else if (value instanceof JSONArray) {
            result += toString((JSONArray) value, indentFactor);
        } else if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            result += toString(new JSONObject(map), indentFactor);
        } else if (value instanceof Collection) {
            Collection<?> coll = (Collection<?>) value;
            result += toString(new JSONArray(coll), indentFactor);
        } else if (value.getClass().isArray()) {
            result += toString(new JSONArray(value), indentFactor);
        } else {
            result += quote(value.toString());
        }
        return result;
    }

    /**
     * 输出{@link JSONArray}的{@link String}
     * 
     * @param origin
     * @param indentFactor
     *            文本整体缩进的空格数*4
     * @return
     */
    private static String toString(JSONArray origin, int indentFactor) {
        String result = "[";
        int i;
        int length = origin.length();
        for (i = 0; i < length; i++) {
            result += toString(origin.get(i), indentFactor + 1);
            result += (i < length - 1) ? ", " : "";
        }
        result += "]";
        return result;
    }

    /**
     * 输出空格数
     * 
     * @param indentFactor
     *            文本整体缩进的空格数*4
     * @return
     */
    private static String space(int indentFactor) {
        String space = "";
        indentFactor = 4 * indentFactor;
        while (indentFactor-- > 0) {
            space += " ";
        }
        return space;
    }

    /**
     * 将{@link String}进行转义
     * 
     * @param string
     * @return
     */
    private static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        String hhhh;
        int i;
        int len = string.length();
        String result = "\"";
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
            case '\\':
            case '"':
                result += '\\' + c;
                break;
            case '/':
                if (b == '<') {
                    result += '\\';
                }
                result += c;
                break;
            case '\b':
                result += "\\b";
                break;
            case '\t':
                result += "\\t";
                break;
            case '\n':
                result += "\\n";
                break;
            case '\f':
                result += "\\f";
                break;
            case '\r':
                result += "\\r";
                break;
            default:
                if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
                    result += "\\u";
                    hhhh = Integer.toHexString(c);
                    result += "0000".substring(0, 4 - hhhh.length());
                    result += hhhh;
                } else {
                    result += c;
                }
            }
        }
        result += '"';
        return result;
    }

    /**
     * 获取脚本中指定参数的值
     * 
     * @param script
     * @param key
     * @return
     * @throws Exception
     */
    public static String getString(JSONObject script, String key, JSONObject config)
            throws JSONException, Exception {
        Object value = script.isNull(key) ? null : script.get(key);
        return value == null ? null : replaceScriptParam(value.toString().trim(), config);
    }

    /**
     * 替换脚本中的参数
     * 
     * @param value
     * @return
     * @throws Exception
     */
    private static String replaceScriptParam(String value, JSONObject config)
            throws JSONException, Exception {
        int start = value.indexOf(Const.SCRIPT_VARIATE[0]);
        int end = value.indexOf(Const.SCRIPT_VARIATE[1]);
        if (start != -1 && end != -1) {
            if (config == null) {
                throw new Exception("配置文件为空");
            }
            String replace = config.get(value.substring(start + 2, end)).toString().trim();
            value = value.substring(0, start) + replace + value.substring(end + 2).trim();
            value = replaceScriptParam(value, config);
        }
        return value;
    }

    public static JSONObject getJSONObject(JSONObject script, String key, JSONObject config) {
        try {
            if (script.isNull(key)) {
                return null;
            }
            String value = script.getJSONObject(key).toString();
            JSONObject jsonObject = new JSONObject(replaceScriptParam(value, config));
            jsonObject = jsonObject.length() > 0 ? jsonObject : null;
            return jsonObject;
        } catch (Exception e) {
            return null;
        }
    }
}