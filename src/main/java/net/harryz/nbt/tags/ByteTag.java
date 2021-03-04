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
public class ByteTag extends Tag{

    public final byte tagType = Tag.TAG_BYTE;

    private int data = 0;

    public ByteTag(){
        this("");
    }

    public ByteTag(String name){
        this(name, 0);
    }

    public ByteTag(String name, int data){
        super(name);
        this.setData(data);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
    }

    public void setData(int data){
        this.data = data > 0? 1: 0;
    }

    @Override
    public byte getTagType(){
        return this.tagType;
    }

    @Override
    public byte[] toByteArray() {
        int nameLength = this.getName().length();
        int dataLength = 4;

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

        baos.write(this.data);

        return baos.toByteArray();
    }
}
