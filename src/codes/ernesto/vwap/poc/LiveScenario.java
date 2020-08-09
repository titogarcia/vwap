package codes.ernesto.vwap.poc;

import codes.ernesto.vwap.VwapLogger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LiveScenario {

  public void run(VwapLogger.SerialPolicy serialPolicy) {
    var vwapLogger = new VwapLogger(serialPolicy);

    var instrumentIds = new String[] {
        "instrument1",
        "instrument2",
        "instrument3",
    };

    var marketVenues = new MarketVenueEmulator[] {
        new MarketVenueEmulator("marketVenue1", vwapLogger::handleMarketUpdate),
        new MarketVenueEmulator("marketVenue2", vwapLogger::handleMarketUpdate),
    };

    for (var marketVenue : marketVenues) {
      for (var instrumentId : instrumentIds) {
        marketVenue.subscribe(instrumentId);
      }
    }

    schedulePeriodsAndDays(vwapLogger);

    System.out.println("Press <ENTER> to exit...");
    try {
      System.in.read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    System.out.println("Exiting");
    System.exit(0);
  }

  private void schedulePeriodsAndDays(VwapLogger vwapLogger) {
    var scheduler = Executors.newSingleThreadScheduledExecutor();

    scheduler.scheduleAtFixedRate(new Runnable() {
      private int times = 0;

      @Override
      public void run() {
        times = (times + 1) % 3;

        if (times == 0) {
          System.out.println("End of day ==================================");
          vwapLogger.endDay();
        }
        else {
          System.out.println("End of period -------------------------------");
          vwapLogger.endPeriod();
        }
      }
    }, 60, 60, TimeUnit.SECONDS);
  }
}
