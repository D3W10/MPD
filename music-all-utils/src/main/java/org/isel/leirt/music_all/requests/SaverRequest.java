package org.isel.leirt.music_all.requests;

import java.io.*;

public class SaverRequest implements Request {
    private final Request request;

    public SaverRequest(HttpRequest req) {
        this.request = req;
    }
    
    @Override
    public Reader get(String path) {
        Reader response = request.get(path);
        Writer writer = new StringWriter();

        try {
            response.transferTo(writer);
            String buf = writer.toString();

            MockRequest.saveOn(path, new StringReader(buf));

            return new StringReader(buf);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}