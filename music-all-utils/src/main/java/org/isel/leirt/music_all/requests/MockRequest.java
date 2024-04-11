/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2019, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 *
 */

package org.isel.leirt.music_all.requests;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class MockRequest implements Request {
    
    private static String CACHE_NAME = "weather_cache";
    
    private static final String CACHE_PATH = getCachePath();
    
    private static String convert(String path) {
        return path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('&'))
                   .replace('&', '-')
                   .replace( '?','-')
                   .replace( ',','-')+ ".txt";
    }
    
    private static String getCachePath() {
        URL url = MockRequest.class.getClassLoader().getResource(CACHE_NAME);
        try {
            File file = new File(url.toURI());
            return file.getAbsolutePath();
        }
        catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void saveOn(String uri, Reader reader) {
        final var absPath =  CACHE_PATH + '/' + convert(uri);
       
        try (PrintWriter writer = new PrintWriter(absPath)) {
            reader.transferTo(writer);
        }
        catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
    
    public Reader get(String uri) {
        var absPath = CACHE_NAME + '/' + convert(uri);
        try {
            return
                new InputStreamReader(
                    ClassLoader.getSystemResource(absPath).openStream()
                );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
