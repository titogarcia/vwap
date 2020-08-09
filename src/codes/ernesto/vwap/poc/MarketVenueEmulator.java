package codes.ernesto.vwap.poc;

import codes.ernesto.vwap.MarketUpdate;
import codes.ernesto.vwap.MarketUpdateType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

class MarketVenueEmulator {
  private static final Random random = new Random();

  private final String id;
  private final Consumer<MarketUpdate> listener;
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  private final List<String> instruments = new ArrayList<>();

  public MarketVenueEmulator(String id, Consumer<MarketUpdate> listener) {
    this.id = id;
    this.listener = listener;
    scheduleNextUpdate();
  }

  public void subscribe(String instrument) {
    scheduler.execute(() -> instruments.add(instrument));
  }

  private void scheduleNextUpdate() {
    scheduler.schedule(() -> {
      if (!instruments.isEmpty())
        listener.accept(randomMarketUpdate());

      scheduleNextUpdate();
    }, 2 + random.nextInt(8), TimeUnit.SECONDS);
  }

  private MarketUpdate randomMarketUpdate() {
    var instrument = instruments.get(random.nextInt(instruments.size()));
    var price = 160.0 + 90.0 * random.nextDouble();
    var quantity = 1 + random.nextInt(100);
    var type = random.nextBoolean() ? MarketUpdateType.BUY : MarketUpdateType.SELL;
    return new MarketUpdate(id, instrument, price, quantity, type);
  }
}
