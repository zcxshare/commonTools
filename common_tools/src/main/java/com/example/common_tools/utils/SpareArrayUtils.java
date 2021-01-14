package com.example.common_tools.utils;

import android.util.SparseArray;
import android.util.SparseIntArray;


public class SpareArrayUtils {
    public static boolean isEmpty(SparseArray map) {
        return map == null || map.size() == 0;
    }
    public static boolean isEmpty(SparseIntArray map) {
        return map == null || map.size() == 0;
    }
}
