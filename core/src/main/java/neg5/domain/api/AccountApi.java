package neg5.domain.api;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.userData.DuplicateLoginException;

public interface AccountApi extends DomainObjectApiLayer<AccountDTO, String> {

    AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException;

    Optional<AccountWithHashedPassword> verifyPassword(String usernameOrEmail, String password);

    List<AccountDTO> findByQuery(@Nonnull String query);

    class AccountWithHashedPassword {
        private String username;
        private String name;
        private String hashedPassword;

        public AccountWithHashedPassword(String username, String name, String hashedPassword) {
            this.username = username;
            this.name = name;
            this.hashedPassword = hashedPassword;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }
    }
}
