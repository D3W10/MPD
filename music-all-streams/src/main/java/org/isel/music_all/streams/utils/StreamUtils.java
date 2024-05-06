package org.isel.music_all.streams.utils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamUtils {
    
    public static <T> Optional<T> findLast(Stream<T> str) {
        return str.reduce((t, t2) -> t2);
    }
   
    public static <T> Supplier<Stream<T>> cache(Stream<T> src) {
        List<T> cachedValues = src.toList();

        return cachedValues::stream;
    }
 
    public  static <T,U,V> Stream<V> intersection(
        Stream<T> seq1,
        Stream<U> seq2,
        BiPredicate<T,U> matched,
        BiFunction<T,U, V> mapper) {

        Stream<T> cachedSeq1 = cache(seq1).get();
        Stream<U> cachedSeq2 = cache(seq2).get();

        return cachedSeq1.flatMap(t -> cachedSeq2.filter(u -> matched.test(t, u)).map(u -> mapper.apply(t, u)));
    }
}
