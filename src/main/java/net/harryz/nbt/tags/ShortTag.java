package net.harryz.nbt.tags;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class ShortTag extends Tag{

    public final byte tagType = Tag.TAG_SHORT;

    private int data = 1;

    @Override
    public byte getTagType() {
        return this.tagType;
    }

    @Override
    public byte[] toByteArray() {
        int nameLength = this.getName().length();
        int dataLength = 2;

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
            baos.write(this.getPayLoad());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return baos.toByteArray();
    }

    @Override
    public byte[] getPayLoad() {
        byte[] payload = new byte[2];

        payload[0] = (byte) ((this.data >> 8) & 0xFF);
        payload[1] = (byte) (this.data & 0xFF);

        return payload;
    }
}
