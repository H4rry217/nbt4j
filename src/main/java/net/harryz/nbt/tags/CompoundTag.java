package net.harryz.nbt.tags;

import net.harryz.nbt.NBTIO;
import net.harryz.nbt.exceptions.TagLoadErrorTypeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class CompoundTag extends Tag{

    private final List<Tag> tags = new ArrayList<>();

    public CompoundTag(){
        this("");
    }

    public CompoundTag(String name){
        super(name);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_COMPOUND);
    }

    public CompoundTag put(Tag tag){
        this.tags.add(tag);
        return this;
    }

    @Override
    public byte getTagType() {
        return this.tagType;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(this.tagType);

        baos.write((this.getName().length() >> 8) & 0xFF);
        baos.write(this.getName().length() & 0xFF);

        try {
            baos.write(this.getName().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Tag tag: tags){
            try {
                baos.write(tag.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        baos.write(Tag.TAG_END);

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (Tag tag: tags){
            try {
                baos.write(tag.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

    @Override
    public CompoundTag load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public CompoundTag load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
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
    public CompoundTag loadPayload(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        this.loadPayload(bais);

        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public CompoundTag loadPayload(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
        bais.mark(0);

        int val = -1;
        while((val = bais.read()) != -1){
            if(val == 0) break;

            bais.reset();

            Tag tag = NBTIO.createTag((byte) val);

            if(tag != null) {
                tag.load(bais);
                this.tags.add(tag);
            }

            bais.mark(0);
        }

        return this;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("CompoundTag");
        sb.append('(')
                .append("name=").append(this.getName()).append(", ")
                .append("size=").append(this.tags.size()).append("\n");

        for (Tag tag : tags) {
            sb.append("   ").append(tag.toString()).append("\n");
        }

        return sb.append(')').toString();
    }
}
