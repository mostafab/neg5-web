package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.AccountDTO;
import neg5.domain.impl.entities.Account;

@Singleton
public class AccountMapper extends AbstractObjectMapper<Account, AccountDTO> {

    protected AccountMapper() {
        super(Account.class, AccountDTO.class);
    }
}
