package codes.ernesto;

import java.util.function.Consumer;

public class MarketVenue {
  public final String name;

  public MarketVenue(String name) {
    this.name = name;
  }

  public void subscribe(String instrument) {
  }

  public void listen(Consumer<MarketUpdate> listener) {

  }
}
