package codes.ernesto.vwap.poc;

import codes.ernesto.vwap.ThreadConfinementPolicy;

public class MainWithThreadConfinement {
  public static void main(String[] args) {
    new LiveScenario().run(new ThreadConfinementPolicy());
  }
}
