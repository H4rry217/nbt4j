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
public class DoubleTag extends Tag{

    public final byte tagType = Tag.TAG_DOUBLE;

    private double data = 0D;

    public DoubleTag(String name){
        this(name, 0D);
    }

    public DoubleTag(String name, double data){
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
        long doubleBit = Double.doubleToLongBits(this.data);
        byte[] payload = new byte[8];

        payload[0] = (byte) ((doubleBit >> 56) & 0xFF);
        payload[1] = (byte) ((doubleBit >> 48) & 0xFF);
        payload[2] = (byte) ((doubleBit >> 40) & 0xFF);
        payload[3] = (byte) ((doubleBit >> 32) & 0xFF);
        payload[4] = (byte) ((doubleBit >> 24) & 0xFF);
        payload[5] = (byte) ((doubleBit >> 16) & 0xFF);
        payload[6] = (byte) ((doubleBit >> 8) & 0xFF);
        payload[7] = (byte) (doubleBit & 0xFF);

        return payload;
    }
}
