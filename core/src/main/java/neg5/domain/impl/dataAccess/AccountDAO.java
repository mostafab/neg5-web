package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.util.List;
import neg5.domain.impl.entities.Account;

@Singleton
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

    public List<Account> getByUsernameOrEmail(String username, String email) {
        return getEntityManager()
                .createQuery(
                        "SELECT a from Account a where a.id = :username OR a.email = :email",
                        Account.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getResultList();
    }
}
