package pt.leirt.mpd.products;

import java.util.function.BiFunction;

public class QueryUtils {
    public static <T, U> U reduce(Iterable<T> src, U initial, BiFunction<U, T, U> combiner) {
        U result = initial;
        for (T element : src) {
            result = combiner.apply(result, element);
        }
        return result;
    }
}