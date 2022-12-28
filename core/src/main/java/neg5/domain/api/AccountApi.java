package neg5.domain.api;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import neg5.userData.DuplicateLoginException;

public interface AccountApi extends DomainObjectApiLayer<AccountDTO, String> {

    AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException;

    Optional<String> verifyPassword(String usernameOrEmail, String password);

    List<AccountDTO> findByQuery(@Nonnull String query);
}
