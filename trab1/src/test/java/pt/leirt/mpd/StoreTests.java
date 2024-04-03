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
        .addCatalog(new Speaker("s250", "Samsung", 200, 60))
        .addCatalog(StoreDB.jblCharge3);

  }

  private static <T> long count(Iterable<T> src) {
      long c = 0;

      for(var t : src ) c++;
      return c;
  }

  @Test
  public void getCatalogInJson() throws IOException {
    // To complete
  }

  @Test
  public void productsFromSansungTests() {
    List<Electronics> expected =
        List.of(new Speaker("s250", "Samsung", 200, 60));

    Iterable<Electronics> result = store.fromSamsung() ;

    System.out.println(result);
    assertEquals(expected.size(), count(result));
    assertEquals(expected, result);
  }

  @Test
  public void getAbove50InchesTvsTest() {
    List<TV> expected = List.of(new TV("X95", "Sony", 3000, uhd, 65.0));

    Iterable<TV> result = store.getAboveSizeTvs(50) ;

    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  public void getSpeakersInPowerIntervalTest(){
    List<Speaker> expected = List.of(new Speaker("s250", "Samsung", 200, 60),
                                     new Speaker("Charge 3", "JBL", 160, 70));

    Iterable<Speaker> result = store.getSpeakersInPowerInterval(50, 80);

    System.out.println(result);

    assertEquals(expected, result);
  }

  @Test
  public void storeToJson() {
    store.addCatalog(new SmartPhone("iPhone", "Apple", 1200, new Resolution(2430, 1080), 17, 1500))
            .addCatalog(new Notebook("Surface", "Microsoft", 1000, new Resolution(1920, 1080), 16, 1200, 3))
            .addCatalog(new Pack("Pack", List.of(new TV("X95", "Sony", 3000, uhd, 65.0), new Speaker("s250", "Samsung", 200, 60))))
            .addCatalog(new Promo(new Speaker("s250", "Samsung", 200, 60), 50));

    assertEquals(store.toJson(), "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"type\":\"SmartPhone\",\"brand\":\"Apple\",\"resolution\":{\"width\":2430,\"height\":1080}},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"price\":100,\"name\":\"x300\",\"power\":40,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"type\":\"Notebook\",\"brand\":\"Microsoft\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"name\":\"Pack\",\"type\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"}]},{\"product\":{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},\"discount\":50,\"type\":\"Promo\"},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}}]}");
  }

  @Test
  public void storeFromJson() {
    String expected = "{\"catalog\":[{\"screenSize\":17,\"price\":1200,\"name\":\"iPhone\",\"batteryCapacity\":1500,\"type\":\"SmartPhone\",\"brand\":\"Apple\",\"resolution\":{\"width\":2430,\"height\":1080}},{\"price\":160,\"name\":\"Charge 3\",\"power\":70,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"price\":100,\"name\":\"x300\",\"power\":40,\"type\":\"Speaker\",\"brand\":\"JBL\"},{\"usbPorts\":3,\"screenSize\":16,\"price\":1000,\"name\":\"Surface\",\"batteryCapacity\":1200,\"type\":\"Notebook\",\"brand\":\"Microsoft\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"name\":\"Pack\",\"type\":\"Pack\",\"products\":[{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"}]},{\"product\":{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},\"discount\":50,\"type\":\"Promo\"},{\"price\":200,\"name\":\"s250\",\"power\":60,\"type\":\"Speaker\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"type\":\"TV\",\"brand\":\"Sony\",\"resolution\":{\"width\":3840,\"height\":2160}}]}";
    store.fromJson(expected);

    Store temp = new Store();
    temp.addCatalog(new SmartPhone("iPhone", "Apple", 1200, new Resolution(2430, 1080), 17, 1500))
            .addCatalog(new Notebook("Surface", "Microsoft", 1000, new Resolution(1920, 1080), 16, 1200, 3))
            .addCatalog(new Pack("Pack", List.of(new TV("X95", "Sony", 3000, uhd, 65.0), new Speaker("s250", "Samsung", 200, 60))))
            .addCatalog(new Promo(new Speaker("s250", "Samsung", 200, 60), 50));

    assertEquals(store.toJson(), temp.toJson());
  }
}
