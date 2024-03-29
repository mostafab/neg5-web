package neg5.domain.api;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.domain.api.enums.AccountSource;
import neg5.userData.DuplicateLoginException;

public interface AccountApi extends DomainObjectApiLayer<AccountDTO, String> {

    AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException;

    Optional<AccountDTO> findByUsernameOrEmail(String usernameOrEmail);

    Optional<AccountWithHashedPassword> verifyPassword(String usernameOrEmail, String password);

    List<AccountDTO> findByQuery(@Nonnull String query);

    class AccountWithHashedPassword {
        private String username;
        private String name;
        private String hashedPassword;
        private AccountSource source;

        public AccountWithHashedPassword(
                String username, String name, String hashedPassword, AccountSource source) {
            this.username = username;
            this.name = name;
            this.hashedPassword = hashedPassword;
            this.source = source;
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

        public AccountSource getSource() {
            return source;
        }
    }
}
