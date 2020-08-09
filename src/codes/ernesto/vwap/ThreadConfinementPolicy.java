package codes.ernesto.vwap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadConfinementPolicy implements VwapLogger.SerialPolicy {
  final List<ExecutorService> executors;

  int next = -1;

  public ThreadConfinementPolicy(int numThreads) {
    executors = Stream
        .generate(() -> Executors.newSingleThreadExecutor())
        .limit(numThreads)
        .collect(Collectors.toList());
  }

  public ThreadConfinementPolicy() {
    this(Runtime.getRuntime().availableProcessors());
  }

  @Override
  public <K, V> Map<K, V> newInstrumentsMap() {
    return new ConcurrentHashMap<>();
  }

  @Override
  public Executor obtainSerialExecutor() {
    next = (next + 1) % executors.size();
    return executors.get(next);
  }
}
