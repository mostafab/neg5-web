package neg5.api;

import org.neg5.AccountCreationDTO;
import org.neg5.AccountDTO;
import org.neg5.login.DuplicateLoginException;

public interface AccountApi extends ObjectApiLayer<AccountDTO, String> {

    AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException;

    boolean verifyPassword(String username, String password);
}
