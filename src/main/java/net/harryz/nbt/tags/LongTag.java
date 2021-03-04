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
        int dataLength = 8;

        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                Tag.TAG_LENGTH_TAGTYPE + Tag.TAG_LENGTH_TAGNAME + nameLength + Tag.TAG_LENGTH_TAGDATA + dataLength
        );

        baos.write(this.tagType);

        baos.write(nameLength >>> 8);
        baos.write(nameLength ^ (nameLength >>> 8 << 8));

        try {
            baos.write(this.getName().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        baos.write((int) ((this.data >> 56) & 0xFF));
        baos.write((int) ((this.data >> 48) & 0xFF));
        baos.write((int) ((this.data >> 40) & 0xFF));
        baos.write((int) ((this.data >> 32) & 0xFF));
        baos.write((int) ((this.data >> 24) & 0xFF));
        baos.write((int) ((this.data >> 16) & 0xFF));
        baos.write((int) (this.data & 0xFF));

        return baos.toByteArray();
    }
}
