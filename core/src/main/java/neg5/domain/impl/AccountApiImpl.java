package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.Optional;
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

    public Optional<String> verifyPassword(String usernameOrEmail, String password) {
        UsernameAndPassword usernameAndPassword = getHashedPassword(usernameOrEmail);
        if (usernameAndPassword == null
                || !BCrypt.checkpw(password, usernameAndPassword.hashedPassword)) {
            return Optional.empty();
        }
        return Optional.of(usernameAndPassword.username);
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
    UsernameAndPassword getHashedPassword(String usernameOrEmail) {
        try {
            Account account = accountDAO.getByUsernameOrEmail(usernameOrEmail);
            if (account == null) {
                return null;
            }
            UsernameAndPassword result = new UsernameAndPassword();
            result.username = account.getId();
            result.hashedPassword = account.getHashedPassword();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

    private static class UsernameAndPassword {
        private String username;
        private String hashedPassword;
    }
}
