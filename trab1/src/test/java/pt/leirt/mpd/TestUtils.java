package pt.leirt.mpd;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static <T> long count(Iterable<T> src) {
        long c = 0;
        
        for(var t : src ) c++;
        return c;
    }
    
    public static <T> List<T> toList(Iterable<T> src) {
        var res = new ArrayList<T>();
        for(var p : src) res.add(p);
        return res;
    }
}
