package org.isel.music_all.streams;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.isel.music_all.streams.utils.StreamUtils.cache;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamUtilsTests {
    @Test
    public void testCache() {
        Random r = new Random();
        var nrs = Stream.generate(() -> r.nextInt(100));
        var nrs_cache = cache(nrs);
        
        var nrs1 = nrs_cache.get();
        var nrs2 = nrs_cache.get();
        
        var expected = nrs1.limit(10).collect(toList());
        var actual = nrs2.limit(10).collect(toList());
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCache2() {
        
        Stream<Integer> nrs = IntStream.range(1,10).boxed();
        var nrs_cached = cache(nrs);
        
        var itA = nrs_cached.get().spliterator();
        var itB = nrs_cached.get().spliterator();
        
        int ia[] = new int[2];
        itA.tryAdvance(v -> ia[0] = v);
        
        int ib[] = new int[2];
        itB.tryAdvance(v -> ib[0] = v);
        itB.tryAdvance(v -> ib[1] = v);
        
        itA.tryAdvance(v -> ia[1] = v);
        
        
        assertEquals(ia[0], ib[0]);
        assertEquals(ia[1], ib[1]);
        assertEquals(1, ia[0]);
        assertEquals(2, ia[1]);
    }
    
}
