package org.isel.leirt.music_all.requests;

import java.io.Reader;

public class SaverRequest implements Request {
    private final Request request;

    public SaverRequest(HttpRequest req) {
        this.request = req;
    }
    
    @Override
    public Reader get(String path) {
        Reader response = request.get(path);
        MockRequest.saveOn(path, response);
        return response;
    }
}