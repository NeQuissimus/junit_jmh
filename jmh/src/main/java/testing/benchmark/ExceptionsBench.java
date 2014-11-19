package testing.benchmark;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import testing.Either;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ExceptionsBench {

  private static final String[] NULLS = new String[] {null, null, null, null};
  private static final String[] NON_NULLS = new String[] {"foo", "bar", "hello", "world"};

  static Integer get(String a, String b) {
    return Integer.parseInt(a) + Integer.parseInt(b);
  }

  static Either<Exception, Integer> runWithEither() {
    final String a = NULLS[new Random().nextInt(4)];
    final String b = NON_NULLS[new Random().nextInt(4)];

    try {
      return Either.success(get(a, b));
    } catch (NumberFormatException e) {
      return Either.failure(e);
    }
  }

  static Either<Exception, Integer> runWithEitherTestFirst() {
    final String a = NULLS[new Random().nextInt(4)];
    final String b = NON_NULLS[new Random().nextInt(4)];

    if (null != a) {
      return Either.success(get(a, b));
    } else {
      return Either.failure(new NumberFormatException(a));
    }
  }

  static int runWithException() {
    final String a = NULLS[new Random().nextInt(4)];
    final String b = NON_NULLS[new Random().nextInt(4)];
    return get(a, b);
  }

  @Benchmark
  public void testEither(Blackhole bh) {
    final Either<Exception, Integer> e = runWithEither();
    bh.consume(e.success());
    bh.consume(e.failure());
  }

  @Benchmark
  public void testEitherTestFirst(Blackhole bh) {
    final Either<Exception, Integer> e = runWithEitherTestFirst();
    bh.consume(e.success());
    bh.consume(e.failure());
  }

  @Benchmark
  public void testException(Blackhole bh) {
    try {
      final int i = runWithException();
      bh.consume(i);
    } catch (Exception e) {
      bh.consume(e);
    }
  }

  @Benchmark
  public void testExceptionStack(Blackhole bh) {
    try {
      final int i = runWithException();
      bh.consume(i);
    } catch (Exception e) {
      bh.consume(e.fillInStackTrace());
    }
  }
}
