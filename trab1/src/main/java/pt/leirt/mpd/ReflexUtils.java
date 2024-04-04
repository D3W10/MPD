package pt.leirt.mpd;

import org.json.JSONObject;
import pt.leirt.mpd.products.Internal;
import pt.leirt.mpd.products.JsonName;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflexUtils {
    public static void saveToFile(Object o, String fileName) throws IOException, IllegalAccessException {
        JSONObject objectJson = new JSONObject();
        List<Field> objectFields = getAllFields(o.getClass());

        for (var f : objectFields){
            f.setAccessible(true);

            if (!f.isAnnotationPresent(Internal.class))
                objectJson.put(f.isAnnotationPresent(JsonName.class) ? f.getAnnotation(JsonName.class).name() : f.getName(), f.get(o));

        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            bw.write(objectJson.toString());
        }
    }

    public static void saveToWriter(Object o, Writer w) throws IOException {
        try(PrintWriter fw = new PrintWriter(new FileWriter("output.txt"))){
            List<Field> objectFields = getAllFields(o.getClass());

            for (var f : objectFields){
                fw.printf("%s: %s", f.getName(), f);
            }
        }
    }

    public static List<Field> getAllFields(Class<?> cls) {
        var currCls = cls;
        var fields = new ArrayList<Field>();

        while (currCls != Object.class) {
            fields.addAll(Arrays.asList(currCls.getDeclaredFields()));
            currCls = currCls.getSuperclass();
        }

        return fields;
    }
}
