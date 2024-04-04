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

    public static void saveToWriter(Object o, Writer w) throws IOException, IllegalAccessException {
        w.write(serializeObject(o).toString());
    }

    public static JSONObject serializeObject(Object obj) throws IllegalAccessException {
        JSONObject jObj = new JSONObject();
        List<Field> fields = getAllFields(obj.getClass());

        for (Field field : fields) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Internal.class)) {
                Object value = field.get(obj);

                if (value != null && !isPrimitiveType(value.getClass()))
                    jObj.put(field.isAnnotationPresent(JsonName.class) ? field.getAnnotation(JsonName.class).name() : field.getName(), serializeObject(value));
                else
                    jObj.put(field.isAnnotationPresent(JsonName.class) ? field.getAnnotation(JsonName.class).name() : field.getName(), value);
            }
        }

        return jObj;
    }

    private static boolean isPrimitiveType(Class<?> type) {
        return type.isPrimitive() || type == String.class || type == Integer.class || type == Long.class || type == Double.class || type == Float.class || type == Boolean.class;
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
