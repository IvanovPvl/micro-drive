package io.microdrive.accounts.errors;

public class UsernameAlreadyExistsError extends Throwable {
    private String username;

    public UsernameAlreadyExistsError(String username) {
        super("Username already exists: " + username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
