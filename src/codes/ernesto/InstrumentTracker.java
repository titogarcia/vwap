package codes.ernesto;

public class InstrumentTracker {
  private InstrumentDayTracker buyDayTracker = new InstrumentDayTracker();
  private InstrumentDayTracker sellDayTracker = new InstrumentDayTracker();

  public void endDay() {
    buyDayTracker = new InstrumentDayTracker();
    sellDayTracker = new InstrumentDayTracker();
  }

  public void handleMarketUpdate(MarketUpdate update) {
    switch (update.type) {
      case BUY:
        buyDayTracker.handleMarketUpdate(update);
        break;

      case SELL:
        sellDayTracker.handleMarketUpdate(update);
        break;
    }
  }

  public double getVwapBuy() {
    return buyDayTracker.getVwap();
  }

  public double getVwapSell() {
    return sellDayTracker.getVwap();
  }

  public void endPeriod() {
    buyDayTracker.endPeriod();
    sellDayTracker.endPeriod();
  }
}
