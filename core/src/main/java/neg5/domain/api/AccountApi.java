package neg5.domain.api;

import java.util.Optional;
import neg5.userData.DuplicateLoginException;

public interface AccountApi extends DomainObjectApiLayer<AccountDTO, String> {

    AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException;

    Optional<String> verifyPassword(String usernameOrEmail, String password);
}
