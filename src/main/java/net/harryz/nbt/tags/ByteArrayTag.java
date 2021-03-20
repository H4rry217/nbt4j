package net.harryz.nbt.tags;

import net.harryz.nbt.exceptions.TagLoadErrorTypeException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class ByteArrayTag extends Tag{

    private byte[] dataBytes;

    public ByteArrayTag(){
        this("");
    }

    public ByteArrayTag(String name){
        super(name);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_BYTE_ARRAY);
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }

    @Override
    public Tag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public Tag load(ByteArrayInputStream bytes) throws TagLoadErrorTypeException {
        this.loadInfo(bytes);
        this.loadPayload(bytes);

        try {
            bytes.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public Tag loadPayload(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        this.loadPayload(bais);

        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public Tag loadPayload(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
        int length = 0;
        length += bais.read() << 24;
        length += bais.read() << 16;
        length += bais.read() << 8;
        length += bais.read();

        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) bais.read();
        }

        this.dataBytes = bytes;

        return this;
    }
}
