package net.harryz.nbt.tags;

import lombok.Data;
import lombok.SneakyThrows;
import net.harryz.nbt.NBTIO;
import net.harryz.nbt.exceptions.ListTagTypeErrorException;
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

@Data
public class ListTag<T extends Tag> extends Tag{

    public final byte tagType = Tag.TAG_LIST;

    private byte listValuesType = Tag.TAG_UNDEFINE;
    private List<Tag> list = new ArrayList<>();

    public ListTag(){
        this("");
    }

    public ListTag(String name){
        super(name);
        this.setByteOrder(ByteOrder.BIG_ENDIAN);
        this.setTagType(Tag.TAG_LIST);
    }

    @SneakyThrows
    public ListTag<T> add(Tag tag){
        if(this.listValuesType == Tag.TAG_UNDEFINE){
            this.list.add(tag);
            this.listValuesType = tag.getTagType();
        }else if(this.listValuesType == tag.getTagType()){
            this.list.add(tag);
        }else{
            throw new ListTagTypeErrorException();
        }

        return this;
    }

    @Override
    public byte getTagType() {
        return this.tagType;
    }

    @Override
    public byte[] toByteArray(){
        int nameLength = this.getName().length();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        /*tag type*/
        baos.write(this.tagType);

        /*name length*/
        baos.write((nameLength >> 8) & 0xFF);
        baos.write(nameLength & 0xFF);

        /*name*/
        try {
            baos.write(this.getName().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //payload
        try {
            baos.write(this.getPayload());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayload(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /*list tag type*/
        byteArrayOutputStream.write( this.listValuesType);

        /*list size*/
        byteArrayOutputStream.write( this.list.size() >> 24 & 0xff);
        byteArrayOutputStream.write( this.list.size() >> 16 & 0xff);
        byteArrayOutputStream.write( this.list.size() >> 8 & 0xff);
        byteArrayOutputStream.write( this.list.size() & 0xff);

        /*list tag value*/
        for(Tag tag: this.list){
            try {
                byteArrayOutputStream.write(tag.getPayload());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public ListTag<T> load(byte[] bytes) throws TagLoadErrorTypeException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return this.load(bais);
    }

    @Override
    public ListTag<T> load(ByteArrayInputStream bais) throws TagLoadErrorTypeException {
        this.loadInfo(bais);

        this.listValuesType = (byte) bais.read();

        int listLength = 0;
        listLength += bais.read() << 24;
        listLength += bais.read() << 16;
        listLength += bais.read() << 8;
        listLength += bais.read();

        for (int i = 0; i < listLength; i++) {
            Tag tag = NBTIO.createTag(this.listValuesType);
            tag.loadPayload(bais);

            this.add(tag);
        }

        return this;
    }

    @Override
    public ListTag<T> loadPayload(byte[] bytes) {
        return null;
    }

    @Override
    public Tag loadPayload(ByteArrayInputStream bais) {
        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("ListTag");
        sb.append('(')
                .append("name=").append(this.getName()).append(", ")
                .append("size=").append(this.list.size()).append("\n");

        for (Tag tag : this.list) {
            sb.append("   ").append(tag.toString()).append("\n");
        }

        return sb.append(')').toString();
    }

}
