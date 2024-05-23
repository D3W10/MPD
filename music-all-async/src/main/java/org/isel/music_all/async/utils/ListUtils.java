package org.isel.music_all.async.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> List<T> concat(List<T> l1, List<T> l2) {
        var lr =   new ArrayList<>(l1);
        lr.addAll(l2);
        return lr;
    }
}
