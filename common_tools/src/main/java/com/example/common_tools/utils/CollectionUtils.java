package com.example.common_tools.utils;

import java.util.List;

public class CollectionUtils {
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }
}
