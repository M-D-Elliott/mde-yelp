package jPlus.generic;

import java.util.HashMap;
import java.util.Map;

public class HashMap3<KEY, T1, T2, T3> {

    public final Map<KEY, T1> t1 = mapImpl();

    public final Map<KEY, T2> t2 = mapImpl();

    public final Map<KEY, T3> t3 = mapImpl();

    protected <K, V> Map<K, V> mapImpl() {
        return new HashMap<>();
    }

    public void clear() {
        t1.clear();
        t2.clear();
        t3.clear();
    }
}
