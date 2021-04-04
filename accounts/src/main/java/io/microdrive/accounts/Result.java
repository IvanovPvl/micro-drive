package io.microdrive.accounts;

public class Result<E extends Throwable, T> {
    private final E left;
    private final T right;

    private Result(E error, T result) {
        this.left = error;
        this.right = result;
    }

    public static <E extends Throwable, T> Result<E, T> fail(E throwable) {
        return new Result<>(throwable, null);
    }

    public static <E extends Throwable, T> Result<E, T> success(T result) {
        return new Result<>(null, result);
    }

    public Throwable left() {
        return left;
    }

    public T right() {
        return right;
    }

    public boolean isSuccess() {
        return left == null;
    }

    public boolean isFailed() {
        return !isSuccess();
    }
}
