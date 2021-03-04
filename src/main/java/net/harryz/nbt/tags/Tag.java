package net.harryz.nbt.tags;

import java.nio.ByteOrder;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public abstract class Tag {

    public static final int TAG_END = 0;
    public static final int TAG_BYTE = 1;
    public static final int TAG_SHORT = 2;
    public static final int TAG_INT = 3;
    public static final int TAG_LONG = 4;
    public static final int TAG_FLOAT = 5;
    public static final int TAG_DOUBLE = 6;
    public static final int TAG_BYTE_ARRAY = 7;
    public static final int TAG_STRING = 8;
    public static final int TAG_LIST = 9;
    public static final int TAG_COMPOUND = 10;
    public static final int TAG_INT_ARRAY = 11;
    public static final int TAG_LONG_ARRAY = 12;

    public static final int TAG_LENGTH_TAGTYPE = 1;
    public static final int TAG_LENGTH_TAGNAME = 2;
    public static final int TAG_LENGTH_TAGDATA = 2;

    private String name;

    private ByteOrder byteOrder;

    public Tag(){
        this.setName("");
    }

    public Tag(String name){
        this.setName(name == null? "": name);
    }

    public abstract byte getTagType();

    public abstract byte[] toByteArray();

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
