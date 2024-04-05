package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreImprovedTests {
    private final static StoreImproved store = new StoreImproved();
    private final static Resolution hd = new Resolution(1024, 768);
    private final static Resolution fullHd = new Resolution(1920, 1080);
    private final static Resolution uhd = new Resolution(3840, 2160);

    static {
        store.addCatalog(new TV("X95", "Sony", 3000, uhd, 65.0))
                .addCatalog(new Speaker("x300", "JBL", 100, 40))
                .addCatalog(StoreDB.samsungS250)
                .addCatalog(StoreDB.jblCharge3)
                .addCatalog(new SmartPhone("iPhone", "Apple", 1200, fullHd, 17, 1500))
                .addCatalog(new Notebook("Surface", "Microsoft", 1000, hd, 16, 1200, 3))
                .addCatalog(new Pack("Pack", List.of(new TV("X95", "Sony", 3000, uhd, 65.0), new Speaker("s250", "Samsung", 200, 60))))
                .addCatalog(new Promo(StoreDB.jblCharge1, 50))
                .addCatalog(new Promo(StoreDB.lgOLED, 20))
                .addCatalog(new Promo(StoreDB.lgNanoCell, 20));
    }

    private static <T> long count(Iterable<T> src) {
        long c = 0;

        for (var t : src)
            c++;

        return c;
    }

    @Test
    public void productsFromSamsungTests() {
        List<Electronics> expected = List.of(StoreDB.samsungS250);

        Iterable<Electronics> result = store.fromSamsung();

        assertEquals(expected.size(), count(result));
        assertEquals(expected, result);
    }

    @Test
    public void getAbove50InchesTvsTest() {
        List<TV> expected = List.of(new TV("X95", "Sony", 3000, uhd, 65.0));

        Iterable<TV> result = store.getAboveSizeTvs(50);

        assertEquals(expected, result);
    }

    @Test
    public void getSpeakersInPowerIntervalTest() {
        List<Speaker> expected = List.of(new Speaker("Charge 3", "JBL", 160, 70),
                new Speaker("s250", "Samsung", 200, 60));

        Iterable<Speaker> result = store.getSpeakersInPowerInterval(50, 80);

        assertEquals(expected, result);
    }

    @Test
    public void getMostExpensiveCommunicationDeviceTest() {
        Electronics expected = new SmartPhone("iPhone", "Apple", 1200, fullHd, 17, 1500);
        Electronics result = store.getMostExpensiveCommunicationDevice();

        assertEquals(expected, result);
    }

    @Test
    public void getMostExpensiveIndividualProductInPacksTest() {
        Electronics expected = new TV("X95", "Sony", 3000, uhd, 65.0);
        Electronics result = store.getMostExpensiveIndividualProductInPacks();

        assertEquals(expected, result);
    }

    @Test
    public void getDisplaysSummaryTest() {
        List<String> summaries = List.of("Apple iPhone: price 1200,00 euros 17.0 Resolution: { Height: 1080, Width: 1920}", "Microsoft Surface: price 1000,00 euros 16.0 Resolution: { Height: 768, Width: 1024}", "Resolution: Resolution: { Height: 2160, Width: 3840}\nScreen Size:65.0 65.0 Resolution: { Height: 2160, Width: 3840}");

        for (Store.DisplaySummary summary : store.getDisplaysSummary())
            assertTrue(summaries.contains(summary.getSummary()));
    }

    @Test
    public void getCheapestSmartPhoneWithBatteryGreaterThenTest() {
        SmartPhone expected = new SmartPhone("iPhone", "Apple", 1200, fullHd, 17, 1500);
        SmartPhone result = store.getCheapestSmartPhoneWithBatteryGreaterThen(1000);

        assertEquals(expected, result);
    }

    //TODO: rever método
    @Test
    public void getPromoTVsWith20PercentDiscountTest() {
        List<Promo> expected = List.of(new Promo(StoreDB.lgOLED, 20), new Promo(StoreDB.lgNanoCell, 20));
        int i = 0;

        for(var p : store.getPromoTVsWith20PercentDiscount()){
            assertEquals(expected.get(i), p);
            i++;
        }
    }
}