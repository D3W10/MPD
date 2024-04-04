package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.Resolution;
import pt.leirt.mpd.products.Speaker;
import pt.leirt.mpd.products.TV;

import java.io.IOException;

import static pt.leirt.mpd.ReflexUtils.saveToFile;

public class ReflexTest {
    private final static Store store = new Store();

    static {
        store.addCatalog(new TV("X95", "Sony", 3000, new Resolution(3840, 2160), 65.0))
                .addCatalog(new Speaker("x300", "JBL", 100, 40))
                .addCatalog(new Speaker("s250", "Samsung", 200, 60))
                .addCatalog(StoreDB.jblCharge3);
    }

    @Test
    public void saveToFileTest() throws IOException, IllegalAccessException {
        saveToFile(store, "output.json");
    }
}