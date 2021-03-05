package net.harryz.nbt.tags;

import lombok.Data;

import java.io.BufferedOutputStream;
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

    public final byte tagType = Tag.TAG_STRING;

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
    public byte[] getPayLoad() {
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

}
