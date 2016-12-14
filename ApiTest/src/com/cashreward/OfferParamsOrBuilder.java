package com.cashreward;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageOrBuilder;

public interface OfferParamsOrBuilder extends MessageOrBuilder {

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
     * <pre>
     * str
     * </pre>
     *
     * <code>required string did = 3;</code>
     */
    boolean hasDid();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string did = 3;</code>
     */
    String getDid();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string did = 3;</code>
     */
    ByteString getDidBytes();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string gaid = 4;</code>
     */
    boolean hasGaid();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string gaid = 4;</code>
     */
    String getGaid();

    /**
     * <pre>
     * str
     * </pre>
     *
     * <code>required string gaid = 4;</code>
     */
    ByteString getGaidBytes();

    /**
     * <code>required string ov = 5;</code>
     */
    boolean hasOv();

    /**
     * <code>required string ov = 5;</code>
     */
    String getOv();

    /**
     * <code>required string ov = 5;</code>
     */
    ByteString getOvBytes();

    /**
     * <code>required string lc = 6;</code>
     */
    boolean hasLc();

    /**
     * <code>required string lc = 6;</code>
     */
    String getLc();

    /**
     * <code>required string lc = 6;</code>
     */
    ByteString getLcBytes();

    /**
     * <code>required string mcc = 7;</code>
     */
    boolean hasMcc();

    /**
     * <code>required string mcc = 7;</code>
     */
    String getMcc();

    /**
     * <code>required string mcc = 7;</code>
     */
    ByteString getMccBytes();

    /**
     * <code>required int32 nt = 8;</code>
     */
    boolean hasNt();

    /**
     * <code>required int32 nt = 8;</code>
     */
    int getNt();

    /**
     * <code>required int32 offer_id = 9;</code>
     */
    boolean hasOfferId();

    /**
     * <code>required int32 offer_id = 9;</code>
     */
    int getOfferId();

    /**
     * <code>required int32 a = 10;</code>
     */
    boolean hasA();

    /**
     * <code>required int32 a = 10;</code>
     */
    int getA();

    /**
     * <code>required int32 b = 11;</code>
     */
    boolean hasB();

    /**
     * <code>required int32 b = 11;</code>
     */
    int getB();

    /**
     * <code>required int32 c = 12;</code>
     */
    boolean hasC();

    /**
     * <code>required int32 c = 12;</code>
     */
    int getC();

    /**
     * <code>required int32 vc = 13;</code>
     */
    boolean hasVc();

    /**
     * <code>required int32 vc = 13;</code>
     */
    int getVc();

    /**
     * <code>required int32 action = 14;</code>
     */
    boolean hasAction();

    /**
     * <code>required int32 action = 14;</code>
     */
    int getAction();
}
