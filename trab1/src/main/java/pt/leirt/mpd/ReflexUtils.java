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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            bw.write(serializeObject(o).toString());
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

    public static JSONObject serializeObject(Object obj) throws IllegalAccessException {
        JSONObject jObj = new JSONObject();
        List<Field> fields = getAllFields(obj.getClass());

        for (Field field : fields) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Internal.class)) {
                Object value = field.get(obj);

                if (value != null && !value.getClass().isPrimitive())
                    jObj.put(field.isAnnotationPresent(JsonName.class) ? field.getAnnotation(JsonName.class).name() : field.getName(), serializeObject(value));
                else
                    jObj.put(field.isAnnotationPresent(JsonName.class) ? field.getAnnotation(JsonName.class).name() : field.getName(), value);
            }
        }

        return jObj;
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
