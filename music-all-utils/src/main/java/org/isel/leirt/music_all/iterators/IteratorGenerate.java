package org.isel.leirt.music_all.iterators;

import java.util.Iterator;
import java.util.function.Supplier;

public class IteratorGenerate<T> implements Iterator<T> {
    private final Supplier<T> next;

    public IteratorGenerate(Supplier<T> next) {
        this.next = next;
    }
    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        return next.get();
    }
}
