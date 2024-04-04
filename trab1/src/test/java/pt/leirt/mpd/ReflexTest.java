package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.Resolution;
import pt.leirt.mpd.products.Speaker;
import pt.leirt.mpd.products.TV;

import java.io.*;

import static pt.leirt.mpd.ReflexUtils.saveToFile;
import static pt.leirt.mpd.ReflexUtils.saveToWriter;

public class ReflexTest {
    @Test
    public void saveToFileTest() throws IOException, IllegalAccessException {
        saveToFile(StoreDB.iPhone15, "output.json");
    }

    @Test
    public void saveToWriterTest() throws IOException, IllegalAccessException {
        try(Writer bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            saveToWriter(StoreDB.iPhone15, bw);
        }
    }
}