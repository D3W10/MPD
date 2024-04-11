package org.isel.leirt.music_all.requests;

import org.isel.leirt.music_all.requests.MockRequest;
import org.isel.leirt.music_all.requests.Request;

import java.io.*;

import static org.isel.leirt.music_all.Errors.TODO;

public class SaverRequest implements Request {
    
    public SaverRequest(HttpRequest req) {
       TODO("SaverRequest");
    }
    
    @Override
    public Reader get(String path) {
        TODO("SaverRequest.get");
        return null;
    }
}
