package io.microdrive.accounts;

public class Result<T> {
    private final Throwable left;
    private final T right;

    private Result(Throwable error, T result) {
        this.left = error;
        this.right = result;
    }

    public static <T> Result<T> fail(Throwable throwable) {
        return new Result<>(throwable, null);
    }

    public static <T> Result<T> success(T result) {
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
