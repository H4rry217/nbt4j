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
public class LongTag extends Tag{

    public final byte tagType = Tag.TAG_LONG;

    private long data = 0L;

    public LongTag(){
        this("");
    }

    public LongTag(String name){
        this(name, 0L);
    }

    public LongTag(String name, long data){
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

        try {
            baos.write(getPayLoad());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayLoad() {
        byte[] payload = new byte[8];

        payload[0] = (byte) ((this.data >> 56) & 0xFF);
        payload[0] = (byte) ((this.data >> 48) & 0xFF);
        payload[0] = (byte) ((this.data >> 40) & 0xFF);
        payload[0] = (byte) ((this.data >> 32) & 0xFF);
        payload[0] = (byte) ((this.data >> 24) & 0xFF);
        payload[0] = (byte) ((this.data >> 16) & 0xFF);
        payload[0] = (byte) ((this.data >> 8) & 0xFF);
        payload[0] = (byte) (this.data & 0xFF);

        return payload;
    }

}
