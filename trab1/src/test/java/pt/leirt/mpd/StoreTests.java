package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.*;

import java.io.IOException;
import java.io.StringWriter;
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
    store.fromJson("{\"catalog\":[{\"price\":100,\"name\":\"x300\",\"power\":40,\"category\":\"AUDIO\",\"brand\":\"JBL\"},{\"price\":200,\"name\":\"s250\",\"power\":60,\"category\":\"AUDIO\",\"brand\":\"Samsung\"},{\"screenSize\":65,\"price\":3000,\"name\":\"X95\",\"category\":\"VIDEO\",\"resolution\":{\"json\":{\"width\":3840,\"height\":2160}},\"brand\":\"Sony\"}]}");
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
    store.addCatalog(new TV("LG QOLED", "LG", 1300, new Resolution(1920, 1080), 23))
                    .addCatalog(new Notebook("Surface", "Microsoft", 1000, new Resolution(1920, 1080), 16, 1200, 3));

    assertEquals(store.toJson(), "{\"catalog\":[{\"power\":70,\"type\":\"Speaker\"},{\"power\":40,\"type\":\"Speaker\"},{\"res\":{\"width\":1920,\"height\":1080},\"screenSize\":23,\"type\":\"TV\"},{\"usbPorts\":3,\"screenSize\":16,\"batteryCapacity\":1200,\"type\":\"Notebook\",\"resolution\":{\"width\":1920,\"height\":1080}},{\"power\":60,\"type\":\"Speaker\"},{\"res\":{\"width\":3840,\"height\":2160},\"screenSize\":65,\"type\":\"TV\"}]}");
  }
}
