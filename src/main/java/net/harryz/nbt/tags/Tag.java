package net.harryz.nbt.tags;

import net.harryz.nbt.exceptions.TagLoadErrorTypeException;

import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public abstract class Tag {

    public static final byte TAG_END = 0;
    public static final byte TAG_BYTE = 1;
    public static final byte TAG_SHORT = 2;
    public static final byte TAG_INT = 3;
    public static final byte TAG_LONG = 4;
    public static final byte TAG_FLOAT = 5;
    public static final byte TAG_DOUBLE = 6;
    public static final byte TAG_BYTE_ARRAY = 7;
    public static final byte TAG_STRING = 8;
    public static final byte TAG_LIST = 9;
    public static final byte TAG_COMPOUND = 10;
    public static final byte TAG_INT_ARRAY = 11;
    public static final byte TAG_LONG_ARRAY = 12;
    public static final byte TAG_UNKNOWN = 20;
    public static final byte TAG_UNDEFINE = 21;

    private String name;

    private ByteOrder byteOrder;

    protected byte tagType;

    public Tag(){
        this.setName("");
    }

    public Tag(String name){
        this.setName(name == null? "": name);
    }

    public byte getTagType(){
        return this.tagType;
    }

    protected void setTagType(byte type){
        this.tagType = type;
    }

    public abstract byte[] toByteArray();

    public abstract byte[] getPayload();

    public abstract Tag load(byte[] bytes) throws TagLoadErrorTypeException;

    public abstract Tag load(ByteArrayInputStream bytes) throws TagLoadErrorTypeException;

    public abstract Tag loadPayload(byte[] bytes) throws TagLoadErrorTypeException;

    public abstract Tag loadPayload(ByteArrayInputStream bais) throws TagLoadErrorTypeException;

    public void loadInfo(byte[] bytes) throws TagLoadErrorTypeException{
        if(this.tagType != bytes[0]){
            throw new TagLoadErrorTypeException();
        }

        int nameLength = (bytes[1] << 8) + bytes[2];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nameLength; i++) {
            sb.append((char)bytes[3 + i]);
        }

        this.setName(sb.toString());
    };

    public void loadInfo(ByteArrayInputStream bais) throws TagLoadErrorTypeException{
        byte tagType = (byte) bais.read();

        if(tagType != this.tagType){
            throw new TagLoadErrorTypeException();
        }

        int nameLength = (bais.read() << 8) + bais.read();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nameLength; i++) {
            sb.append((char)bais.read());
        }

        this.setName(sb.toString());
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }
}
