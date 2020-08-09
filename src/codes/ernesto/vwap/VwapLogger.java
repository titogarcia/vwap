package codes.ernesto.vwap;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class VwapLogger {

  public interface SerialPolicy {
    <K, V> Map<K, V> newInstrumentsMap();
    Executor obtainSerialExecutor();
  }

  private final SerialPolicy serialPolicy;

  private class Instrument {
    final InstrumentTracker tracker;
    final Executor executor;

    private Instrument(InstrumentTracker tracker, Executor executor) {
      this.tracker = tracker;
      this.executor = executor;
    }
  }

  private final Map<String, Instrument> instruments;

  public VwapLogger(SerialPolicy serialPolicy) {
    this.serialPolicy = serialPolicy;
    instruments = serialPolicy.newInstrumentsMap();
  }

  public void handleMarketUpdate(MarketUpdate update) {
    var instrument = getOrPut(instruments, update.instrumentId,
        () -> new Instrument(new InstrumentTracker(), serialPolicy.obtainSerialExecutor()));

    instrument.executor.execute(() -> {
      instrument.tracker.handleMarketUpdate(update);

      System.out.printf(
          "Received: %s\n" +
              "\tinstrument=%s, VWAP(buy)=%.2f, VWAP(sell)=%.2f\n",
          update,
          update.instrumentId, instrument.tracker.getVwapBuy(), instrument.tracker.getVwapSell());
    });
  }

  public void endPeriod() {
    for (var instrument : instruments.values()) {
      instrument.executor.execute(() -> {
        instrument.tracker.endPeriod();
      });
    }
  }

  public void endDay() {
    for (var instrument : instruments.values()) {
      instrument.executor.execute(() -> {
        instrument.tracker.endDay();
      });
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
