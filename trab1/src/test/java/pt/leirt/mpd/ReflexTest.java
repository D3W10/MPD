package pt.leirt.mpd;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static pt.leirt.mpd.ReflexUtils.saveToFile;

public class ReflexTest {

    @Test
    public void saveToFileTest() throws IOException, IllegalAccessException, NoSuchFieldException {
        saveToFile(StoreDB.iPhone15, "output.json");
    }

}
