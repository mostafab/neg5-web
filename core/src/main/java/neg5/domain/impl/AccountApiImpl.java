package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
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

    @Override
    public AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException {
        account.setPassword(account.getPassword().trim());
        account.setUsername(account.getUsername().trim().toLowerCase());
        boolean accountIsNew = verifyUniqueAccount(account.getUsername(), account.getEmail());
        if (!accountIsNew) {
            throw new DuplicateLoginException(
                    "There exists an account with username: " + account.getUsername());
        }
        String salt = BCrypt.gensalt(SALT_ROUNDS);
        String hashedPassword = BCrypt.hashpw(account.getPassword(), salt);
        return createAccountInTransaction(account, hashedPassword);
    }

    @Override
    @Transactional
    public Optional<AccountDTO> findByUsernameOrEmail(String usernameOrEmail) {
        try {
            return Optional.ofNullable(accountDAO.getByUsernameOrEmail(usernameOrEmail))
                    .map(accountMapper::toDTO);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccountWithHashedPassword> verifyPassword(
            String usernameOrEmail, String password) {
        AccountWithHashedPassword accountWithHashedPassword = getHashedPassword(usernameOrEmail);
        if (accountWithHashedPassword == null
                || !BCrypt.checkpw(password, accountWithHashedPassword.getHashedPassword())) {
            return Optional.empty();
        }
        return Optional.of(accountWithHashedPassword);
    }

    @Override
    @Transactional
    public List<AccountDTO> findByQuery(@Nonnull String query) {
        Objects.requireNonNull(query, "query cannot be null.");
        return accountDAO.findByQuery(query.toLowerCase().trim()).stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
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
    AccountWithHashedPassword getHashedPassword(String usernameOrEmail) {
        try {
            Account account = accountDAO.getByUsernameOrEmail(usernameOrEmail);
            if (account == null) {
                return null;
            }
            return new AccountWithHashedPassword(
                    account.getId(), account.getName(), account.getHashedPassword());
        } catch (NoResultException e) {
            return null;
        }
    }
}
