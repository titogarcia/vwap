package codes.ernesto.vwap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class LockingPolicy implements VwapLogger.SerialPolicy {

  @Override
  public <K, V> Map<K, V> newInstrumentsMap() {
    return new ConcurrentHashMap<>();
  }

  @Override
  public Executor obtainSerialExecutor() {
    return new Executor() {
      @Override
      public void execute(Runnable command) {
        synchronized (this) {
          command.run();
        }
      }
    };
  }
}
