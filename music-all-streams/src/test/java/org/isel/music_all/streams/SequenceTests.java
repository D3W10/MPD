package org.isel.music_all.streams;

import org.isel.music_all.streams.utils.Sequence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.isel.music_all.streams.utils.Sequence.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SequenceTests {
    @Test
    public void ofTest1() {
        var expected = List.of(2, 5, 7, 6, 4, 9);
        Sequence<Integer> numbers = of(expected);
        assertEquals(expected, numbers.toList());
    }
    
    @Test
    public void ofTest2() {
        List<Integer> expected = List.of();
        Sequence<Integer> numbers = of(expected);
        assertEquals(expected, numbers.toList());
    }
    
    @Test
    public void filterTest() {
        Sequence<Integer> numbers =
            of(List.of(2, 5, 7, 6, 4, 9));
        Sequence<Integer> evens =
            numbers.filter( n -> n % 2 == 0);

        List<Integer> expected = List.of(2, 6, 4);
        assertEquals(expected, evens.toList());
    }
    
    @Test
    public void concatTest() {
        Sequence<Integer> numbers =
            of(List.of(2, 5, 7, 6, 4, 9));
        Sequence<Integer> evens =
            of(List.of(2, 5, 7, 6, 4, 9)).filter(n -> n % 2 == 0);
        var seqConcat = numbers.concat(evens);
        var expected = List.of(2, 5, 7, 6, 4, 9, 2, 6, 4);
        assertEquals(expected, seqConcat.toList());
    }

    @Test
    public void concatTestEmpty() {
        Sequence<Integer> emptySequence1 = Sequence.of(new ArrayList<>());
        Sequence<Integer> emptySequence2 = Sequence.of(new ArrayList<>());

        Sequence<Integer> concatenatedSequence = emptySequence1.concat(emptySequence2);

        List<Integer> result = new ArrayList<>();
        concatenatedSequence.tryAdvance(result::add);

        assertTrue(result.isEmpty());
    }
    
    @Test
    public void skipTest() {
        Sequence<Integer> numbers =
            of(List.of(2, 5, 7, 6, 4, 9));
       
        var seqSkip = numbers.skip(4);
        var expected = List.of(4,9);
        assertEquals(expected, seqSkip.toList());
    }

    @Test
    public void skipTestEmpty() {
        Sequence<Integer> sequence = Sequence.of(new ArrayList<>());
        var skipped = sequence.skip(3);

        assertTrue(skipped.toList().isEmpty());
    }
    
    @Test
    public void zipTest() {
        Sequence<String> names =
            of(List.of("Jorge", "Mario", "Manuel"));
        
        Sequence<String> surNames =
            of(List.of("Martins", "Pinheiro", "Carvalho", "Oliveira"));
        
        var seqZip = names.zip(surNames, (s1, s2) -> s1 + " " + s2);
        
        var expected =
            List.of("Jorge Martins","Mario Pinheiro", "Manuel Carvalho");
            
        assertEquals(expected, seqZip.toList());
    }

    @Test
    public void zipTestLess() {
        Sequence<String> names =
                of(List.of("Jorge", "Mario", "Manuel"));

        Sequence<String> surNames =
                of(List.of("Martins", "Pinheiro"));

        var seqZip = names.zip(surNames, (s1, s2) -> s1 + " " + s2);

        var expected =
                List.of("Jorge Martins","Mario Pinheiro");

        assertEquals(expected, seqZip.toList());
    }

    @Test
    public void zipTestEmpty() {
        Sequence<String> seq1 = Sequence.of(new ArrayList<>());
        Sequence<String> seq2 = Sequence.of(new ArrayList<>());

        var seqZip = seq1.zip(seq2, (s1, s2) -> s1 + " " + s2);

        assertEquals(List.of(), seqZip.toList());
    }
    
    @Test
    public void firstTest() {
        var firstCountry =
            of(List.of("Portugal", "Spain", "Italy", "Greece")).first();
        
      
        assertTrue(firstCountry.isPresent());
        assertEquals("Portugal", firstCountry.get());
    }
}
