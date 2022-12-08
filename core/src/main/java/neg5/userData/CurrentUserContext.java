package neg5.userData;

import com.google.inject.Singleton;
import java.util.Optional;

@Singleton
public class CurrentUserContext {

    private static final ThreadLocal<Optional<UserData>> CURRENT_USER_TL =
            ThreadLocal.withInitial(Optional::empty);

    public Optional<UserData> getUserData() {
        return CURRENT_USER_TL.get();
    }

    public UserData getUserDataOrThrow() {
        return getUserData()
                .orElseThrow(
                        () ->
                                new IllegalStateException(
                                        "No user initialized for the current thread"));
    }

    public void clear() {
        CURRENT_USER_TL.remove();
    }

    public void set(UserData user) {
        CURRENT_USER_TL.set(Optional.ofNullable(user));
    }
}
