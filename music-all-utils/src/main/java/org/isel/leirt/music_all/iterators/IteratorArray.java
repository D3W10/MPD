package org.isel.leirt.music_all.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorArray<T> implements Iterator<T> {

    private final T[] src;
    private int index=-1;

    public IteratorArray(T[] src) {
        this.src=src;
    }
    @Override
    public boolean hasNext() {
        return index < src.length-1;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        return src[++index];
    }
}
