package neg5.domain.impl.entities.converters;

import javax.persistence.Converter;
import neg5.domain.api.enums.PlayerYear;

@Converter(autoApply = true)
public class PlayerYearConverter extends StringIdentifiableConverter<PlayerYear> {}
