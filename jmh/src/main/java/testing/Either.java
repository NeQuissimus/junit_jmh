package testing;

public final class Either<F extends Exception, S> {
  private final F failure;
  private final S success;

  private Either(F failure, S success) {
    this.failure = failure;
    this.success = success;
  }

  public static <F extends Exception, S> Either<F, S> success(S success) {
    return new Either<>(null, success);
  }

  public static <F extends Exception, S> Either<F, S> failure(F failure) {
    return new Either<>(failure, null);
  }

  public boolean isSuccess() {
    return (null != this.success);
  }

  public F failure() {
    return this.failure;
  }

  public S success() {
    return this.success;
  }
}
