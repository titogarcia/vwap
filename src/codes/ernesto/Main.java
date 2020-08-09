package codes.ernesto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Main {

  public static void main(String[] args) {
    var instrumentIds = new String[] {
        "instrument1",
        "instrument2",
        "instrument3",
    };

    var marketVenues = new MarketVenue[] {
        new MarketVenue("marketVenue1"),
        new MarketVenue("marketVenue2"),
        new MarketVenue("marketVenue3"),
        new MarketVenue("marketVenue4"),
    };

    for (var marketVenue : marketVenues) {
      marketVenue.listen(Main::handleMarketUpdate);

      for (var instrumentId : instrumentIds) {
        marketVenue.subscribe(instrumentId);
      }
    }

    System.out.println("Press <ENTER> to exit...");
    try {
      System.in.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Map<String, InstrumentTracker> instrumentTrackers = new HashMap<>();

  private static void handleMarketUpdate(MarketUpdate update) {
    var instrumentTracker = getOrPut(instrumentTrackers, update.instrumentId, InstrumentTracker::new);
    instrumentTracker.handleMarketUpdate(update);

    System.out.printf(
        "Received: %s\n" +
        "\tinstrument=%s, VWAP(buy)=%.2f, VWAP(sell)=%.2f",
        update,
        update.instrumentId, instrumentTracker.getVwapBuy(), instrumentTracker.getVwapSell());
  }

  private static void endPeriod() {
    for (var instrumentTracker : instrumentTrackers.values()) {
      instrumentTracker.endPeriod();
    }
  }

  private static void endDay() {
    for (var instrumentTracker : instrumentTrackers.values()) {
      instrumentTracker.endDay();
    }
  }

  private static <K, V> V getOrPut(Map<K, V> map, K key, Supplier<V> defaultValue) {
    var result = map.get(key);
    if (result == null) {
      result = defaultValue.get();
      map.put(key, result);
    }
    return result;
  }
}
