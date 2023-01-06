package neg5.transactions;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import java.util.function.Supplier;

@Singleton
public class TransactionHelper {

    @Transactional
    public <T> T runInTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}
