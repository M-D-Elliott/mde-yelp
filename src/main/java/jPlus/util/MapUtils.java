package jPlus.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author P. Furey
 * Refactored from previous class by Marcus at some point?
 */
public final class MapUtils {

    public static <K, V> HashMap<K, V> deepCopy(Map<K, V> original) {
        final HashMap<K, V> ret = new HashMap<>();

        for (Map.Entry<K, V> entry : original.entrySet()) ret.put(entry.getKey(), entry.getValue());

        return ret;
    }

    public static <F, E> F getKeyByValue(Map<F, E> map, E value) {
        for (Map.Entry<F, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <T> LinkedHashMap<String, Object> asObjectMap(T obj) {
        final LinkedHashMap<String, Object> ret = new LinkedHashMap<>();

        try {
            for (Field f : obj.getClass().getDeclaredFields())
                if (Modifier.isPublic(f.getModifiers()))
                    ret.put(f.getName(), f.get(obj));

            for (Method m : obj.getClass().getMethods())
                if (Modifier.isPublic(m.getModifiers()) && m.getName().startsWith("get") && !m.getName().equals("getClass"))
                    ret.put(
                            StringUtils.lowerCaseFirstLetter(m.getName().replace("get", "")),
                            m.invoke(obj));
        } catch (IllegalAccessException | InvocationTargetException ex) {

        }

        return ret;
    }
}
