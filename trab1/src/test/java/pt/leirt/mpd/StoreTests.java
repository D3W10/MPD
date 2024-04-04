package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                .addCatalog(new Promo(StoreDB.jblCharge1, 50));
    }

    private static <T> long count(Iterable<T> src) {
        long c = 0;

        for (var t : src)
            c++;

        return c;
    }

    @Test
    public void getCatalogInJson() throws IOException {
        // To complete
    }

    @Test
    public void productsFromSamsungTests() {
        List<Electronics> expected = List.of(StoreDB.samsungS250);

        Iterable<Electronics> result = store.fromSamsung();

        System.out.println(result);
        assertEquals(expected.size(), count(result));
        assertEquals(expected, result);
    }

    @Test
    public void getAbove50InchesTvsTest() {
        List<TV> expected = List.of(new TV("X95", "Sony", 3000, uhd, 65.0));

        Iterable<TV> result = store.getAboveSizeTvs(50);
        System.out.println(result);

        assertEquals(expected, result);
    }

    @Test
    public void getSpeakersInPowerIntervalTest() {
        List<Speaker> expected = List.of(new Speaker("s250", "Samsung", 200, 60),
                new Speaker("Charge 3", "JBL", 160, 70));

        Iterable<Speaker> result = store.getSpeakersInPowerInterval(50, 80);
        System.out.println(result);

        assertEquals(expected, result);
    }

    @Test
    public void storeToJson() {
        assertEquals(store.toJson(), "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"type\":\"SmartPhone\",\"brand\":\"Apple\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"product\":{\"price\":50,\"name\":\"Charge 1\",\"power\":20,\"type\":\"Speaker\",\"brand\":\"JBL\"},\"discount\":50,\"type\":\"Promo\"},{\"price\":100,\"name\":\"x300\",\"power\":40,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"type\":\"Notebook\",\"brand\":\"Microsoft\",\"resolution\":{\"width\":1024,\"height\":768}},{\"name\":\"Pack\",\"type\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"}]},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}}]}");
    }

    @Test
    public void storeToJsonImproved() throws IllegalAccessException {
        assertEquals(store.toJsonImproved(), "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"resolution\":{\"width\":1920,\"height\":1080},\"brand\":\"Apple\"},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"brand\":\"JBL\"},{\"product\":{\"price\":50,\"name\":\"Charge 1\",\"power\":20,\"brand\":\"JBL\"},\"discount\":50},{\"price\":100,\"name\":\"x300\",\"power\":40,\"brand\":\"JBL\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"resolution\":{\"width\":1024,\"height\":768},\"brand\":\"Microsoft\"},{\"name\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"resolution\":{\"width\":3840,\"height\":2160},\"brand\":\"Sony\"},{\"price\":200,\"name\":\"s250\",\"power\":60,\"brand\":\"Samsung\"}]},{\"price\":200,\"name\":\"s250\",\"power\":60,\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"resolution\":{\"width\":3840,\"height\":2160},\"brand\":\"Sony\"}]}");
    }

    @Test
    public void storeFromJson() {
        String expected = "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"type\":\"SmartPhone\",\"brand\":\"Apple\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"product\":{\"price\":50,\"name\":\"Charge 1\",\"power\":20,\"type\":\"Speaker\",\"brand\":\"JBL\"},\"discount\":50,\"type\":\"Promo\"},{\"price\":100,\"name\":\"x300\",\"power\":40,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"type\":\"Notebook\",\"brand\":\"Microsoft\",\"resolution\":{\"width\":1024,\"height\":768}},{\"name\":\"Pack\",\"type\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"}]},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}}]}";
        store.fromJson(expected);
    }
}