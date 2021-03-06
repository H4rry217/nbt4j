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
public class LongTag extends Tag{

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
        this.setTagType(Tag.TAG_LONG);
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

    @Override
    public LongTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return load(bais);
    }

    @Override
    public LongTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
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
    public LongTag loadPayload(byte[] bytes) {
        long value = 0;
        value += (long)bytes[0] << 56;
        value += (long)bytes[1] << 48;
        value += (long)bytes[2] << 40;
        value += (long)bytes[3] << 32;
        value += bytes[4] << 24;
        value += bytes[5] << 16;
        value += bytes[6] << 8;
        value += bytes[7];

        this.data = value;
        return this;
    }

    @Override
    public LongTag loadPayload(ByteArrayInputStream bais) {
        long value = 0;
        value += (long)bais.read() << 56;
        value += (long)bais.read() << 48;
        value += (long)bais.read() << 40;
        value += (long)bais.read() << 32;
        value += bais.read() << 24;
        value += bais.read() << 16;
        value += bais.read() << 8;
        value += bais.read();

        this.data = value;
        return this;
    }

}
