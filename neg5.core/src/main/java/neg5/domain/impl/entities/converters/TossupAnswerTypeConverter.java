package org.neg5.data.converters;

import org.neg5.enums.TossupAnswerType;

import javax.persistence.Converter;

/**
 * Enum converter for {@link TossupAnswerType}
 */
@Converter(autoApply = true)
public class TossupAnswerTypeConverter extends StringIdentifiableConverter<TossupAnswerType> {

}
