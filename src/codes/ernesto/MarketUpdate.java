package codes.ernesto;

public abstract class MarketUpdate {
  public final String marketVenueId;
  public final String instrumentId;
  public final double price;
  public final int quantity;
  public final MarketUpdateType type;

  protected MarketUpdate(String marketVenueId, String instrumentId, double price, int quantity, MarketUpdateType type) {
    this.marketVenueId = marketVenueId;
    this.instrumentId = instrumentId;
    this.price = price;
    this.quantity = quantity;
    this.type = type;
  }

  @Override
  public String toString() {
    return "MarketUpdate{" +
        "marketVenueId='" + marketVenueId + '\'' +
        ", instrumentId='" + instrumentId + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        ", type=" + type +
        '}';
  }
}
