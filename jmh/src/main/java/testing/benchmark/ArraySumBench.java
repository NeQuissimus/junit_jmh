package testing.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

public class ArraySumBench {

  private static final int BATCH_SIZE = 25000;

  private static final double[] array = new double[BATCH_SIZE];

  static {
    for (int i = 0; i < array.length; i++) {
      array[i] = (double)i;
    }
  }

  private static double sum(double[] array) {
    double total = 0.0d;
    for (int i = 0; i < array.length; i++) {
      total += array[i];
    }
    return total;
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public void testSum(Blackhole bh) {
    bh.consume(sum(array));
  }
}
