package neg5.domain.impl.entities.converters;

import javax.persistence.Converter;
import neg5.domain.api.enums.State;

@Converter(autoApply = true)
public class StateConverter extends StringIdentifiableConverter<State> {}
