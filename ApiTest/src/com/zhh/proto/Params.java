package com.zhh.proto;

import java.io.IOException;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;

public final class Params {
    private static final Descriptor internal_static_com_zhh_proto_OfferParams_descriptor;
    private static final FieldAccessorTable internal_static_com_zhh_proto_OfferParams_fieldAccessorTable;
    private static final Descriptor internal_static_com_zhh_proto_ExchangeParams_descriptor;
    private static final FieldAccessorTable internal_static_com_zhh_proto_ExchangeParams_fieldAccessorTable;
    private static FileDescriptor descriptor;
    static {
        String[] descriptorData = { "\n\014Params.proto\022\rcom.zhh.proto\"\304\001\n\013OfferP"
                + "arams\022\014\n\004imei\030\001 \002(\t\022\014\n\004anid\030\002 \002(\t\022\013\n\003did"
                + "\030\003 \002(\t\022\014\n\004gaid\030\004 \002(\t\022\n\n\002ov\030\005 \002(\t\022\n\n\002lc\030\006"
                + " \002(\t\022\013\n\003mcc\030\007 \002(\t\022\n\n\002nt\030\010 \002(\005\022\020\n\010offer_i"
                + "d\030\t \002(\005\022\t\n\001a\030\n \002(\005\022\t\n\001b\030\013 \002(\005\022\t\n\001c\030\014 \002(\005"
                + "\022\n\n\002vc\030\r \002(\005\022\016\n\006action\030\016 \002(\005\"\246\001\n\016Exchang"
                + "eParams\022\014\n\004imei\030\001 \002(\t\022\014\n\004anid\030\002 \002(\t\022\n\n\002e"
                + "t\030\003 \002(\005\022\016\n\006amount\030\004 \002(\005\022\t\n\001a\030\005 \002(\005\022\t\n\001b\030"
                + "\006 \002(\005\022\t\n\001c\030\007 \002(\005\022\n\n\002vc\030\010 \002(\005\022\n\n\002ea\030\t \002(\t"
                + "\022\013\n\003sku\030\n \002(\t\022\n\n\002lc\030\013 \002(\t\022\n\n\002ct\030\014 \002(\t" };
        FileDescriptor.InternalDescriptorAssigner assigner = new FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new FileDescriptor[] {},
                assigner);
        internal_static_com_zhh_proto_OfferParams_descriptor = descriptor.getMessageTypes().get(0);
        internal_static_com_zhh_proto_OfferParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(
                internal_static_com_zhh_proto_OfferParams_descriptor,
                new String[] { "Imei", "Anid", "Did", "Gaid", "Ov", "Lc", "Mcc", "Nt", "OfferId",
                        "A", "B", "C", "Vc", "Action", });
        internal_static_com_zhh_proto_ExchangeParams_descriptor = descriptor.getMessageTypes()
                .get(1);
        internal_static_com_zhh_proto_ExchangeParams_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(
                internal_static_com_zhh_proto_ExchangeParams_descriptor, new String[] { "Imei",
                        "Anid", "Et", "Amount", "A", "B", "C", "Vc", "Ea", "Sku", "Lc", "Ct", });
    }

    /**
     * Protobuf type {@code com.zhh.proto.OfferParams}
     */
    public static final class OfferParams extends GeneratedMessage implements OfferParamsOrBuilder {
        private OfferParams(GeneratedMessage.Builder<?> builder) {
            super(builder);
        }

        private OfferParams() {
            imei_ = "";
            anid_ = "";
            did_ = "";
            gaid_ = "";
            ov_ = "";
            lc_ = "";
            mcc_ = "";
            nt_ = 0;
            offerId_ = 0;
            a_ = 0;
            b_ = 0;
            c_ = 0;
            vc_ = 0;
            action_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return unknownFields;
        }

        private OfferParams(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                    case 0:
                        done = true;
                        break;
                    default: {
                        if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                    case 10: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000001;
                        imei_ = bs;
                        break;
                    }
                    case 18: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000002;
                        anid_ = bs;
                        break;
                    }
                    case 26: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000004;
                        did_ = bs;
                        break;
                    }
                    case 34: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000008;
                        gaid_ = bs;
                        break;
                    }
                    case 42: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000010;
                        ov_ = bs;
                        break;
                    }
                    case 50: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000020;
                        lc_ = bs;
                        break;
                    }
                    case 58: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000040;
                        mcc_ = bs;
                        break;
                    }
                    case 64: {
                        bitField0_ |= 0x00000080;
                        nt_ = input.readInt32();
                        break;
                    }
                    case 72: {
                        bitField0_ |= 0x00000100;
                        offerId_ = input.readInt32();
                        break;
                    }
                    case 80: {
                        bitField0_ |= 0x00000200;
                        a_ = input.readInt32();
                        break;
                    }
                    case 88: {
                        bitField0_ |= 0x00000400;
                        b_ = input.readInt32();
                        break;
                    }
                    case 96: {
                        bitField0_ |= 0x00000800;
                        c_ = input.readInt32();
                        break;
                    }
                    case 104: {
                        bitField0_ |= 0x00001000;
                        vc_ = input.readInt32();
                        break;
                    }
                    case 112: {
                        bitField0_ |= 0x00002000;
                        action_ = input.readInt32();
                        break;
                    }
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e) {
                throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final Descriptor getDescriptor() {
            return internal_static_com_zhh_proto_OfferParams_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_com_zhh_proto_OfferParams_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(OfferParams.class, OfferParams.Builder.class);
        }

        private int bitField0_;
        public static final int IMEI_FIELD_NUMBER = 1;
        private volatile Object imei_;

        /**
         * <code>required string imei = 1;</code>
         */
        @Override
        public boolean hasImei() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>required string imei = 1;</code>
         */
        @Override
        public String getImei() {
            Object ref = imei_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    imei_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string imei = 1;</code>
         */
        @Override
        public ByteString getImeiBytes() {
            Object ref = imei_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                imei_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int ANID_FIELD_NUMBER = 2;
        private volatile Object anid_;

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string anid = 2;</code>
         */
        @Override
        public boolean hasAnid() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string anid = 2;</code>
         */
        public String getAnid() {
            Object ref = anid_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    anid_ = s;
                }
                return s;
            }
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string anid = 2;</code>
         */
        public ByteString getAnidBytes() {
            Object ref = anid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                anid_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int DID_FIELD_NUMBER = 3;
        private volatile Object did_;

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string did = 3;</code>
         */
        public boolean hasDid() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string did = 3;</code>
         */
        public String getDid() {
            Object ref = did_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    did_ = s;
                }
                return s;
            }
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string did = 3;</code>
         */
        public ByteString getDidBytes() {
            Object ref = did_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                did_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int GAID_FIELD_NUMBER = 4;
        private volatile Object gaid_;

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string gaid = 4;</code>
         */
        public boolean hasGaid() {
            return ((bitField0_ & 0x00000008) == 0x00000008);
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string gaid = 4;</code>
         */
        public String getGaid() {
            Object ref = gaid_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    gaid_ = s;
                }
                return s;
            }
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string gaid = 4;</code>
         */
        public ByteString getGaidBytes() {
            Object ref = gaid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                gaid_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int OV_FIELD_NUMBER = 5;
        private volatile Object ov_;

        /**
         * <code>required string ov = 5;</code>
         */
        public boolean hasOv() {
            return ((bitField0_ & 0x00000010) == 0x00000010);
        }

        /**
         * <code>required string ov = 5;</code>
         */
        public String getOv() {
            Object ref = ov_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    ov_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string ov = 5;</code>
         */
        public ByteString getOvBytes() {
            Object ref = ov_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                ov_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int LC_FIELD_NUMBER = 6;
        private volatile Object lc_;

        /**
         * <code>required string lc = 6;</code>
         */
        public boolean hasLc() {
            return ((bitField0_ & 0x00000020) == 0x00000020);
        }

        /**
         * <code>required string lc = 6;</code>
         */
        public String getLc() {
            Object ref = lc_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    lc_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string lc = 6;</code>
         */
        public ByteString getLcBytes() {
            Object ref = lc_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                lc_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int MCC_FIELD_NUMBER = 7;
        private volatile Object mcc_;

        /**
         * <code>required string mcc = 7;</code>
         */
        public boolean hasMcc() {
            return ((bitField0_ & 0x00000040) == 0x00000040);
        }

        /**
         * <code>required string mcc = 7;</code>
         */
        public String getMcc() {
            Object ref = mcc_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    mcc_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string mcc = 7;</code>
         */
        public ByteString getMccBytes() {
            Object ref = mcc_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                mcc_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int NT_FIELD_NUMBER = 8;
        private int nt_;

        /**
         * <code>required int32 nt = 8;</code>
         */
        public boolean hasNt() {
            return ((bitField0_ & 0x00000080) == 0x00000080);
        }

        /**
         * <code>required int32 nt = 8;</code>
         */
        public int getNt() {
            return nt_;
        }

        public static final int OFFER_ID_FIELD_NUMBER = 9;
        private int offerId_;

        /**
         * <code>required int32 offer_id = 9;</code>
         */
        public boolean hasOfferId() {
            return ((bitField0_ & 0x00000100) == 0x00000100);
        }

        /**
         * <code>required int32 offer_id = 9;</code>
         */
        public int getOfferId() {
            return offerId_;
        }

        public static final int A_FIELD_NUMBER = 10;
        private int a_;

        /**
         * <code>required int32 a = 10;</code>
         */
        public boolean hasA() {
            return ((bitField0_ & 0x00000200) == 0x00000200);
        }

        /**
         * <code>required int32 a = 10;</code>
         */
        public int getA() {
            return a_;
        }

        public static final int B_FIELD_NUMBER = 11;
        private int b_;

        /**
         * <code>required int32 b = 11;</code>
         */
        public boolean hasB() {
            return ((bitField0_ & 0x00000400) == 0x00000400);
        }

        /**
         * <code>required int32 b = 11;</code>
         */
        public int getB() {
            return b_;
        }

        public static final int C_FIELD_NUMBER = 12;
        private int c_;

        /**
         * <code>required int32 c = 12;</code>
         */
        public boolean hasC() {
            return ((bitField0_ & 0x00000800) == 0x00000800);
        }

        /**
         * <code>required int32 c = 12;</code>
         */
        public int getC() {
            return c_;
        }

        public static final int VC_FIELD_NUMBER = 13;
        private int vc_;

        /**
         * <code>required int32 vc = 13;</code>
         */
        public boolean hasVc() {
            return ((bitField0_ & 0x00001000) == 0x00001000);
        }

        /**
         * <code>required int32 vc = 13;</code>
         */
        public int getVc() {
            return vc_;
        }

        public static final int ACTION_FIELD_NUMBER = 14;
        private int action_;

        /**
         * <code>required int32 action = 14;</code>
         */
        public boolean hasAction() {
            return ((bitField0_ & 0x00002000) == 0x00002000);
        }

        /**
         * <code>required int32 action = 14;</code>
         */
        public int getAction() {
            return action_;
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1)
                return true;
            if (isInitialized == 0)
                return false;

            if (!hasImei()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasAnid()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasDid()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasGaid()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasOv()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasLc()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasMcc()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasNt()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasOfferId()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasA()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasB()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasC()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasVc()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasAction()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws java.io.IOException {
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                GeneratedMessage.writeString(output, 1, imei_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                GeneratedMessage.writeString(output, 2, anid_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                GeneratedMessage.writeString(output, 3, did_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                GeneratedMessage.writeString(output, 4, gaid_);
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                GeneratedMessage.writeString(output, 5, ov_);
            }
            if (((bitField0_ & 0x00000020) == 0x00000020)) {
                GeneratedMessage.writeString(output, 6, lc_);
            }
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
                GeneratedMessage.writeString(output, 7, mcc_);
            }
            if (((bitField0_ & 0x00000080) == 0x00000080)) {
                output.writeInt32(8, nt_);
            }
            if (((bitField0_ & 0x00000100) == 0x00000100)) {
                output.writeInt32(9, offerId_);
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                output.writeInt32(10, a_);
            }
            if (((bitField0_ & 0x00000400) == 0x00000400)) {
                output.writeInt32(11, b_);
            }
            if (((bitField0_ & 0x00000800) == 0x00000800)) {
                output.writeInt32(12, c_);
            }
            if (((bitField0_ & 0x00001000) == 0x00001000)) {
                output.writeInt32(13, vc_);
            }
            if (((bitField0_ & 0x00002000) == 0x00002000)) {
                output.writeInt32(14, action_);
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1)
                return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += GeneratedMessage.computeStringSize(1, imei_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += GeneratedMessage.computeStringSize(2, anid_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += GeneratedMessage.computeStringSize(3, did_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                size += GeneratedMessage.computeStringSize(4, gaid_);
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                size += GeneratedMessage.computeStringSize(5, ov_);
            }
            if (((bitField0_ & 0x00000020) == 0x00000020)) {
                size += GeneratedMessage.computeStringSize(6, lc_);
            }
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
                size += GeneratedMessage.computeStringSize(7, mcc_);
            }
            if (((bitField0_ & 0x00000080) == 0x00000080)) {
                size += CodedOutputStream.computeInt32Size(8, nt_);
            }
            if (((bitField0_ & 0x00000100) == 0x00000100)) {
                size += CodedOutputStream.computeInt32Size(9, offerId_);
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                size += CodedOutputStream.computeInt32Size(10, a_);
            }
            if (((bitField0_ & 0x00000400) == 0x00000400)) {
                size += CodedOutputStream.computeInt32Size(11, b_);
            }
            if (((bitField0_ & 0x00000800) == 0x00000800)) {
                size += CodedOutputStream.computeInt32Size(12, c_);
            }
            if (((bitField0_ & 0x00001000) == 0x00001000)) {
                size += CodedOutputStream.computeInt32Size(13, vc_);
            }
            if (((bitField0_ & 0x00002000) == 0x00002000)) {
                size += CodedOutputStream.computeInt32Size(14, action_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;

        public static OfferParams parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static OfferParams parseFrom(ByteString data,
                ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static OfferParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static OfferParams parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static OfferParams parseFrom(java.io.InputStream input) throws java.io.IOException {
            return GeneratedMessage.parseWithIOException(PARSER, input);
        }

        public static OfferParams parseFrom(java.io.InputStream input,
                ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
            return GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OfferParams parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
        }

        public static OfferParams parseDelimitedFrom(java.io.InputStream input,
                ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
            return GeneratedMessage.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OfferParams parseFrom(CodedInputStream input) throws java.io.IOException {
            return GeneratedMessage.parseWithIOException(PARSER, input);
        }

        public static OfferParams parseFrom(CodedInputStream input,
                ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
            return GeneratedMessage.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OfferParams prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code com.zhh.proto.OfferParams}
         */
        public static final class Builder extends GeneratedMessage.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:com.zhh.proto.OfferParams)
                OfferParamsOrBuilder {
            public static final Descriptor getDescriptor() {
                return internal_static_com_zhh_proto_OfferParams_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_com_zhh_proto_OfferParams_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(OfferParams.class,
                                OfferParams.Builder.class);
            }

            // Construct using OfferParams.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                imei_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                anid_ = "";
                bitField0_ = (bitField0_ & ~0x00000002);
                did_ = "";
                bitField0_ = (bitField0_ & ~0x00000004);
                gaid_ = "";
                bitField0_ = (bitField0_ & ~0x00000008);
                ov_ = "";
                bitField0_ = (bitField0_ & ~0x00000010);
                lc_ = "";
                bitField0_ = (bitField0_ & ~0x00000020);
                mcc_ = "";
                bitField0_ = (bitField0_ & ~0x00000040);
                nt_ = 0;
                bitField0_ = (bitField0_ & ~0x00000080);
                offerId_ = 0;
                bitField0_ = (bitField0_ & ~0x00000100);
                a_ = 0;
                bitField0_ = (bitField0_ & ~0x00000200);
                b_ = 0;
                bitField0_ = (bitField0_ & ~0x00000400);
                c_ = 0;
                bitField0_ = (bitField0_ & ~0x00000800);
                vc_ = 0;
                bitField0_ = (bitField0_ & ~0x00001000);
                action_ = 0;
                bitField0_ = (bitField0_ & ~0x00002000);
                return this;
            }

            public Descriptor getDescriptorForType() {
                return internal_static_com_zhh_proto_OfferParams_descriptor;
            }

            public OfferParams getDefaultInstanceForType() {
                return OfferParams.getDefaultInstance();
            }

            public OfferParams build() {
                OfferParams result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public OfferParams buildPartial() {
                OfferParams result = new OfferParams(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.imei_ = imei_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.anid_ = anid_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.did_ = did_;
                if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
                    to_bitField0_ |= 0x00000008;
                }
                result.gaid_ = gaid_;
                if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
                    to_bitField0_ |= 0x00000010;
                }
                result.ov_ = ov_;
                if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
                    to_bitField0_ |= 0x00000020;
                }
                result.lc_ = lc_;
                if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
                    to_bitField0_ |= 0x00000040;
                }
                result.mcc_ = mcc_;
                if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
                    to_bitField0_ |= 0x00000080;
                }
                result.nt_ = nt_;
                if (((from_bitField0_ & 0x00000100) == 0x00000100)) {
                    to_bitField0_ |= 0x00000100;
                }
                result.offerId_ = offerId_;
                if (((from_bitField0_ & 0x00000200) == 0x00000200)) {
                    to_bitField0_ |= 0x00000200;
                }
                result.a_ = a_;
                if (((from_bitField0_ & 0x00000400) == 0x00000400)) {
                    to_bitField0_ |= 0x00000400;
                }
                result.b_ = b_;
                if (((from_bitField0_ & 0x00000800) == 0x00000800)) {
                    to_bitField0_ |= 0x00000800;
                }
                result.c_ = c_;
                if (((from_bitField0_ & 0x00001000) == 0x00001000)) {
                    to_bitField0_ |= 0x00001000;
                }
                result.vc_ = vc_;
                if (((from_bitField0_ & 0x00002000) == 0x00002000)) {
                    to_bitField0_ |= 0x00002000;
                }
                result.action_ = action_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof OfferParams) {
                    return mergeFrom((OfferParams) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(OfferParams other) {
                if (other == OfferParams.getDefaultInstance())
                    return this;
                if (other.hasImei()) {
                    bitField0_ |= 0x00000001;
                    imei_ = other.imei_;
                    onChanged();
                }
                if (other.hasAnid()) {
                    bitField0_ |= 0x00000002;
                    anid_ = other.anid_;
                    onChanged();
                }
                if (other.hasDid()) {
                    bitField0_ |= 0x00000004;
                    did_ = other.did_;
                    onChanged();
                }
                if (other.hasGaid()) {
                    bitField0_ |= 0x00000008;
                    gaid_ = other.gaid_;
                    onChanged();
                }
                if (other.hasOv()) {
                    bitField0_ |= 0x00000010;
                    ov_ = other.ov_;
                    onChanged();
                }
                if (other.hasLc()) {
                    bitField0_ |= 0x00000020;
                    lc_ = other.lc_;
                    onChanged();
                }
                if (other.hasMcc()) {
                    bitField0_ |= 0x00000040;
                    mcc_ = other.mcc_;
                    onChanged();
                }
                if (other.hasNt()) {
                    setNt(other.getNt());
                }
                if (other.hasOfferId()) {
                    setOfferId(other.getOfferId());
                }
                if (other.hasA()) {
                    setA(other.getA());
                }
                if (other.hasB()) {
                    setB(other.getB());
                }
                if (other.hasC()) {
                    setC(other.getC());
                }
                if (other.hasVc()) {
                    setVc(other.getVc());
                }
                if (other.hasAction()) {
                    setAction(other.getAction());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                if (!hasImei()) {
                    return false;
                }
                if (!hasAnid()) {
                    return false;
                }
                if (!hasDid()) {
                    return false;
                }
                if (!hasGaid()) {
                    return false;
                }
                if (!hasOv()) {
                    return false;
                }
                if (!hasLc()) {
                    return false;
                }
                if (!hasMcc()) {
                    return false;
                }
                if (!hasNt()) {
                    return false;
                }
                if (!hasOfferId()) {
                    return false;
                }
                if (!hasA()) {
                    return false;
                }
                if (!hasB()) {
                    return false;
                }
                if (!hasC()) {
                    return false;
                }
                if (!hasVc()) {
                    return false;
                }
                if (!hasAction()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input,
                    ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
                OfferParams parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (OfferParams) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private Object imei_ = "";

            /**
             * <code>required string imei = 1;</code>
             */
            public boolean hasImei() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public String getImei() {
                Object ref = imei_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        imei_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public ByteString getImeiBytes() {
                Object ref = imei_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    imei_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public Builder setImei(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                imei_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public Builder clearImei() {
                bitField0_ = (bitField0_ & ~0x00000001);
                imei_ = getDefaultInstance().getImei();
                onChanged();
                return this;
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public Builder setImeiBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                imei_ = value;
                onChanged();
                return this;
            }

            private Object anid_ = "";

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public boolean hasAnid() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public String getAnid() {
                Object ref = anid_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        anid_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public ByteString getAnidBytes() {
                Object ref = anid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    anid_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public Builder setAnid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                anid_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public Builder clearAnid() {
                bitField0_ = (bitField0_ & ~0x00000002);
                anid_ = getDefaultInstance().getAnid();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public Builder setAnidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                anid_ = value;
                onChanged();
                return this;
            }

            private Object did_ = "";

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string did = 3;</code>
             */
            public boolean hasDid() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string did = 3;</code>
             */
            public String getDid() {
                Object ref = did_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        did_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string did = 3;</code>
             */
            public ByteString getDidBytes() {
                Object ref = did_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    did_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string did = 3;</code>
             */
            public Builder setDid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                did_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string did = 3;</code>
             */
            public Builder clearDid() {
                bitField0_ = (bitField0_ & ~0x00000004);
                did_ = getDefaultInstance().getDid();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string did = 3;</code>
             */
            public Builder setDidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000004;
                did_ = value;
                onChanged();
                return this;
            }

            private Object gaid_ = "";

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string gaid = 4;</code>
             */
            public boolean hasGaid() {
                return ((bitField0_ & 0x00000008) == 0x00000008);
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string gaid = 4;</code>
             */
            public String getGaid() {
                Object ref = gaid_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        gaid_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string gaid = 4;</code>
             */
            public ByteString getGaidBytes() {
                Object ref = gaid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    gaid_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string gaid = 4;</code>
             */
            public Builder setGaid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000008;
                gaid_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string gaid = 4;</code>
             */
            public Builder clearGaid() {
                bitField0_ = (bitField0_ & ~0x00000008);
                gaid_ = getDefaultInstance().getGaid();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string gaid = 4;</code>
             */
            public Builder setGaidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000008;
                gaid_ = value;
                onChanged();
                return this;
            }

            private Object ov_ = "";

            /**
             * <code>required string ov = 5;</code>
             */
            public boolean hasOv() {
                return ((bitField0_ & 0x00000010) == 0x00000010);
            }

            /**
             * <code>required string ov = 5;</code>
             */
            public String getOv() {
                Object ref = ov_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        ov_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string ov = 5;</code>
             */
            public ByteString getOvBytes() {
                Object ref = ov_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    ov_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string ov = 5;</code>
             */
            public Builder setOv(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000010;
                ov_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string ov = 5;</code>
             */
            public Builder clearOv() {
                bitField0_ = (bitField0_ & ~0x00000010);
                ov_ = getDefaultInstance().getOv();
                onChanged();
                return this;
            }

            /**
             * <code>required string ov = 5;</code>
             */
            public Builder setOvBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000010;
                ov_ = value;
                onChanged();
                return this;
            }

            private Object lc_ = "";

            /**
             * <code>required string lc = 6;</code>
             */
            public boolean hasLc() {
                return ((bitField0_ & 0x00000020) == 0x00000020);
            }

            /**
             * <code>required string lc = 6;</code>
             */
            public String getLc() {
                Object ref = lc_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        lc_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string lc = 6;</code>
             */
            public ByteString getLcBytes() {
                Object ref = lc_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    lc_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string lc = 6;</code>
             */
            public Builder setLc(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000020;
                lc_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string lc = 6;</code>
             */
            public Builder clearLc() {
                bitField0_ = (bitField0_ & ~0x00000020);
                lc_ = getDefaultInstance().getLc();
                onChanged();
                return this;
            }

            /**
             * <code>required string lc = 6;</code>
             */
            public Builder setLcBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000020;
                lc_ = value;
                onChanged();
                return this;
            }

            private Object mcc_ = "";

            /**
             * <code>required string mcc = 7;</code>
             */
            public boolean hasMcc() {
                return ((bitField0_ & 0x00000040) == 0x00000040);
            }

            /**
             * <code>required string mcc = 7;</code>
             */
            public String getMcc() {
                Object ref = mcc_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        mcc_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string mcc = 7;</code>
             */
            public ByteString getMccBytes() {
                Object ref = mcc_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    mcc_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string mcc = 7;</code>
             */
            public Builder setMcc(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000040;
                mcc_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string mcc = 7;</code>
             */
            public Builder clearMcc() {
                bitField0_ = (bitField0_ & ~0x00000040);
                mcc_ = getDefaultInstance().getMcc();
                onChanged();
                return this;
            }

            /**
             * <code>required string mcc = 7;</code>
             */
            public Builder setMccBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000040;
                mcc_ = value;
                onChanged();
                return this;
            }

            private int nt_;

            /**
             * <code>required int32 nt = 8;</code>
             */
            public boolean hasNt() {
                return ((bitField0_ & 0x00000080) == 0x00000080);
            }

            /**
             * <code>required int32 nt = 8;</code>
             */
            public int getNt() {
                return nt_;
            }

            /**
             * <code>required int32 nt = 8;</code>
             */
            public Builder setNt(int value) {
                bitField0_ |= 0x00000080;
                nt_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 nt = 8;</code>
             */
            public Builder clearNt() {
                bitField0_ = (bitField0_ & ~0x00000080);
                nt_ = 0;
                onChanged();
                return this;
            }

            private int offerId_;

            /**
             * <code>required int32 offer_id = 9;</code>
             */
            public boolean hasOfferId() {
                return ((bitField0_ & 0x00000100) == 0x00000100);
            }

            /**
             * <code>required int32 offer_id = 9;</code>
             */
            public int getOfferId() {
                return offerId_;
            }

            /**
             * <code>required int32 offer_id = 9;</code>
             */
            public Builder setOfferId(int value) {
                bitField0_ |= 0x00000100;
                offerId_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 offer_id = 9;</code>
             */
            public Builder clearOfferId() {
                bitField0_ = (bitField0_ & ~0x00000100);
                offerId_ = 0;
                onChanged();
                return this;
            }

            private int a_;

            /**
             * <code>required int32 a = 10;</code>
             */
            public boolean hasA() {
                return ((bitField0_ & 0x00000200) == 0x00000200);
            }

            /**
             * <code>required int32 a = 10;</code>
             */
            public int getA() {
                return a_;
            }

            /**
             * <code>required int32 a = 10;</code>
             */
            public Builder setA(int value) {
                bitField0_ |= 0x00000200;
                a_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 a = 10;</code>
             */
            public Builder clearA() {
                bitField0_ = (bitField0_ & ~0x00000200);
                a_ = 0;
                onChanged();
                return this;
            }

            private int b_;

            /**
             * <code>required int32 b = 11;</code>
             */
            public boolean hasB() {
                return ((bitField0_ & 0x00000400) == 0x00000400);
            }

            /**
             * <code>required int32 b = 11;</code>
             */
            public int getB() {
                return b_;
            }

            /**
             * <code>required int32 b = 11;</code>
             */
            public Builder setB(int value) {
                bitField0_ |= 0x00000400;
                b_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 b = 11;</code>
             */
            public Builder clearB() {
                bitField0_ = (bitField0_ & ~0x00000400);
                b_ = 0;
                onChanged();
                return this;
            }

            private int c_;

            /**
             * <code>required int32 c = 12;</code>
             */
            public boolean hasC() {
                return ((bitField0_ & 0x00000800) == 0x00000800);
            }

            /**
             * <code>required int32 c = 12;</code>
             */
            public int getC() {
                return c_;
            }

            /**
             * <code>required int32 c = 12;</code>
             */
            public Builder setC(int value) {
                bitField0_ |= 0x00000800;
                c_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 c = 12;</code>
             */
            public Builder clearC() {
                bitField0_ = (bitField0_ & ~0x00000800);
                c_ = 0;
                onChanged();
                return this;
            }

            private int vc_;

            /**
             * <code>required int32 vc = 13;</code>
             */
            public boolean hasVc() {
                return ((bitField0_ & 0x00001000) == 0x00001000);
            }

            /**
             * <code>required int32 vc = 13;</code>
             */
            public int getVc() {
                return vc_;
            }

            /**
             * <code>required int32 vc = 13;</code>
             */
            public Builder setVc(int value) {
                bitField0_ |= 0x00001000;
                vc_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 vc = 13;</code>
             */
            public Builder clearVc() {
                bitField0_ = (bitField0_ & ~0x00001000);
                vc_ = 0;
                onChanged();
                return this;
            }

            private int action_;

            /**
             * <code>required int32 action = 14;</code>
             */
            public boolean hasAction() {
                return ((bitField0_ & 0x00002000) == 0x00002000);
            }

            /**
             * <code>required int32 action = 14;</code>
             */
            public int getAction() {
                return action_;
            }

            /**
             * <code>required int32 action = 14;</code>
             */
            public Builder setAction(int value) {
                bitField0_ |= 0x00002000;
                action_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 action = 14;</code>
             */
            public Builder clearAction() {
                bitField0_ = (bitField0_ & ~0x00002000);
                action_ = 0;
                onChanged();
                return this;
            }

            // @@protoc_insertion_point(builder_scope:com.zhh.proto.OfferParams)
        }

        // @@protoc_insertion_point(class_scope:com.zhh.proto.OfferParams)
        private static final OfferParams DEFAULT_INSTANCE;
        static {
            DEFAULT_INSTANCE = new OfferParams();
        }

        public static OfferParams getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        @Deprecated
        public static final Parser<OfferParams> PARSER = new AbstractParser<OfferParams>() {
            public OfferParams parsePartialFrom(CodedInputStream input,
                    ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OfferParams(input, extensionRegistry);
            }
        };

        public static Parser<OfferParams> parser() {
            return PARSER;
        }

        @Override
        public Parser<OfferParams> getParserForType() {
            return PARSER;
        }

        public OfferParams getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    /**
     * Protobuf type {@code com.zhh.proto.ExchangeParams}
     */
    public static final class ExchangeParams extends GeneratedMessage
            implements ExchangeParamsOrBuilder {
        private ExchangeParams(GeneratedMessage.Builder<?> builder) {
            super(builder);
        }

        private ExchangeParams() {
            imei_ = "";
            anid_ = "";
            et_ = 0;
            amount_ = 0;
            a_ = 0;
            b_ = 0;
            c_ = 0;
            vc_ = 0;
            ea_ = "";
            sku_ = "";
            lc_ = "";
            ct_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ExchangeParams(CodedInputStream input, ExtensionRegistryLite extensionRegistry)
                throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                    case 0:
                        done = true;
                        break;
                    default: {
                        if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                    case 10: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000001;
                        imei_ = bs;
                        break;
                    }
                    case 18: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000002;
                        anid_ = bs;
                        break;
                    }
                    case 24: {
                        bitField0_ |= 0x00000004;
                        et_ = input.readInt32();
                        break;
                    }
                    case 32: {
                        bitField0_ |= 0x00000008;
                        amount_ = input.readInt32();
                        break;
                    }
                    case 40: {
                        bitField0_ |= 0x00000010;
                        a_ = input.readInt32();
                        break;
                    }
                    case 48: {
                        bitField0_ |= 0x00000020;
                        b_ = input.readInt32();
                        break;
                    }
                    case 56: {
                        bitField0_ |= 0x00000040;
                        c_ = input.readInt32();
                        break;
                    }
                    case 64: {
                        bitField0_ |= 0x00000080;
                        vc_ = input.readInt32();
                        break;
                    }
                    case 74: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000100;
                        ea_ = bs;
                        break;
                    }
                    case 82: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000200;
                        sku_ = bs;
                        break;
                    }
                    case 90: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000400;
                        lc_ = bs;
                        break;
                    }
                    case 98: {
                        ByteString bs = input.readBytes();
                        bitField0_ |= 0x00000800;
                        ct_ = bs;
                        break;
                    }
                    }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final Descriptor getDescriptor() {
            return internal_static_com_zhh_proto_ExchangeParams_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_com_zhh_proto_ExchangeParams_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(ExchangeParams.class,
                            ExchangeParams.Builder.class);
        }

        private int bitField0_;
        public static final int IMEI_FIELD_NUMBER = 1;
        private volatile Object imei_;

        /**
         * <code>required string imei = 1;</code>
         */
        public boolean hasImei() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>required string imei = 1;</code>
         */
        public String getImei() {
            Object ref = imei_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    imei_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string imei = 1;</code>
         */
        public ByteString getImeiBytes() {
            Object ref = imei_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                imei_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int ANID_FIELD_NUMBER = 2;
        private volatile Object anid_;

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string anid = 2;</code>
         */
        public boolean hasAnid() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string anid = 2;</code>
         */
        public String getAnid() {
            Object ref = anid_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    anid_ = s;
                }
                return s;
            }
        }

        /**
         * <pre>
         * str
         * </pre>
         *
         * <code>required string anid = 2;</code>
         */
        public ByteString getAnidBytes() {
            Object ref = anid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                anid_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int ET_FIELD_NUMBER = 3;
        private int et_;

        /**
         * <code>required int32 et = 3;</code>
         */
        public boolean hasEt() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <code>required int32 et = 3;</code>
         */
        public int getEt() {
            return et_;
        }

        public static final int AMOUNT_FIELD_NUMBER = 4;
        private int amount_;

        /**
         * <code>required int32 amount = 4;</code>
         */
        public boolean hasAmount() {
            return ((bitField0_ & 0x00000008) == 0x00000008);
        }

        /**
         * <code>required int32 amount = 4;</code>
         */
        public int getAmount() {
            return amount_;
        }

        public static final int A_FIELD_NUMBER = 5;
        private int a_;

        /**
         * <code>required int32 a = 5;</code>
         */
        public boolean hasA() {
            return ((bitField0_ & 0x00000010) == 0x00000010);
        }

        /**
         * <code>required int32 a = 5;</code>
         */
        public int getA() {
            return a_;
        }

        public static final int B_FIELD_NUMBER = 6;
        private int b_;

        /**
         * <code>required int32 b = 6;</code>
         */
        public boolean hasB() {
            return ((bitField0_ & 0x00000020) == 0x00000020);
        }

        /**
         * <code>required int32 b = 6;</code>
         */
        public int getB() {
            return b_;
        }

        public static final int C_FIELD_NUMBER = 7;
        private int c_;

        /**
         * <code>required int32 c = 7;</code>
         */
        public boolean hasC() {
            return ((bitField0_ & 0x00000040) == 0x00000040);
        }

        /**
         * <code>required int32 c = 7;</code>
         */
        public int getC() {
            return c_;
        }

        public static final int VC_FIELD_NUMBER = 8;
        private int vc_;

        /**
         * <code>required int32 vc = 8;</code>
         */
        public boolean hasVc() {
            return ((bitField0_ & 0x00000080) == 0x00000080);
        }

        /**
         * <code>required int32 vc = 8;</code>
         */
        public int getVc() {
            return vc_;
        }

        public static final int EA_FIELD_NUMBER = 9;
        private volatile Object ea_;

        /**
         * <code>required string ea = 9;</code>
         */
        public boolean hasEa() {
            return ((bitField0_ & 0x00000100) == 0x00000100);
        }

        /**
         * <code>required string ea = 9;</code>
         */
        public String getEa() {
            Object ref = ea_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    ea_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string ea = 9;</code>
         */
        public ByteString getEaBytes() {
            Object ref = ea_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                ea_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int SKU_FIELD_NUMBER = 10;
        private volatile Object sku_;

        /**
         * <code>required string sku = 10;</code>
         */
        public boolean hasSku() {
            return ((bitField0_ & 0x00000200) == 0x00000200);
        }

        /**
         * <code>required string sku = 10;</code>
         */
        public String getSku() {
            Object ref = sku_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    sku_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string sku = 10;</code>
         */
        public ByteString getSkuBytes() {
            Object ref = sku_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                sku_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int LC_FIELD_NUMBER = 11;
        private volatile Object lc_;

        /**
         * <code>required string lc = 11;</code>
         */
        public boolean hasLc() {
            return ((bitField0_ & 0x00000400) == 0x00000400);
        }

        /**
         * <code>required string lc = 11;</code>
         */
        public String getLc() {
            Object ref = lc_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    lc_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string lc = 11;</code>
         */
        public ByteString getLcBytes() {
            Object ref = lc_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                lc_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        public static final int CT_FIELD_NUMBER = 12;
        private volatile Object ct_;

        /**
         * <code>required string ct = 12;</code>
         */
        public boolean hasCt() {
            return ((bitField0_ & 0x00000800) == 0x00000800);
        }

        /**
         * <code>required string ct = 12;</code>
         */
        public String getCt() {
            Object ref = ct_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    ct_ = s;
                }
                return s;
            }
        }

        /**
         * <code>required string ct = 12;</code>
         */
        public ByteString getCtBytes() {
            Object ref = ct_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                ct_ = b;
                return b;
            } else {
                return (ByteString) ref;
            }
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1)
                return true;
            if (isInitialized == 0)
                return false;

            if (!hasImei()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasAnid()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasEt()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasAmount()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasA()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasB()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasC()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasVc()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasEa()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasSku()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasLc()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasCt()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws java.io.IOException {
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                GeneratedMessage.writeString(output, 1, imei_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                GeneratedMessage.writeString(output, 2, anid_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeInt32(3, et_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                output.writeInt32(4, amount_);
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                output.writeInt32(5, a_);
            }
            if (((bitField0_ & 0x00000020) == 0x00000020)) {
                output.writeInt32(6, b_);
            }
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
                output.writeInt32(7, c_);
            }
            if (((bitField0_ & 0x00000080) == 0x00000080)) {
                output.writeInt32(8, vc_);
            }
            if (((bitField0_ & 0x00000100) == 0x00000100)) {
                GeneratedMessage.writeString(output, 9, ea_);
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                GeneratedMessage.writeString(output, 10, sku_);
            }
            if (((bitField0_ & 0x00000400) == 0x00000400)) {
                GeneratedMessage.writeString(output, 11, lc_);
            }
            if (((bitField0_ & 0x00000800) == 0x00000800)) {
                GeneratedMessage.writeString(output, 12, ct_);
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1)
                return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += GeneratedMessage.computeStringSize(1, imei_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += GeneratedMessage.computeStringSize(2, anid_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += CodedOutputStream.computeInt32Size(3, et_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                size += CodedOutputStream.computeInt32Size(4, amount_);
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                size += CodedOutputStream.computeInt32Size(5, a_);
            }
            if (((bitField0_ & 0x00000020) == 0x00000020)) {
                size += CodedOutputStream.computeInt32Size(6, b_);
            }
            if (((bitField0_ & 0x00000040) == 0x00000040)) {
                size += CodedOutputStream.computeInt32Size(7, c_);
            }
            if (((bitField0_ & 0x00000080) == 0x00000080)) {
                size += CodedOutputStream.computeInt32Size(8, vc_);
            }
            if (((bitField0_ & 0x00000100) == 0x00000100)) {
                size += GeneratedMessage.computeStringSize(9, ea_);
            }
            if (((bitField0_ & 0x00000200) == 0x00000200)) {
                size += GeneratedMessage.computeStringSize(10, sku_);
            }
            if (((bitField0_ & 0x00000400) == 0x00000400)) {
                size += GeneratedMessage.computeStringSize(11, lc_);
            }
            if (((bitField0_ & 0x00000800) == 0x00000800)) {
                size += GeneratedMessage.computeStringSize(12, ct_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;

        public static ExchangeParams parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code com.zhh.proto.ExchangeParams}
         */
        public static final class Builder extends GeneratedMessage.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:com.zhh.proto.ExchangeParams)
                ExchangeParamsOrBuilder {
            public static final Descriptor getDescriptor() {
                return internal_static_com_zhh_proto_ExchangeParams_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_com_zhh_proto_ExchangeParams_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(ExchangeParams.class,
                                ExchangeParams.Builder.class);
            }

            // Construct using ExchangeParams.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                imei_ = "";
                bitField0_ = (bitField0_ & ~0x00000001);
                anid_ = "";
                bitField0_ = (bitField0_ & ~0x00000002);
                et_ = 0;
                bitField0_ = (bitField0_ & ~0x00000004);
                amount_ = 0;
                bitField0_ = (bitField0_ & ~0x00000008);
                a_ = 0;
                bitField0_ = (bitField0_ & ~0x00000010);
                b_ = 0;
                bitField0_ = (bitField0_ & ~0x00000020);
                c_ = 0;
                bitField0_ = (bitField0_ & ~0x00000040);
                vc_ = 0;
                bitField0_ = (bitField0_ & ~0x00000080);
                ea_ = "";
                bitField0_ = (bitField0_ & ~0x00000100);
                sku_ = "";
                bitField0_ = (bitField0_ & ~0x00000200);
                lc_ = "";
                bitField0_ = (bitField0_ & ~0x00000400);
                ct_ = "";
                bitField0_ = (bitField0_ & ~0x00000800);
                return this;
            }

            public Descriptor getDescriptorForType() {
                return internal_static_com_zhh_proto_ExchangeParams_descriptor;
            }

            public ExchangeParams getDefaultInstanceForType() {
                return ExchangeParams.getDefaultInstance();
            }

            public ExchangeParams build() {
                ExchangeParams result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public ExchangeParams buildPartial() {
                ExchangeParams result = new ExchangeParams(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.imei_ = imei_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.anid_ = anid_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.et_ = et_;
                if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
                    to_bitField0_ |= 0x00000008;
                }
                result.amount_ = amount_;
                if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
                    to_bitField0_ |= 0x00000010;
                }
                result.a_ = a_;
                if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
                    to_bitField0_ |= 0x00000020;
                }
                result.b_ = b_;
                if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
                    to_bitField0_ |= 0x00000040;
                }
                result.c_ = c_;
                if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
                    to_bitField0_ |= 0x00000080;
                }
                result.vc_ = vc_;
                if (((from_bitField0_ & 0x00000100) == 0x00000100)) {
                    to_bitField0_ |= 0x00000100;
                }
                result.ea_ = ea_;
                if (((from_bitField0_ & 0x00000200) == 0x00000200)) {
                    to_bitField0_ |= 0x00000200;
                }
                result.sku_ = sku_;
                if (((from_bitField0_ & 0x00000400) == 0x00000400)) {
                    to_bitField0_ |= 0x00000400;
                }
                result.lc_ = lc_;
                if (((from_bitField0_ & 0x00000800) == 0x00000800)) {
                    to_bitField0_ |= 0x00000800;
                }
                result.ct_ = ct_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ExchangeParams) {
                    return mergeFrom((ExchangeParams) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(ExchangeParams other) {
                if (other == ExchangeParams.getDefaultInstance())
                    return this;
                if (other.hasImei()) {
                    bitField0_ |= 0x00000001;
                    imei_ = other.imei_;
                    onChanged();
                }
                if (other.hasAnid()) {
                    bitField0_ |= 0x00000002;
                    anid_ = other.anid_;
                    onChanged();
                }
                if (other.hasEt()) {
                    setEt(other.getEt());
                }
                if (other.hasAmount()) {
                    setAmount(other.getAmount());
                }
                if (other.hasA()) {
                    setA(other.getA());
                }
                if (other.hasB()) {
                    setB(other.getB());
                }
                if (other.hasC()) {
                    setC(other.getC());
                }
                if (other.hasVc()) {
                    setVc(other.getVc());
                }
                if (other.hasEa()) {
                    bitField0_ |= 0x00000100;
                    ea_ = other.ea_;
                    onChanged();
                }
                if (other.hasSku()) {
                    bitField0_ |= 0x00000200;
                    sku_ = other.sku_;
                    onChanged();
                }
                if (other.hasLc()) {
                    bitField0_ |= 0x00000400;
                    lc_ = other.lc_;
                    onChanged();
                }
                if (other.hasCt()) {
                    bitField0_ |= 0x00000800;
                    ct_ = other.ct_;
                    onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                if (!hasImei()) {
                    return false;
                }
                if (!hasAnid()) {
                    return false;
                }
                if (!hasEt()) {
                    return false;
                }
                if (!hasAmount()) {
                    return false;
                }
                if (!hasA()) {
                    return false;
                }
                if (!hasB()) {
                    return false;
                }
                if (!hasC()) {
                    return false;
                }
                if (!hasVc()) {
                    return false;
                }
                if (!hasEa()) {
                    return false;
                }
                if (!hasSku()) {
                    return false;
                }
                if (!hasLc()) {
                    return false;
                }
                if (!hasCt()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input,
                    ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
                ExchangeParams parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ExchangeParams) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private Object imei_ = "";

            /**
             * <code>required string imei = 1;</code>
             */
            public boolean hasImei() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public String getImei() {
                Object ref = imei_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        imei_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public ByteString getImeiBytes() {
                Object ref = imei_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    imei_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public Builder setImei(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                imei_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public Builder clearImei() {
                bitField0_ = (bitField0_ & ~0x00000001);
                imei_ = getDefaultInstance().getImei();
                onChanged();
                return this;
            }

            /**
             * <code>required string imei = 1;</code>
             */
            public Builder setImeiBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                imei_ = value;
                onChanged();
                return this;
            }

            private Object anid_ = "";

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public boolean hasAnid() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public String getAnid() {
                Object ref = anid_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        anid_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public ByteString getAnidBytes() {
                Object ref = anid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    anid_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public Builder setAnid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                anid_ = value;
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public Builder clearAnid() {
                bitField0_ = (bitField0_ & ~0x00000002);
                anid_ = getDefaultInstance().getAnid();
                onChanged();
                return this;
            }

            /**
             * <pre>
             * str
             * </pre>
             *
             * <code>required string anid = 2;</code>
             */
            public Builder setAnidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                anid_ = value;
                onChanged();
                return this;
            }

            private int et_;

            /**
             * <code>required int32 et = 3;</code>
             */
            public boolean hasEt() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <code>required int32 et = 3;</code>
             */
            public int getEt() {
                return et_;
            }

            /**
             * <code>required int32 et = 3;</code>
             */
            public Builder setEt(int value) {
                bitField0_ |= 0x00000004;
                et_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 et = 3;</code>
             */
            public Builder clearEt() {
                bitField0_ = (bitField0_ & ~0x00000004);
                et_ = 0;
                onChanged();
                return this;
            }

            private int amount_;

            /**
             * <code>required int32 amount = 4;</code>
             */
            public boolean hasAmount() {
                return ((bitField0_ & 0x00000008) == 0x00000008);
            }

            /**
             * <code>required int32 amount = 4;</code>
             */
            public int getAmount() {
                return amount_;
            }

            /**
             * <code>required int32 amount = 4;</code>
             */
            public Builder setAmount(int value) {
                bitField0_ |= 0x00000008;
                amount_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 amount = 4;</code>
             */
            public Builder clearAmount() {
                bitField0_ = (bitField0_ & ~0x00000008);
                amount_ = 0;
                onChanged();
                return this;
            }

            private int a_;

            /**
             * <code>required int32 a = 5;</code>
             */
            public boolean hasA() {
                return ((bitField0_ & 0x00000010) == 0x00000010);
            }

            /**
             * <code>required int32 a = 5;</code>
             */
            public int getA() {
                return a_;
            }

            /**
             * <code>required int32 a = 5;</code>
             */
            public Builder setA(int value) {
                bitField0_ |= 0x00000010;
                a_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 a = 5;</code>
             */
            public Builder clearA() {
                bitField0_ = (bitField0_ & ~0x00000010);
                a_ = 0;
                onChanged();
                return this;
            }

            private int b_;

            /**
             * <code>required int32 b = 6;</code>
             */
            public boolean hasB() {
                return ((bitField0_ & 0x00000020) == 0x00000020);
            }

            /**
             * <code>required int32 b = 6;</code>
             */
            public int getB() {
                return b_;
            }

            /**
             * <code>required int32 b = 6;</code>
             */
            public Builder setB(int value) {
                bitField0_ |= 0x00000020;
                b_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 b = 6;</code>
             */
            public Builder clearB() {
                bitField0_ = (bitField0_ & ~0x00000020);
                b_ = 0;
                onChanged();
                return this;
            }

            private int c_;

            /**
             * <code>required int32 c = 7;</code>
             */
            public boolean hasC() {
                return ((bitField0_ & 0x00000040) == 0x00000040);
            }

            /**
             * <code>required int32 c = 7;</code>
             */
            public int getC() {
                return c_;
            }

            /**
             * <code>required int32 c = 7;</code>
             */
            public Builder setC(int value) {
                bitField0_ |= 0x00000040;
                c_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 c = 7;</code>
             */
            public Builder clearC() {
                bitField0_ = (bitField0_ & ~0x00000040);
                c_ = 0;
                onChanged();
                return this;
            }

            private int vc_;

            /**
             * <code>required int32 vc = 8;</code>
             */
            public boolean hasVc() {
                return ((bitField0_ & 0x00000080) == 0x00000080);
            }

            /**
             * <code>required int32 vc = 8;</code>
             */
            public int getVc() {
                return vc_;
            }

            /**
             * <code>required int32 vc = 8;</code>
             */
            public Builder setVc(int value) {
                bitField0_ |= 0x00000080;
                vc_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required int32 vc = 8;</code>
             */
            public Builder clearVc() {
                bitField0_ = (bitField0_ & ~0x00000080);
                vc_ = 0;
                onChanged();
                return this;
            }

            private Object ea_ = "";

            /**
             * <code>required string ea = 9;</code>
             */
            public boolean hasEa() {
                return ((bitField0_ & 0x00000100) == 0x00000100);
            }

            /**
             * <code>required string ea = 9;</code>
             */
            public String getEa() {
                Object ref = ea_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        ea_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string ea = 9;</code>
             */
            public ByteString getEaBytes() {
                Object ref = ea_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    ea_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string ea = 9;</code>
             */
            public Builder setEa(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000100;
                ea_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string ea = 9;</code>
             */
            public Builder clearEa() {
                bitField0_ = (bitField0_ & ~0x00000100);
                ea_ = getDefaultInstance().getEa();
                onChanged();
                return this;
            }

            /**
             * <code>required string ea = 9;</code>
             */
            public Builder setEaBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000100;
                ea_ = value;
                onChanged();
                return this;
            }

            private Object sku_ = "";

            /**
             * <code>required string sku = 10;</code>
             */
            public boolean hasSku() {
                return ((bitField0_ & 0x00000200) == 0x00000200);
            }

            /**
             * <code>required string sku = 10;</code>
             */
            public String getSku() {
                Object ref = sku_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        sku_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string sku = 10;</code>
             */
            public ByteString getSkuBytes() {
                Object ref = sku_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    sku_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string sku = 10;</code>
             */
            public Builder setSku(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000200;
                sku_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string sku = 10;</code>
             */
            public Builder clearSku() {
                bitField0_ = (bitField0_ & ~0x00000200);
                sku_ = getDefaultInstance().getSku();
                onChanged();
                return this;
            }

            /**
             * <code>required string sku = 10;</code>
             */
            public Builder setSkuBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000200;
                sku_ = value;
                onChanged();
                return this;
            }

            private Object lc_ = "";

            /**
             * <code>required string lc = 11;</code>
             */
            public boolean hasLc() {
                return ((bitField0_ & 0x00000400) == 0x00000400);
            }

            /**
             * <code>required string lc = 11;</code>
             */
            public String getLc() {
                Object ref = lc_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        lc_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string lc = 11;</code>
             */
            public ByteString getLcBytes() {
                Object ref = lc_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    lc_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string lc = 11;</code>
             */
            public Builder setLc(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000400;
                lc_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string lc = 11;</code>
             */
            public Builder clearLc() {
                bitField0_ = (bitField0_ & ~0x00000400);
                lc_ = getDefaultInstance().getLc();
                onChanged();
                return this;
            }

            /**
             * <code>required string lc = 11;</code>
             */
            public Builder setLcBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000400;
                lc_ = value;
                onChanged();
                return this;
            }

            private Object ct_ = "";

            /**
             * <code>required string ct = 12;</code>
             */
            public boolean hasCt() {
                return ((bitField0_ & 0x00000800) == 0x00000800);
            }

            /**
             * <code>required string ct = 12;</code>
             */
            public String getCt() {
                Object ref = ct_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString) ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        ct_ = s;
                    }
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>required string ct = 12;</code>
             */
            public ByteString getCtBytes() {
                Object ref = ct_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    ct_ = b;
                    return b;
                } else {
                    return (ByteString) ref;
                }
            }

            /**
             * <code>required string ct = 12;</code>
             */
            public Builder setCt(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000800;
                ct_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>required string ct = 12;</code>
             */
            public Builder clearCt() {
                bitField0_ = (bitField0_ & ~0x00000800);
                ct_ = getDefaultInstance().getCt();
                onChanged();
                return this;
            }

            /**
             * <code>required string ct = 12;</code>
             */
            public Builder setCtBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000800;
                ct_ = value;
                onChanged();
                return this;
            }

        }

        private static final ExchangeParams DEFAULT_INSTANCE;
        static {
            DEFAULT_INSTANCE = new ExchangeParams();
        }

        public static ExchangeParams getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        @Deprecated
        public static final Parser<ExchangeParams> PARSER = new AbstractParser<ExchangeParams>() {
            public ExchangeParams parsePartialFrom(CodedInputStream input,
                    ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ExchangeParams(input, extensionRegistry);
            }
        };

        public static Parser<ExchangeParams> parser() {
            return PARSER;
        }

        @Override
        public Parser<ExchangeParams> getParserForType() {
            return PARSER;
        }

        public ExchangeParams getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }
}
