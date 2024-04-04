package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.Pack;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.leirt.mpd.TestUtils.count;
import static pt.leirt.mpd.TestUtils.toList;

public class PackTests {
    @Test
    public void checkAttributtesOfPackSamsungTest() {
        var pack = StoreDB.packSamsung;
        var expectedProducts =
            List.of(StoreDB.samsungU7,StoreDB.samsungS250,StoreDB.samsungS23 );
        
        var expectedProductsCount = 3;
        
        assertEquals(expectedProductsCount, count(pack));
        assertEquals(expectedProducts, toList(pack));
    }
    
    @Test
    public void checkAttributtesOfMultiPackTest() {
        var pack = new Pack("TVsPlusSpeaker",
            List.of(StoreDB.packTvs, StoreDB.jblX300));
        
        var expectedProducts =
            List.of(StoreDB.samsungU7, StoreDB.sonyX95, StoreDB.jblX300 );
        
        var expectedProductsCount = 3;
        
        assertEquals(expectedProductsCount, count(pack));
        assertEquals(expectedProducts, toList(pack));
    }

    @Test
    public void checkAttributesOfBigPackTest() {
        var pack = StoreDB.packBig;
        var expectedProducts = List.of(StoreDB.iPhone15, StoreDB.samsungU7, StoreDB.samsungS250, StoreDB.samsungS23, StoreDB.jblX300, StoreDB.samsungU7, StoreDB.sonyX95);

        var expectedProductsCount = 7;

        assertEquals(expectedProductsCount, count(pack));
        assertEquals(expectedProducts, toList(pack));
    }
}
