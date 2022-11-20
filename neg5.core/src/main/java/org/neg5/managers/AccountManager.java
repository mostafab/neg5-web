package org.neg5.managers;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import org.neg5.AccountCreationDTO;
import org.neg5.AccountDTO;
import org.neg5.daos.AccountDAO;
import org.neg5.data.Account;
import org.neg5.login.DuplicateLoginException;
import org.neg5.mappers.AccountMapper;

import javax.persistence.NoResultException;

public class AccountManager extends AbstractDTOManager<Account, AccountDTO, String> {

    private final AccountDAO accountDAO;
    private final AccountMapper accountMapper;

    private static final Integer SALT_ROUNDS = 10;

    @Inject
    public AccountManager(AccountDAO accountDAO,
                          AccountMapper accountMapper) {
        this.accountDAO = accountDAO;
        this.accountMapper = accountMapper;
    }

    public AccountDTO createAccount(AccountCreationDTO account) throws DuplicateLoginException {
        boolean accountIsNew = verifyUsernameIsNew(account.getUsername());
        if (!accountIsNew) {
            throw new DuplicateLoginException("There exists an account with username: " + account.getUsername());
        }
        String salt = BCrypt.gensalt(SALT_ROUNDS);
        String hashedPassword = BCrypt.hashpw(account.getPassword(), salt);
        return createAccountInTransaction(account, hashedPassword);
    }

    public boolean verifyPassword(String username, String password) {
        String hashedPassword = getHashedPassword(username);
        return BCrypt.checkpw(password, hashedPassword);
    }

    @Transactional
    boolean verifyUsernameIsNew(String username) {
        try {
            AccountDTO account = get(username);
            return account == null;
        } catch (NoResultException e) {
            return true;
        }
    }

    @Transactional
    AccountDTO createAccountInTransaction(AccountCreationDTO accountCreationDTO, String hashedPassword) {
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
        Account account = accountDAO.getByUsername(username);
        if (account == null) {
            return null;
        }
        return account.getHashedPassword();
    }
}
