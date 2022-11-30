package neg5.domain.impl.entities.converters;

import javax.persistence.Converter;
import neg5.domain.api.enums.TossupAnswerType;

/** Enum converter for {@link TossupAnswerType} */
@Converter(autoApply = true)
public class TossupAnswerTypeConverter extends StringIdentifiableConverter<TossupAnswerType> {}
