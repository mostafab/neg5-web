package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.List;
import javax.persistence.NoResultException;
import neg5.domain.api.AccountApi;
import neg5.domain.api.AccountCreationDTO;
import neg5.domain.api.AccountDTO;
import neg5.domain.impl.dataAccess.AccountDAO;
import neg5.domain.impl.entities.Account;
import neg5.domain.impl.mappers.AccountMapper;
import neg5.userData.DuplicateLoginException;
import org.mindrot.jbcrypt.BCrypt;

public class AccountApiImpl extends AbstractApiLayerImpl<Account, AccountDTO, String>
        implements AccountApi {

    private final AccountDAO accountDAO;
    private final AccountMapper accountMapper;

    private static final Integer SALT_ROUNDS = 10;

    @Inject
    public AccountApiImpl(AccountDAO accountDAO, AccountMapper accountMapper) {
        this.accountDAO = accountDAO;
        this.accountMapper = accountMapper;
    }

    public AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException {
        boolean accountIsNew = verifyUniqueAccount(account.getUsername(), account.getEmail());
        if (!accountIsNew) {
            throw new DuplicateLoginException(
                    "There exists an account with username: " + account.getUsername());
        }
        String salt = BCrypt.gensalt(SALT_ROUNDS);
        String hashedPassword = BCrypt.hashpw(account.getPassword(), salt);
        return createAccountInTransaction(account, hashedPassword);
    }

    public boolean verifyPassword(String username, String password) {
        String hashedPassword = getHashedPassword(username);
        if (hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(password, hashedPassword);
    }

    @Transactional
    boolean verifyUniqueAccount(String username, String email) {
        List<Account> accounts = accountDAO.getByUsernameOrEmail(username, email);
        return accounts.isEmpty();
    }

    @Transactional
    AccountDTO createAccountInTransaction(
            AccountCreationDTO accountCreationDTO, String hashedPassword) {
        Account account = new Account();
        account.setId(accountCreationDTO.getUsername());
        account.setEmail(accountCreationDTO.getEmail());
        account.setName(accountCreationDTO.getName());
        account.setHashedPassword(hashedPassword);
        return getMapper().toDTO(getDao().save(account));
    }

    @Override
    protected AccountDAO getDao() {
        return accountDAO;
    }

    @Override
    protected AccountMapper getMapper() {
        return accountMapper;
    }

    @Transactional
    String getHashedPassword(String username) {
        try {
            Account account = accountDAO.getByUsername(username);
            if (account == null) {
                return null;
            }
            return account.getHashedPassword();
        } catch (NoResultException e) {
            return null;
        }
    }
}
