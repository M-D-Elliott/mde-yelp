package jPlus.util;

import java.util.Arrays;

public final class ArrayUtils {
    public static Integer[][] rotateCCW(Integer[][] arr) {
        return (arr.length > 0)
                ? rotateCCW(arr, new Integer[arr[0].length][arr.length])
                : arr;
    }

    public static <E> E[][] rotateCCW(E[][] arr, E[][] ret) {
        final int outerL = arr.length;
        if (outerL > 0) {
            for (int i = 0; i < arr[0].length; ++i)
                for (int j = 0; j < outerL; ++j)
                    ret[i][j] = arr[outerL - j - 1][i]; //***

            return ret;
        } else return arr;
    }

    public static <E> boolean twoDArrEquals(E[][] arr1, E[][] arr2) {
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) if (!Arrays.equals(arr1[i], arr2[i])) return false;

        return true;
    }

    public static Integer[][] filled2D(int outer, int inner, int v) {
        final Integer[][] ret = new Integer[outer][inner];
        for (Integer[] innerArr : ret)
            Arrays.fill(innerArr, v);
        return ret;
    }
}
