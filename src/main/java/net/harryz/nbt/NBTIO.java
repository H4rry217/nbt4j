package net.harryz.nbt;

import lombok.SneakyThrows;
import net.harryz.nbt.tags.*;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class NBTIO {

    public static Tag createTag(byte tagType){
        Tag tag = null;

        switch(tagType){
            case Tag.TAG_END:{
                tag = new EndTag();
                break;
            }
            case Tag.TAG_BYTE:{
                tag = new ByteTag();
                break;
            }
            case Tag.TAG_SHORT:{
                tag = new ShortTag();
                break;
            }
            case Tag.TAG_INT:{
                tag = new IntTag();
                break;
            }
            case Tag.TAG_LONG:{
                tag = new LongTag();
                break;
            }
            case Tag.TAG_FLOAT:{
                tag = new FloatTag();
                break;
            }
            case Tag.TAG_DOUBLE:{
                tag = new DoubleTag();
                break;
            }
            case Tag.TAG_BYTE_ARRAY:{
                tag = new ByteArrayTag();
                break;
            }
            case Tag.TAG_STRING:{
                tag = new StringTag();
                break;
            }
            case Tag.TAG_LIST:{
                tag = new ListTag<>();
                break;
            }
            case Tag.TAG_COMPOUND:{
                tag = new CompoundTag();
            }
            case Tag.TAG_INT_ARRAY:{
                //TODO
                break;
            }
            case Tag.TAG_LONG_ARRAY:{
                //TODO
                break;
            }
        }

        return tag;
    }

    @SneakyThrows
    public static Tag readCompressGZIP(File file) throws ZipException{
        InputStream gzipStream;
        try {
            gzipStream = new GZIPInputStream(new FileInputStream(file));
        }catch (ZipException e){
            throw e;
        }

        BufferedInputStream bis = new BufferedInputStream(gzipStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int len = -1;
        while((len = bis.read()) != -1){
            baos.write(len);
        }

        return new CompoundTag().load(baos.toByteArray());
    }

}
