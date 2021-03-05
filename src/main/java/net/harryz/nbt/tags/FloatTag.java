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
public class FloatTag extends Tag{

    public final byte tagType = Tag.TAG_FLOAT;

    private float data = 0F;

    public FloatTag(){
        this("");
    }

    public FloatTag(String name){
        this(name, 0F);
    }

    public FloatTag(String name, float data){
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

        int floatBit = Float.floatToIntBits(this.data);
        baos.write((floatBit >> 24) & 0xFF);
        baos.write((floatBit >> 16) & 0xFF);
        baos.write((floatBit >> 8) & 0xFF);
        baos.write(floatBit & 0xFF);

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayLoad() {
        int floatBit = Float.floatToIntBits(this.data);
        byte[] payload = new byte[4];

        payload[0] = (byte) ((floatBit >> 24) & 0xFF);
        payload[1] = (byte) ((floatBit >> 16) & 0xFF);
        payload[2] = (byte) ((floatBit >> 8) & 0xFF);
        payload[3] = (byte) (floatBit & 0xFF);

        return payload;
    }
}
