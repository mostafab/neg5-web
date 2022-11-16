package org.neg5.core;

import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class CurrentUserContext {

    private static final ThreadLocal<Optional<UserData>> currentUser = new ThreadLocal<>();

    public Optional<UserData> getUserData() {
        return currentUser.get();
    }

    public UserData getUserDataOrThrow() {
        return getUserData().orElseThrow(() -> new IllegalStateException("No user for the current thread"));
    }

    public void clear() {
        currentUser.remove();
    }

    public void set(UserData user) {
        this.currentUser.set(Optional.ofNullable(user));
    }

}
