package org.neg5.data.converters;

import neg5.domain.api.enums.TossupAnswerType;

import javax.persistence.Converter;

/**
 * Enum converter for {@link TossupAnswerType}
 */
@Converter(autoApply = true)
public class TossupAnswerTypeConverter extends StringIdentifiableConverter<TossupAnswerType> {

}
