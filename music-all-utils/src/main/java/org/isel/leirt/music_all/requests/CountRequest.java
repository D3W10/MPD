package org.isel.leirt.music_all.requests;

import java.io.Reader;

import static org.isel.leirt.music_all.Errors.TODO;

public class CountRequest implements Request {
   
    public CountRequest(Request req) {
        TODO("CountRequest");
    }

    @Override
    public Reader get(String path) {
        TODO("CountRequest.get");
        return null;
    }

    public int getCount() {
        TODO("CountRequest.getCount");
        return 0;
    }
}
