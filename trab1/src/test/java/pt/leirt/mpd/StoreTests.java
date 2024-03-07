package pt.leirt.mpd;

import org.junit.jupiter.api.Test;
import pt.leirt.mpd.products.Electronics;
import pt.leirt.mpd.products.Resolution;
import pt.leirt.mpd.products.Speaker;
import pt.leirt.mpd.products.TV;

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
        .addCatalog(new Speaker("s250", "Samsung", 200, 60));

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
}
