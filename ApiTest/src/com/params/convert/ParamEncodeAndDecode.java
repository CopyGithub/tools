package com.params.convert;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.params.convert.Param.ParamType;
import com.zhh.proto.Params;

public class ParamEncodeAndDecode {

    public static String key = "";
    static Map<String, Param> paramsMap = new HashMap<String, Param>();

    static void initOfferParam() {
        paramsMap.put("imei", new Param(ParamType.STRING));
        paramsMap.put("anid", new Param(ParamType.STRING));
        paramsMap.put("did", new Param(ParamType.STRING));
        paramsMap.put("gaid", new Param(ParamType.STRING));
        paramsMap.put("ov", new Param(ParamType.STRING));
        paramsMap.put("lc", new Param(ParamType.STRING));
        paramsMap.put("mcc", new Param(ParamType.STRING));
        paramsMap.put("nt", new Param(ParamType.INT));
        paramsMap.put("offer_id", new Param(ParamType.INT));
        paramsMap.put("a", new Param(ParamType.INT));
        paramsMap.put("b", new Param(ParamType.INT));
        paramsMap.put("c", new Param(ParamType.INT));
        paramsMap.put("vc", new Param(ParamType.INT));
        paramsMap.put("action", new Param(ParamType.INT));
    }

    static void initExchangeParam() {
        paramsMap.put("imei", new Param(ParamType.STRING));
        paramsMap.put("anid", new Param(ParamType.STRING));
        paramsMap.put("et", new Param(ParamType.INT));
        paramsMap.put("amount", new Param(ParamType.INT));
        paramsMap.put("a", new Param(ParamType.INT));
        paramsMap.put("b", new Param(ParamType.INT));
        paramsMap.put("c", new Param(ParamType.INT));
        paramsMap.put("vc", new Param(ParamType.INT));
        paramsMap.put("ea", new Param(ParamType.STRING));
        paramsMap.put("sku", new Param(ParamType.STRING));
        paramsMap.put("lc", new Param(ParamType.STRING));
        paramsMap.put("ct", new Param(ParamType.STRING));
    }

    /**
     * 
     * @param param
     * @param type
     *            0普通，1正常，2特殊
     * @return
     */
    public static String decode(String param, int type) {
        String result = "";
        if (type == 1) {
            try {
                byte[] encrypted1 = ByteUtil.hex2byte(param.toLowerCase());
                byte[] decode = AESUtil.decrypt(encrypted1, key);
                Params.OfferParams offer = Params.OfferParams.parseFrom(decode);
                result = offer.toString();
            } catch (Exception e) {
                System.out.println("不是offer的p参数");
            }
        } else if (type == 2) {
            try {
                byte[] encrypted1 = ByteUtil.hex2byte(param.toLowerCase());
                byte[] decode = AESUtil.decrypt(encrypted1, key);
                Params.ExchangeParams offer = Params.ExchangeParams.parseFrom(decode);
                result = offer.toString();
            } catch (Exception e) {
                System.out.println("不是exchange的p参数");
            }
        } else {
            result = AESUtil.decrypt(param, key);
        }
        return result;
    }

    private static final String encrypt(byte[] params) {
        try {
            byte[] encrypt = AESUtil.encrypt(params, key);
            return ByteUtil.byte2hex(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(JSONObject param, int type) {
        String result = "";
        if (type == 1) {
            try {
                initOfferParam();
                readText2Map(param);
                Params.OfferParams.Builder builder = Params.OfferParams.newBuilder();
                byte[] params = builder.setImei(paramsMap.get("imei").toString())
                        .setAnid(paramsMap.get("anid").toString())
                        .setDid(paramsMap.get("did").toString())
                        .setGaid(paramsMap.get("gaid").toString())
                        .setOv(paramsMap.get("ov").toString()).setLc(paramsMap.get("lc").toString())
                        .setMcc(paramsMap.get("mcc").toString()).setNt(paramsMap.get("nt").toInt())
                        .setOfferId(paramsMap.get("offer_id").toInt())
                        .setA(paramsMap.get("a").toInt()).setB(paramsMap.get("b").toInt())
                        .setC(paramsMap.get("c").toInt()).setVc(paramsMap.get("vc").toInt())
                        .setAction(paramsMap.get("action").toInt()).build().toByteArray();
                result = encrypt(params);
            } catch (Exception e) {
                System.out.println("不是offer的参数");
            }
        } else if (type == 2) {
            try {
                initExchangeParam();
                readText2Map(param);
                Params.ExchangeParams.Builder builder = Params.ExchangeParams.newBuilder();
                byte[] params = builder.setImei(paramsMap.get("imei").toString())
                        .setAnid(paramsMap.get("anid").toString())
                        .setEt(paramsMap.get("et").toInt())
                        .setAmount(paramsMap.get("amount").toInt()).setA(paramsMap.get("a").toInt())
                        .setB(paramsMap.get("b").toInt()).setC(paramsMap.get("c").toInt())
                        .setVc(paramsMap.get("vc").toInt()).setEa(paramsMap.get("ea").toString())
                        .setSku(paramsMap.get("sku").toString())
                        .setCt(paramsMap.get("ct").toString()).setLc(paramsMap.get("lc").toString())
                        .build().toByteArray();
                result = encrypt(params);
            } catch (Exception e) {
                System.out.println("不是exchange的参数");
            }
        } else {
            try {
                result = AESUtil.encrypt(param.toString(), key);
            } catch (Exception e) {
            }
        }
        return result;
    }

    private static void readText2Map(JSONObject decode) {
        String[] names = JSONObject.getNames(decode);
        for (String name : names) {
            String param = "";
            try {
                int value = decode.getInt(name);
                System.out.println("int value=" + value);
                param = name + ": " + value;
            } catch (JSONException e) {
                String value = decode.getString(name);
                System.out.println("string value=" + value);
                param = name + ": \"" + value + "\"";
            }
            paramsMap.get(name).setExpression(param);
        }
    }
}
