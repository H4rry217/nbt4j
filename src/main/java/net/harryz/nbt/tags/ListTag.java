package net.harryz.nbt.tags;

import lombok.Data;
import net.harryz.nbt.exceptions.ListTagTypeErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    }

    public ListTag<T> add(Tag tag) throws ListTagTypeErrorException {
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
            baos.write(this.getPayLoad());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    @Override
    public byte[] getPayLoad() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /*list tag type*/
        byteArrayOutputStream.write( this.listValuesType);

        /*list size*/
        byteArrayOutputStream.write( list.size() >> 24 & 0xff);
        byteArrayOutputStream.write( list.size() >> 16 & 0xff);
        byteArrayOutputStream.write( list.size() >> 8 & 0xff);
        byteArrayOutputStream.write( list.size() & 0xff);

        /*list tag value*/
        for(Tag tag: list){
            byteArrayOutputStream.write(tag.getPayLoad());
        }

        return byteArrayOutputStream.toByteArray();
    }

}
