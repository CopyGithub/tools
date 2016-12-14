package com.cashreward;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface ExchangeParamsOrBuilder extends MessageOrBuilder {

    /**
     * <code>required string imei = 1;</code>
     */
    boolean hasImei();

    /**
     * <code>required string imei = 1;</code>
     */
    String getImei();

    /**
     * <code>required string imei = 1;</code>
     */
    ByteString getImeiBytes();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string anid = 2;</code>
     */
    boolean hasAnid();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string anid = 2;</code>
     */
    String getAnid();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string anid = 2;</code>
     */
    ByteString getAnidBytes();

    /**
     * <code>required int32 et = 3;</code>
     */
    boolean hasEt();

    /**
     * <code>required int32 et = 3;</code>
     */
    int getEt();

    /**
     * <code>required int32 amount = 4;</code>
     */
    boolean hasAmount();

    /**
     * <code>required int32 amount = 4;</code>
     */
    int getAmount();

    /**
     * <code>required int32 a = 5;</code>
     */
    boolean hasA();

    /**
     * <code>required int32 a = 5;</code>
     */
    int getA();

    /**
     * <code>required int32 b = 6;</code>
     */
    boolean hasB();

    /**
     * <code>required int32 b = 6;</code>
     */
    int getB();

    /**
     * <code>required int32 c = 7;</code>
     */
    boolean hasC();

    /**
     * <code>required int32 c = 7;</code>
     */
    int getC();

    /**
     * <code>required int32 vc = 8;</code>
     */
    boolean hasVc();

    /**
     * <code>required int32 vc = 8;</code>
     */
    int getVc();

    /**
     * <code>required string ea = 9;</code>
     */
    boolean hasEa();

    /**
     * <code>required string ea = 9;</code>
     */
    String getEa();

    /**
     * <code>required string ea = 9;</code>
     */
    ByteString getEaBytes();

    /**
     * <code>required string sku = 10;</code>
     */
    boolean hasSku();

    /**
     * <code>required string sku = 10;</code>
     */
    String getSku();

    /**
     * <code>required string sku = 10;</code>
     */
    ByteString getSkuBytes();

    /**
     * <code>required string lc = 11;</code>
     */
    boolean hasLc();

    /**
     * <code>required string lc = 11;</code>
     */
    String getLc();

    /**
     * <code>required string lc = 11;</code>
     */
    ByteString getLcBytes();

    /**
     * <code>required string ct = 12;</code>
     */
    boolean hasCt();

    /**
     * <code>required string ct = 12;</code>
     */
    String getCt();

    /**
     * <code>required string ct = 12;</code>
     */
    ByteString getCtBytes();
}
