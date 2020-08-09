package codes.ernesto;

public class InstrumentDayTracker {
  private InstrumentPeriodTracker periodTracker = new InstrumentPeriodTracker();

  private double cumulativeTpv = 0;
  private int cumulativeVolume = 0;

  public void endPeriod() {
    cumulativeTpv += periodTracker.getTpv();
    cumulativeVolume += periodTracker.getVolume();

    periodTracker = new InstrumentPeriodTracker();
  }

  public double getVwap() {
    return cumulativeTpv / cumulativeVolume;
  }

  public void handleMarketUpdate(MarketUpdate update) {
    periodTracker.handleMarketUpdate(update);
  }
}
