package org.neg5.data.converters;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.neg5.enums.TossupAnswerType;

public class TossupAnswerTypeConverterTest {

    private TossupAnswerTypeConverter tossupAnswerTypeConverter;

    @BeforeEach
    public void setup() {
        tossupAnswerTypeConverter = new TossupAnswerTypeConverter();
    }

    @Test
    public void testConvertToEntityAttribute() {
        for (TossupAnswerType answerType : TossupAnswerType.values()) {
            Assert.assertEquals(answerType + " did not map properly", answerType,
                    tossupAnswerTypeConverter.convertToEntityAttribute(answerType.getId()));
        }
    }

    @Test
    public void testConvertToEntityAttributeNullColumn() {
        Assert.assertNull(tossupAnswerTypeConverter.convertToEntityAttribute(null));
    }

    @Test
    public void testConvertToDatabaseColumn() {
        for (TossupAnswerType answerType : TossupAnswerType.values()) {
            Assert.assertEquals(answerType + " did not map properly", answerType.getId(),
                    tossupAnswerTypeConverter.convertToDatabaseColumn(answerType));
        }
    }

    @Test
    public void testConvertToDatabaseColumnNull() {
        Assert.assertNull(tossupAnswerTypeConverter.convertToDatabaseColumn(null));
    }
}
