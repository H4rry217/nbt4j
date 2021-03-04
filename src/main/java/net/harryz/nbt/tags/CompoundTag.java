package net.harryz.nbt.tags;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: nbt4j
 * @description:
 * @author: H4rry217
 **/

public class CompoundTag extends Tag{

    private final Map<String, Tag> tags = new HashMap();

    public Tag write(Tag tag) {
        return null;
    }

    @Override
    public byte getTagType() {
        return 0;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
