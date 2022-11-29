package org.neg5.data.converters;

import org.neg5.enums.Identifiable;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converter used to convert a DB column to an enum that implements {@link Identifiable}
 * @param <EnumType> the enum type
 * @param <ColumnType> the type serialized to the database.
 */
public abstract class IdentifiableConverter<EnumType extends Enum<EnumType> & Identifiable<ColumnType>, ColumnType>
        implements AttributeConverter<EnumType, ColumnType> {

    private final Class<EnumType> enumTypeClass;
    private final Map<ColumnType, EnumType> enumValuesById;

    IdentifiableConverter() {
        enumTypeClass = (Class<EnumType>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        enumValuesById = Arrays.stream(enumTypeClass.getEnumConstants()).collect(Collectors.toMap(
                enumValue -> enumValue.getId(),
                Function.identity()
        ));
    }

    @Override
    public EnumType convertToEntityAttribute(ColumnType s) {
        if (s == null) {
            return null;
        }
        return Optional.ofNullable(enumValuesById.get(s))
                .orElseThrow(() -> new RuntimeException("Unknown enum value " + s + " for enum " + enumTypeClass));
    }

    @Override
    public ColumnType convertToDatabaseColumn(EnumType e) {
        if (e == null) {
            return null;
        }
        return e.getId();
    }
}
