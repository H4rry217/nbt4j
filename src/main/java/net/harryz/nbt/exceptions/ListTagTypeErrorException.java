package net.harryz.nbt.exceptions;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class ListTagTypeErrorException extends Exception{

    public ListTagTypeErrorException(){
        super("尝试往ListTag中添加一个类型不相同的子Tag");
    }

}
