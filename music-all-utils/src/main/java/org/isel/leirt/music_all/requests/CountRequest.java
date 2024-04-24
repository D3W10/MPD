package org.isel.leirt.music_all.requests;

import java.io.Reader;

public class CountRequest implements Request {
    private final Request request;
    private int count;

    public CountRequest(Request req) {
        this.request = req;
        this.count = 0;
    }

    @Override
    public Reader get(String path) {
        count++;
        return request.get(path);
    }

    public int getCount() {
        return count;
    }
}
