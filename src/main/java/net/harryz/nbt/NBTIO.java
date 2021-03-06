package net.harryz.nbt;

import net.harryz.nbt.tags.*;

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
            }
            case Tag.TAG_BYTE_ARRAY:{
                //TODO
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

}
