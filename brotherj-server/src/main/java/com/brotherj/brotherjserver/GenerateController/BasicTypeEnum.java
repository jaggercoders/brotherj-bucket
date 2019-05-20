package com.brotherj.brotherjserver.GenerateController;

import java.util.Arrays;

/**
 * description：
 *
 * @author wangjie
 */
public enum BasicTypeEnum {

    INTEGER(Integer.class),
    LONG(Long.class),
    DOUBLE(Double.class),
    FLOAT(Float.class),
    CHAR(Character.class),
    BYTE(Byte.class),
    SHORT(Short.class),
    BOOLEAN(Boolean.class),
    STRING(String.class),
    ;

    private Class<?> clazz;

    public Class<?> getClazz() {
        return clazz;
    }

    BasicTypeEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static boolean containThis(Class<?> clazz){
        return Arrays.stream(BasicTypeEnum.values()).map(BasicTypeEnum::getClazz).anyMatch(x->x.getCanonicalName().equals(clazz.getCanonicalName()));
    }

}
