package neg5.domain.impl.entities.converters;

import neg5.domain.api.enums.StringIdentifiable;

/**
 * Converter used to convert {@link StringIdentifiable} enums to a db column and string columns to
 * an enum
 *
 * @param <E> the enum type
 */
public abstract class StringIdentifiableConverter<E extends Enum<E> & StringIdentifiable>
        extends IdentifiableConverter<E, String> {}
