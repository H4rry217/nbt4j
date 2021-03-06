package net.harryz.nbt.exceptions;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class TagTypeException extends Error{

    TagTypeException(){
        super("Tag类型不被支持");
    }

}
