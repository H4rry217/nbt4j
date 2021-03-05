package net.harryz.nbt.tags;

import lombok.Data;

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

    public final byte tagType = Tag.TAG_INT;

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

        baos.write((this.data >> 24) & 0xFF);
        baos.write((this.data >> 16) & 0xFF);
        baos.write((this.data >> 8) & 0xFF);
        baos.write(this.data & 0xFF);

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayLoad() {
        byte[] payload = new byte[4];

        payload[0] = (byte) ((this.data >> 24) & 0xFF);
        payload[1] = (byte) ((this.data >> 16) & 0xFF);
        payload[2] = (byte) ((this.data >> 8) & 0xFF);
        payload[3] = (byte) (this.data & 0xFF);

        return payload;
    }

}
