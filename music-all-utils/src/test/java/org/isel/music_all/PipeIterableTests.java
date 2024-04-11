/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2019, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 *
 */

package org.isel.music_all;


import org.isel.leirt.music_all.queries.PipeIterable;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toList;
import static org.isel.leirt.music_all.queries.PipeIterable.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.System.out;


public class PipeIterableTests {
    @Test
    public void testGenerate() {
        int[] next = {2};
        Supplier<Integer> si = () -> {
            int curr = next[0];
            next[0] += 2;
            return curr;
        };
        PipeIterable<Integer> nrs =
            generate(si)
            .limit(10);
        
        var expected = List.of(2,4,6,8,10,12,14,16,18,20);
        assertEquals(expected, nrs.toList());
    }
    
    
    @Test
    public void testSkip(){
        List<Integer> nrs = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> actual = of(nrs).skip(3).toList();
        var expected = List.of(4, 5, 6, 7, 8);
        assertEquals(expected, actual);
    }

    @Test
    public void testLimit(){
        var nrs = iterate(1, n -> n + 2).limit(10);
        var expected = List.of(1,3,5,7,9,11,13,15,17,19);
        assertEquals(10,nrs.count());
        assertEquals(expected,nrs.toList());
    }
    
    @Test
    public void testTakeWhile(){
        var nrs = List.of(1,3,5,7,9,11,13,15,17,19);
        var expected = List.of(1,3,5,7,9);
        var lessThan10 = of(nrs).takeWhile(n -> n < 10);
        assertEquals(5,lessThan10.count());
        assertEquals(expected,lessThan10.toList());
    }
    
    @Test
    public void testFirstFilterMapNrs() throws Throwable{
        
        PipeIterable<Integer> nrs = iterate(1, n -> n + 1);
        Optional<Integer> first =
            nrs.map( n -> n * n).filter(n -> n > 3).first();
        
        if (!first.isPresent()) fail("first returning no value!");
        int val = first.get();
        assertEquals(4, val);
    }
    
    @Test
    public void testMap(){
        var words = List.of("super", "isel", "ola", "fcp");
        var actual =
            of(words).map(w -> w.length()).toList();
        var expected = List.of(5, 4, 3, 3);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testFilter() {
        
        var  names = of( "Joao", "Carlos", "Jorge", "Luis");
        
        var jNames = names.filter(name -> name.startsWith("J"));
        
        assertEquals(2, jNames.count());
        assertEquals(List.of("Joao", "Jorge"), jNames.toList());
    }
    
    private List<Integer> chars(String s) {
        return s.chars().boxed().collect(Collectors.toList());
    }
    
    @Test
    public void testFlatMap(){
        var words = List.of("super", "isel", "ola", "slb");
        var actual =
            of(words)
            .flatMap(w -> chars(w))
            .filter(c -> c == 's')
            .count();
        
        assertEquals(3, actual);
    }

    @Test
    public void testCache() {
        Random r = new Random();
        PipeIterable<Integer> nrs = generate(() -> r.nextInt(100));
        nrs = nrs.cache();
        
        var nrs1 = nrs.limit(10);
        
        var expected = nrs.limit(10).toList();
        var actual = nrs.limit(10).toList();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCache2() {
        
        PipeIterable<Integer> nrs = range(1,10);
        nrs = nrs.cache();
        
        var itA = nrs.iterator();
        var itB = nrs.iterator();
        
        int ia1 = itA.next();
        
        int ib1 = itB.next();
        int ib2 = itB.next();
        
        int ia2 = itA.next();
        
        
        assertEquals(ia1, ib1);
        assertEquals(ia2, ib2);
        assertEquals(1, ia1);
        assertEquals(2, ia2);
    }
    
    @Test
    public void testMax(){
        Random r = new Random();
        PipeIterable<Integer> nrs =
            generate(() -> r.nextInt(100))
                .limit(10)
                .cache();
        
        var lst = nrs.toList();
        lst.sort(Integer::compare);
        
        var m = nrs.max(Integer::compare);
        m.ifPresent(out::println);
        assertTrue(m.isPresent());
        assertEquals(lst.get(lst.size()-1), m.get());
    }
    
  
}