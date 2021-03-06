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
public class DoubleTag extends Tag{

    private double data = 0D;

    public DoubleTag(){
        this("");
    }

    public DoubleTag(String name){
        this(name, 0D);
    }

    public DoubleTag(String name, double data){
        super(name);
        this.setData(data);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_DOUBLE);
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
            baos.write(getPayload());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayload() {
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

    @Override
    public DoubleTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public DoubleTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
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
    public DoubleTag loadPayload(byte[] bytes) {
        long longBit = 0L;
        longBit += (long)bytes[0] << 56;
        longBit += (long)bytes[1] << 48;
        longBit += (long)bytes[2] << 40;
        longBit += (long)bytes[3] << 32;
        longBit += bytes[4] << 24;
        longBit += bytes[5] << 16;
        longBit += bytes[6] << 8;
        longBit += bytes[7];

        this.data = Double.longBitsToDouble(longBit);
        return this;
    }

    @Override
    public DoubleTag loadPayload(ByteArrayInputStream bais) {
        long longBit = 0L;
        longBit += (long)bais.read() << 56;
        longBit += (long)bais.read() << 48;
        longBit += (long)bais.read() << 40;
        longBit += (long)bais.read() << 32;
        longBit += bais.read() << 24;
        longBit += bais.read() << 16;
        longBit += bais.read() << 8;
        longBit += bais.read();

        this.data = Double.longBitsToDouble(longBit);
        return this;
    }
}
