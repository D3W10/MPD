package org.isel.music_all.streams.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.isel.leirt.music_all.Errors.TODO;

public interface Sequence<T> {

    /**
     * Functional version of iteration interface
     * tryAdvance receives the consumer which is called
     * with the next element of the sequence, if one exists
     * @param action - consumer
     * @return true if the consumer was called with the next element
     *              or false if none exists
     */
    boolean tryAdvance(Consumer<T> action);

    /**
     * returns a new sequence with the elements from
     * the Iterable received
     * @param src the elements source
     * @param <T>
     * @return
     */
    static <T> Sequence<T> of(Iterable<T> src) {
        Iterator<T> it = src.iterator();
        return action -> {
           if (!it.hasNext()) return false;
           action.accept(it.next());
           return true;
        };
    }

    default <R> Sequence<R> map(Function<T,R> mapper) {
        return (Consumer<R> action) ->
                this.tryAdvance(t -> action.accept(mapper.apply(t)) );
    }

    default Sequence<T> filter(Predicate<T> pred) {
        return action -> {
            boolean[] found = {false};
            while(tryAdvance(t -> {
                if (pred.test(t)) {
                    action.accept(t);
                    found[0] = true;
                }
            }) && !found[0]);
            return found[0];
        };
    }

    default Sequence<T> concat(Sequence<T> other) {
        TODO("concat");
        return null;
    }

  
    default Sequence<T> skip(int n) {
        TODO("skip");
        return null;
    }
    
    default <U, V> Sequence<V> zip(Sequence<U> other, BiFunction<T,U,V> combiner ) {
        TODO("zip");
        return null;
    }

 
    // operações terminais

    default Optional<T> first() {
        //Wrapper<T> wrapper = new Wrapper<>();
        Object[] val = new Object[1];

        if (!tryAdvance(t ->  val[0] = t)) return Optional.empty();
        return Optional.of((T) val[0]);
    }


    default List<T> toList() {
        List<T> list = new ArrayList<>();

        while(tryAdvance(t -> list.add(t)));

        return list;
    }

}
