package testing.benchmark;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class MapBench {
  private static final Map<Integer, UUID> MAP = IntStream.range(1, 100000).boxed().collect(Collectors.toMap(Function.<Integer>identity(), i -> UUID.randomUUID()));

  @Benchmark
  public void testEntrySet(Blackhole bh) {
    for (Map.Entry<Integer, UUID> e : MAP.entrySet()) {
      bh.consume(e.getKey());
      bh.consume(e.getValue());
    }
  }

  @Benchmark
  public void testForEach(Blackhole bh) {
    final BiConsumer<Integer, UUID> consumer = (k, v) -> {
      bh.consume(k);
      bh.consume(v);
    };

    MAP.forEach(consumer);
  }

  @Benchmark
  public void testKeySetGet(Blackhole bh) {
    for (Integer k : MAP.keySet()) {
      bh.consume(k);
      bh.consume(MAP.get(k));
    }
  }
}
