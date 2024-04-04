package pt.leirt.mpd;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class QueryUtils {
    public static <T, U> U reduce(Iterable<T> src, U initial, BiFunction<U, T, U> combiner) {
        U result = initial;

        for (T element : src)
            result = combiner.apply(result, element);

        return result;
    }

    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred) {
        var result = new ArrayList<T>();

        for (var f : src) {
            if (pred.test(f))
                result.add(f);
        }

        return result;
    }

    public static <T,U> Iterable<U> map(Iterable<T> src, Function<T,U> mapper) {
        var res = new ArrayList<U>();

        for (var e: src)
            res.add(mapper.apply(e));

        return res;
    }

    public static <T,U> Iterable<U> flatMap(Iterable<T> src, Function<T,Iterable<U>> mapper) {
        var res = new ArrayList<U>();

        for (var e: src) {
            for (var u : mapper.apply(e))
                res.add(u);
        }

        return res;
    }
}