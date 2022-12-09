package neg5.domain.impl.entities.generators;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET;
import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_NUMBER_GENERATOR;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TournamentIdGenerator implements IdentifierGenerator {

    private static final int ID_LENGTH = 15;

    @Override
    public Serializable generate(
            SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException {
        return NanoIdUtils.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, ID_LENGTH);
    }
}
