package neg5.domain.impl.dataAccess;

import neg5.domain.impl.entities.Account;

public class AccountDAO extends AbstractDAO<Account, String> {

    public AccountDAO() {
        super(Account.class);
    }

    public Account getByUsername(String username) {
        return getEntityManager()
                .createQuery("SELECT a from Account a where a.id = :username", Account.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
