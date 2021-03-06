package net.harryz.nbt.tags;

import net.harryz.nbt.exceptions.TagLoadErrorTypeException;

import java.io.ByteArrayInputStream;
import java.nio.ByteOrder;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class EndTag extends Tag{

    public EndTag(){
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_END);
    }

    @Override
    public byte getTagType() {
        return this.tagType;
    }

    @Override
    public byte[] toByteArray(){
        return new byte[]{0};
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }

    @Override
    public EndTag load(byte[] bytes) {
        return this;
    }

    @Override
    public EndTag load(ByteArrayInputStream bytes) throws TagLoadErrorTypeException {
        return this;
    }

    @Override
    public EndTag loadPayload(byte[] bytes) {
        return this;
    }

    @Override
    public EndTag loadPayload(ByteArrayInputStream bais) {
        return this;
    }
}
