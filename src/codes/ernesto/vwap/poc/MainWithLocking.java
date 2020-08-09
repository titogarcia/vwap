package codes.ernesto.vwap.poc;

import codes.ernesto.vwap.LockingPolicy;

public class MainWithLocking {
  public static void main(String[] args) {
    new LiveScenario().run(new LockingPolicy());
  }
}
