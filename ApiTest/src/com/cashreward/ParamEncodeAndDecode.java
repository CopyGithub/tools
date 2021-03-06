package com.cashreward;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.cashreward.Param.ParamType;
import com.cashreward.Params.ExchangeParams;
import com.cashreward.Params.OfferParams;

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
     * @throws Exception
     */
    public static String decode(String param, int type) throws Exception {
        if (param == null || key == null || key.length() != 16) {
            return "";
        }
        String result = "";
        byte[] encrypted = ByteUtil.hex2byte(param.toLowerCase());
        byte[] decode = AESUtil.decrypt(encrypted, key);
        if (type == 1) {
            OfferParams offer = OfferParams.parseFrom(decode);
            result = offer.toString();
        } else if (type == 2) {
            ExchangeParams offer = ExchangeParams.parseFrom(decode);
            result = offer.toString();
        } else {
            result = decode != null ? new String(decode) : null;
        }
        return result;
    }

    public static String encode(JSONObject param, int type) throws Exception {
        String result = "";
        if (type == 1) {
            initOfferParam();
            readText2Map(param);
            OfferParams.Builder builder = OfferParams.newBuilder();
            byte[] params = builder.setImei(paramsMap.get("imei").toString())
                    .setAnid(paramsMap.get("anid").toString())
                    .setDid(paramsMap.get("did").toString())
                    .setGaid(paramsMap.get("gaid").toString()).setOv(paramsMap.get("ov").toString())
                    .setLc(paramsMap.get("lc").toString()).setMcc(paramsMap.get("mcc").toString())
                    .setNt(paramsMap.get("nt").toInt())
                    .setOfferId(paramsMap.get("offer_id").toInt()).setA(paramsMap.get("a").toInt())
                    .setB(paramsMap.get("b").toInt()).setC(paramsMap.get("c").toInt())
                    .setVc(paramsMap.get("vc").toInt()).setAction(paramsMap.get("action").toInt())
                    .build().toByteArray();
            result = AESUtil.encrypt(params, key);
        } else if (type == 2) {
            initExchangeParam();
            readText2Map(param);
            ExchangeParams.Builder builder = ExchangeParams.newBuilder();
            byte[] params = builder.setImei(paramsMap.get("imei").toString())
                    .setAnid(paramsMap.get("anid").toString()).setEt(paramsMap.get("et").toInt())
                    .setAmount(paramsMap.get("amount").toInt()).setA(paramsMap.get("a").toInt())
                    .setB(paramsMap.get("b").toInt()).setC(paramsMap.get("c").toInt())
                    .setVc(paramsMap.get("vc").toInt()).setEa(paramsMap.get("ea").toString())
                    .setSku(paramsMap.get("sku").toString()).setCt(paramsMap.get("ct").toString())
                    .setLc(paramsMap.get("lc").toString()).build().toByteArray();
            result = AESUtil.encrypt(params, key);
        } else {
            result = AESUtil.encrypt(param.toString().getBytes(), key);
        }
        return result;
    }

    private static void readText2Map(JSONObject decode) {
        String[] names = JSONObject.getNames(decode);
        for (String name : names) {
            String param = "";
            Object value = decode.get(name);
            param = name + ": " + value;
            paramsMap.get(name).mName = name;
            paramsMap.get(name).mValue = String.valueOf(value);
            paramsMap.get(name).mExpression = param;
        }
    }
}
