package org.isel.music_all.async.utils.requests;

import java.io.Reader;
import java.util.concurrent.CompletableFuture;

public class CountAsyncRequest implements AsyncRequest {
    private int count;
    private final AsyncRequest source;
    
    public CountAsyncRequest(AsyncRequest source) {
        this.source = source;
    }
    
    @Override
    public CompletableFuture<Reader> getAsync(String path) {
        count++;
        return source.getAsync(path);
    }
    
    public int getCount() {
        return count;
    }
}
