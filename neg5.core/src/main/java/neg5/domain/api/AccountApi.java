package neg5.domain.api;

import org.neg5.AccountCreationDTO;
import org.neg5.AccountDTO;
import neg5.userData.DuplicateLoginException;

public interface AccountApi extends DomainObjectApiLayer<AccountDTO, String> {

    AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException;

    boolean verifyPassword(String username, String password);
}
