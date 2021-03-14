package net.harryz.nbt.tags;

import net.harryz.nbt.exceptions.TagLoadErrorTypeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class ShortTag extends Tag{

    private int data = 0;

    public ShortTag(){
        this("");
    }

    public ShortTag(String name){
        this(name, 0);
    }

    public ShortTag(String name, int shortValue){
        super(name);
        this.data = shortValue;
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_SHORT);
    }

    @Override
    public byte getTagType() {
        return this.tagType;
    }

    @Override
    public byte[] toByteArray() {
        int nameLength = this.getName().length();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(this.tagType);

        baos.write((nameLength >> 8) & 0xFF);
        baos.write(nameLength & 0xFF);

        try {
            baos.write(this.getName().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            baos.write(this.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return baos.toByteArray();
    }

    @Override
    public byte[] getPayload() {
        byte[] payload = new byte[2];

        payload[0] = (byte) ((this.data >> 8) & 0xFF);
        payload[1] = (byte) (this.data & 0xFF);

        return payload;
    }

    @Override
    public ShortTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public ShortTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
        this.loadInfo(bais);
        this.loadPayload(bais);

        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public ShortTag loadPayload(byte[] bytes) {
        int value = 0;
        value += bytes[0] << 8;
        value += bytes[1];

        this.data = value;
        return this;
    }

    @Override
    public ShortTag loadPayload(ByteArrayInputStream bais) {
        int value = 0;
        value += bais.read() << 8;
        value += bais.read();

        this.data = value;
        return this;
    }

    @Override
    public String toString(){
        return (new StringBuilder("ShortTag"))
                .append('(')
                .append("name=").append(this.getName()).append(", ")
                .append("data=").append(this.data)
                .append(')').toString();
    }

}
