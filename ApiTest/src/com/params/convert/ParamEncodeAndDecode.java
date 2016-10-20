package com.params.convert;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.params.convert.Param.ParamType;
import com.zhh.proto.Params;

public class ParamEncodeAndDecode {

    static String source = "asdfsadfbaseegaWW123$%$";
    static String key = "MoneyReward#1024";
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
                // byte[] bytes = fileContent.getBytes();
                byte[] decode = AESUtil.decrypt(encrypted1, key);
                Params.OfferParams offer = Params.OfferParams.parseFrom(decode);
                result = offer.toString();
            } catch (Exception e) {
                System.out.println("不是offer的p参数");
            }
        } else if (type == 2) {
            try {
                byte[] encrypted1 = ByteUtil.hex2byte(param.toLowerCase());
                // byte[] bytes = fileContent.getBytes();
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

    public static String encode(JSONObject param, int type) {
        String result = "";
        if (type == 1) {
            initOfferParam();
            Params.OfferParams.Builder builder = Params.OfferParams.newBuilder();
            byte[] params = builder.setImei(param.getString("imei"))
                    .setAnid(param.getString("anid")).setDid(param.getString("did"))
                    .setGaid(param.getString("gaid")).setOv(param.getString("ov"))
                    .setLc(param.getString("lc").toString())
                    .setMcc(param.getString("mcc").toString()).setNt(param.getInt("nt"))
                    .setOfferId(param.getInt("offer_id")).setA(param.getInt("a"))
                    .setB(param.getInt("b")).setC(param.getInt("c")).setVc(param.getInt("vc"))
                    .setAction(param.getInt("action")).build().toByteArray();
            result = encrypt(params);
        } else if (type == 2) {
            initExchangeParam();
            Params.ExchangeParams.Builder builder = Params.ExchangeParams.newBuilder();
            byte[] params = builder.setImei(param.getString("imei"))
                    .setAnid(param.getString("anid")).setEt(param.getInt("et"))
                    .setAmount(param.getInt("amount")).setA(param.getInt("a"))
                    .setB(param.getInt("b")).setC(param.getInt("c")).setVc(param.getInt("vc"))
                    .setEa(param.getString("ea")).setSku(param.getString("sku"))
                    .setCt(param.getString("ct")).build().toByteArray();
            result = encrypt(params);
        } else {
            try {
                result = AESUtil.encrypt(param.toString(), key);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
