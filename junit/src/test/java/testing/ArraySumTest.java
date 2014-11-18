package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;

public final class ArraySumTest {
  private static final int ARRAY_SIZE = 25000;

  private static double sum(double[] array) {
    double total = 0.0d;
    for (int i = 0; i < array.length; i++) {
      total += array[i];
    }
    return total;
  }

  private static void benchmarkSum(double[] array) {
    long start = System.nanoTime();
    for (int j = 0; j < ARRAY_SIZE; j++) {
      sum(array);
    }
    long stop = System.nanoTime();
    System.out.printf("Computation finished in %d ns.%n", ((stop - start) / ARRAY_SIZE));
  }

  @Test
  public void testArray() {
    double[] array = new double[ARRAY_SIZE];
    // initialize array with some values
    for (int i = 0; i < array.length; i++) {
      array[i] = (double)i;
    }
    // perform actual benchmark
    for (int iteration = 0; iteration < 20; iteration++) {
      benchmarkSum(array);
    }
  }
}
