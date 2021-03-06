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
public class StringTag extends Tag{

    private String data = "";

    public StringTag(){
        this("");
    }

    public StringTag(String name){
        this(name, "");
    }

    public StringTag(String name, String data){
        super(name);
        this.setData(data);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_STRING);
    }

    @Override
    public byte getTagType(){
        return this.tagType;
    }

    @Override
    public byte[] toByteArray() {
        int nameLength = this.getName().length();
        int dataLength = this.data.length();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(this.tagType);

        baos.write((nameLength >> 8) & 0xFF);
        baos.write(nameLength & 0xFF);
        //

        try {
            baos.write(this.getName().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        baos.write(dataLength >>> 8);
        baos.write(dataLength ^ (dataLength >>> 8 << 8));

        try {
            baos.write(this.data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayload() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(this.data.length() >>> 8);
        byteArrayOutputStream.write(this.data.length() ^ (this.data.length() >>> 8 << 8));

        try {
            byteArrayOutputStream.write(this.data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public StringTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public StringTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
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
    public StringTag loadPayload(byte[] bytes) {
        int length = 0;
        length += bytes[0] << 8;
        length += bytes[1];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char)bytes[2 + i]);
        }

        this.setData(sb.toString());
        return this;
    }

    @Override
    public StringTag loadPayload(ByteArrayInputStream bais) {
        int length = 0;
        length += bais.read() << 8;
        length += bais.read();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char)bais.read());
        }

        this.setData(sb.toString());
        return this;
    }

}
