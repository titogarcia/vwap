package codes.ernesto.vwap.poc;

import codes.ernesto.vwap.InstrumentTracker;
import codes.ernesto.vwap.MarketUpdate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class VwapLogger {
  private Map<String, InstrumentTracker> instrumentTrackers = new HashMap<>();

  public void handleMarketUpdate(MarketUpdate update) {
    var instrumentTracker = getOrPut(instrumentTrackers, update.instrumentId, InstrumentTracker::new);
    instrumentTracker.handleMarketUpdate(update);

    System.out.printf(
        "Received: %s\n" +
            "\tinstrument=%s, VWAP(buy)=%.2f, VWAP(sell)=%.2f\n",
        update,
        update.instrumentId, instrumentTracker.getVwapBuy(), instrumentTracker.getVwapSell());
  }

  public void endPeriod() {
    for (var instrumentTracker : instrumentTrackers.values()) {
      instrumentTracker.endPeriod();
    }
  }

  public void endDay() {
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
