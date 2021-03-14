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
public class ByteTag extends Tag{

    private int data = 0;

    public ByteTag(){
        this("");
    }

    public ByteTag(String name){
        this(name, 0);
    }

    public ByteTag(String name, int data){
        super(name);
        this.setData(data);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_BYTE);
    }

    public void setData(int data){
        this.data = data > 0? 1: 0;
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
        return new byte[]{this.tagType};
    }

    @Override
    public ByteTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public ByteTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
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
    public ByteTag loadPayload(byte[] bytes){
        this.data = bytes[0];
        return this;
    }

    @Override
    public ByteTag loadPayload(ByteArrayInputStream bais) {
        this.data = bais.read();
        return this;
    }

    @Override
    public String toString(){
        return (new StringBuilder("ByteTag"))
                .append('(')
                .append("name=").append(this.getName()).append(", ")
                .append("data=").append(this.data)
                .append(')').toString();
    }

}
