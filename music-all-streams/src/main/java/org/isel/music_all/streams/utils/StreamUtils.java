package org.isel.music_all.streams.utils;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.isel.leirt.music_all.Errors.TODO;

public class StreamUtils {
    
    public static <T> Optional<T> findLast(Stream<T> str) {
        return str.reduce((t, t2) -> t2);
    }
   
    public static <T> Supplier<Stream<T>> cache(Stream<T> src) {
       TODO("cache");
       return null;
    }
 
    public  static <T,U,V> Stream<V> intersection(
        Stream<T> seq1,
        Stream<U> seq2,
        BiPredicate<T,U> matched,
        BiFunction<T,U, V> mapper) {

        TODO("intersection");
        return null;
    }
}
