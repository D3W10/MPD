package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreTests {
    private final static Store store = new Store();
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
        List<Speaker> expected = List.of(new Speaker("s250", "Samsung", 200, 60), new Speaker("Charge 3", "JBL", 160, 70));
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

    @Test
    public void getPromoTVsWith20PercentDiscountTest() {
        List<String> expected = List.of(new Promo(StoreDB.lgOLED, 20).toJson(), new Promo(StoreDB.lgNanoCell, 20).toJson());

        for (var p : store.getPromoTVsWith20PercentDiscount())
            assertTrue(expected.contains(p.toJson()));
    }

    @Test
    public void storeToJson() {
        assertEquals(store.toJson(), "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"type\":\"SmartPhone\",\"brand\":\"Apple\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"product\":{\"price\":50,\"name\":\"Charge 1\",\"power\":20,\"type\":\"Speaker\",\"brand\":\"JBL\"},\"discount\":50,\"type\":\"Promo\"},{\"price\":100,\"name\":\"x300\",\"power\":40,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"product\":{\"screenSize\":85,\"price\":1100,\"name\":\"LG NanoCell\",\"type\":\"TV\",\"brand\":\"LG\",\"resolution\":{\"width\":3840,\"height\":2160}},\"discount\":20,\"type\":\"Promo\"},{\"product\":{\"screenSize\":70,\"price\":1800,\"name\":\"LG OLED\",\"type\":\"TV\",\"brand\":\"LG\",\"resolution\":{\"width\":1024,\"height\":768}},\"discount\":20,\"type\":\"Promo\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"type\":\"Notebook\",\"brand\":\"Microsoft\",\"resolution\":{\"width\":1024,\"height\":768}},{\"name\":\"Pack\",\"type\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"}]},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}}]}");
    }

    @Test
    public void storeToJsonImproved() throws IllegalAccessException {
        assertEquals(store.toJsonImproved(), "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"resolution\":{\"width\":1920,\"height\":1080},\"brand\":\"Apple\"},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"brand\":\"JBL\"},{\"product\":{\"price\":50,\"name\":\"Charge 1\",\"power\":20,\"brand\":\"JBL\"},\"discount\":50},{\"price\":100,\"name\":\"x300\",\"power\":40,\"brand\":\"JBL\"},{\"product\":{\"screenSize\":85,\"price\":1100,\"name\":\"LG NanoCell\",\"resolution\":{\"width\":3840,\"height\":2160},\"brand\":\"LG\"},\"discount\":20},{\"product\":{\"screenSize\":70,\"price\":1800,\"name\":\"LG OLED\",\"resolution\":{\"width\":1024,\"height\":768},\"brand\":\"LG\"},\"discount\":20},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"resolution\":{\"width\":1024,\"height\":768},\"brand\":\"Microsoft\"},{\"name\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"resolution\":{\"width\":3840,\"height\":2160},\"brand\":\"Sony\"},{\"price\":200,\"name\":\"s250\",\"power\":60,\"brand\":\"Samsung\"}]},{\"price\":200,\"name\":\"s250\",\"power\":60,\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"resolution\":{\"width\":3840,\"height\":2160},\"brand\":\"Sony\"}]}");
    }

    @Test
    public void storeFromJson() {
        String expected = "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"type\":\"SmartPhone\",\"brand\":\"Apple\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"product\":{\"price\":50,\"name\":\"Charge 1\",\"power\":20,\"type\":\"Speaker\",\"brand\":\"JBL\"},\"discount\":50,\"type\":\"Promo\"},{\"price\":100,\"name\":\"x300\",\"power\":40,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"type\":\"Notebook\",\"brand\":\"Microsoft\",\"resolution\":{\"width\":1024,\"height\":768}},{\"name\":\"Pack\",\"type\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"}]},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}}]}";
        store.fromJson(expected);
    }
}