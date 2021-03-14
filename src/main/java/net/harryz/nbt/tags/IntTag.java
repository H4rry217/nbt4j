package net.harryz.nbt.tags;

import lombok.Data;
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

@Data
public class IntTag extends Tag{

    private int data = 0;

    public IntTag(){
        this("");
    }

    public IntTag(String name){
        this(name, 0);
    }

    public IntTag(String name, int data){
        super(name);
        this.setData(data);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_INT);
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
        byte[] payload = new byte[4];

        payload[0] = (byte) ((this.data >> 24) & 0xFF);
        payload[1] = (byte) ((this.data >> 16) & 0xFF);
        payload[2] = (byte) ((this.data >> 8) & 0xFF);
        payload[3] = (byte) (this.data & 0xFF);

        return payload;
    }

    @Override
    public IntTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public IntTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
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
    public IntTag loadPayload(byte[] bytes) {
        int value = 0;
        value += bytes[0] << 24;
        value += bytes[1] << 16;
        value += bytes[2] << 8;
        value += bytes[3];

        this.data = value;
        return this;
    }

    @Override
    public IntTag loadPayload(ByteArrayInputStream bais) {
        int value = 0;
        value += bais.read() << 24;
        value += bais.read() << 16;
        value += bais.read() << 8;
        value += bais.read();

        this.data = value;
        return this;
    }

    @Override
    public String toString(){
        return (new StringBuilder("IntTag"))
                .append('(')
                .append("name=").append(this.getName()).append(", ")
                .append("data=").append(this.data)
                .append(')').toString();
    }

}
