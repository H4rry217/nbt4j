package net.harryz.nbt.tags;

import net.harryz.nbt.NBTIO;
import net.harryz.nbt.exceptions.TagLoadErrorTypeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.*;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class CompoundTag extends Tag{

    private final Map<String, Tag> tags = new HashMap<>();

    public CompoundTag(){
        this("");
    }

    public CompoundTag(String name){
        super(name);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_COMPOUND);
    }

    public CompoundTag put(Tag tag){
        this.tags.put(tag.getName(), tag);
        return this;
    }

    public Tag get(String key){
        return this.tags.get(key);
    }

    public boolean exists(String key){
        return this.tags.containsKey(key);
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

        for (Tag tag: this.tags.values()){
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
        for (Tag tag: this.tags.values()){
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
                this.tags.put(tag.getName(), tag);
            }

            if(tag == null){
                System.out.println(val);
                int val1 = -1;
                for (int i = 0; i < 50; i++) {
                    val1 = (bais.read());
                    System.out.println(val1 + "   "+(char)val1);
                }
                while(true){}
            }

            bais.mark(0);
        }

        return this;
    }

    @Override
    public String toString(){
        StringJoiner joiner = new StringJoiner(",\n\t");
        this.tags.forEach((key, tag) -> {
            joiner.add('\'' + key + "' : " + tag.toString().replace("\n", "\n\t"));
        });
        return "CompoundTag '" + this.getName() + "' (" + this.tags.size() + " entries) {\n\t" + joiner.toString() + "\n}";
    }
}
