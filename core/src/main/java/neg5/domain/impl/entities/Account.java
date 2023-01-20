package neg5.domain.impl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import neg5.domain.api.enums.AccountSource;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "account")
@DynamicUpdate
public class Account extends AbstractDataObject<Account> implements IdDataObject<String> {

    private String id;
    private String name;
    private String email;

    private String hashedPassword;
    private AccountSource source;

    @Override
    @Id
    @Column(name = "username")
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "hash")
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Column(name = "source")
    public AccountSource getSource() {
        return source;
    }

    public void setSource(AccountSource source) {
        this.source = source;
    }
}
