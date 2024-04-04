package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.Resolution;
import pt.leirt.mpd.products.Speaker;
import pt.leirt.mpd.products.TV;

import java.io.IOException;

import static pt.leirt.mpd.ReflexUtils.saveToFile;

public class ReflexTest {
    @Test
    public void saveToFileTest() throws IOException, IllegalAccessException {
        saveToFile(StoreDB.iPhone15, "output.json");
    }
}