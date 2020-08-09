package codes.ernesto.vwap.poc;

import codes.ernesto.vwap.InstrumentTracker;
import codes.ernesto.vwap.MarketUpdate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ProofOfConcept {

  public static void main(String[] args) {
    new ProofOfConcept().run();
  }

  private void run() {
    var instrumentIds = new String[] {
        "instrument1",
        "instrument2",
        "instrument3",
    };

    var marketVenues = new MarketVenueEmulator[] {
        new MarketVenueEmulator("marketVenue1", ProofOfConcept::handleMarketUpdate),
        new MarketVenueEmulator("marketVenue2", ProofOfConcept::handleMarketUpdate),
    };

    for (var marketVenue : marketVenues) {
      for (var instrumentId : instrumentIds) {
        marketVenue.subscribe(instrumentId);
      }
    }

    schedulePeriodsAndDays();

    System.out.println("Press <ENTER> to exit...");
    try {
      System.in.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Exiting");
    System.exit(0);
  }

  private void schedulePeriodsAndDays() {
    var scheduler = Executors.newSingleThreadScheduledExecutor();

    scheduler.scheduleAtFixedRate(new Runnable() {
      private int times = 0;

      @Override
      public void run() {
        times = (times + 1) % 3;

        if (times == 0) {
          System.out.println("End of day ==================================");
          endDay();
        }
        else {
          System.out.println("End of period -------------------------------");
          endPeriod();
        }
      }
    }, 60, 60, TimeUnit.SECONDS);
  }


  private static Map<String, InstrumentTracker> instrumentTrackers = new HashMap<>();

  private static void handleMarketUpdate(MarketUpdate update) {
    var instrumentTracker = getOrPut(instrumentTrackers, update.instrumentId, InstrumentTracker::new);
    instrumentTracker.handleMarketUpdate(update);

    System.out.printf(
        "Received: %s\n" +
        "\tinstrument=%s, VWAP(buy)=%.2f, VWAP(sell)=%.2f\n",
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
