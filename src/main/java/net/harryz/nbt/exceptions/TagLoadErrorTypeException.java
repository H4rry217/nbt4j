package net.harryz.nbt.exceptions;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class TagLoadErrorTypeException extends Exception{

    public TagLoadErrorTypeException(){
        super("字节数组中的数据以及格式，无法被当前Tag加载");
    }

}
