package org.isel.music_all.streams.utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
    
    public static <T> Optional<T> findLast(Stream<T> str) {
        return str.reduce((t, t2) -> t2);
    }
   
    public static <T> Supplier<Stream<T>> cache(Stream<T> src) {
        List<T> cachedValues = new ArrayList<>();
        Iterator<T> srcIterator = src.iterator();

        return () -> Stream.concat(
                cachedValues.stream(),
                StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(
                                new Iterator<>() {
                                    private int count = 0;

                                    @Override
                                    public boolean hasNext() {
                                        return srcIterator.hasNext();
                                    }

                                    @Override
                                    public T next() {
                                        if (cachedValues.size() <= count) {
                                            T next = srcIterator.next();
                                            cachedValues.add(next);
                                        }

                                        return cachedValues.get(count++);
                                    }
                                },
                                Spliterator.ORDERED
                        ),
                        false
                )
        );
    }

    public static <T,U,V> Stream<V> intersection(
        Stream<T> seq1,
        Stream<U> seq2,
        BiPredicate<T,U> matched,
        BiFunction<T,U, V> mapper) {

        Stream<T> cachedSeq1 = cache(seq1).get();
        Supplier<Stream<U>> supCachedSeq2 = cache(seq2);

        return cachedSeq1.flatMap(t -> supCachedSeq2.get().filter(u -> matched.test(t, u)).map(u -> mapper.apply(t, u)));
    }
}
