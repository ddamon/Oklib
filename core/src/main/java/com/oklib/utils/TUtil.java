package com.oklib.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 */

public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            Type genType = o.getClass().getGenericSuperclass();
            if (!(genType instanceof ParameterizedType)) {
                return null;
            }
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            if (i >= params.length || i < 0) {
                return null;
            }
            if (!(params[i] instanceof Class)) {
                return null;
            }
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

