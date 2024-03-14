package pt.leirt.mpd;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

public class ReflexUtils {
    public void saveToFile(Object o, String fileName) throws IOException{
        JSONObject objectJson = new JSONObject();
        Field[] objectFields = o.getClass().getDeclaredFields();

        for (var f : objectFields){
            objectJson.put(f.getName(),f);
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(objectJson.toString());
        bw.close();
    }

    public void saveToWriter(Object o, Writer w){

    }
}
