package neg5.domain.impl.dataAccess;

import com.google.inject.Singleton;
import java.util.List;
import neg5.domain.impl.entities.Account;

@Singleton
public class AccountDAO extends AbstractDAO<Account, String> {

    public AccountDAO() {
        super(Account.class);
    }

    public List<Account> findByQuery(String query) {
        String param = String.format("%s%s", query, "%");
        return getEntityManager()
                .createQuery(
                        "SELECT a from Account a where lower(a.id) like :query OR lower(a.name) like :query OR lower(a.email) like :query",
                        Account.class)
                .setParameter("query", param)
                .getResultList();
    }

    public Account getByUsernameOrEmail(String usernameOrEmail) {
        return getEntityManager()
                .createQuery(
                        "SELECT a from Account a where a.id = :query OR a.email = :query",
                        Account.class)
                .setParameter("query", usernameOrEmail)
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
