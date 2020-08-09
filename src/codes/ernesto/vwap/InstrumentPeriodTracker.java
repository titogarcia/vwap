package codes.ernesto.vwap;

public class InstrumentPeriodTracker {
  private double high;
  private double low;
  private double last;
  private int volume = 0;

  public void handleMarketUpdate(MarketUpdate update) {
    high = Math.max(high, update.price);
    low = Math.min(low, update.price);
    last = update.price;
    volume += update.quantity;
  }

  public double getTpv() {
    return ((high + low + last) / 3) * volume;
  }

  public int getVolume() {
    return volume;
  }
}
