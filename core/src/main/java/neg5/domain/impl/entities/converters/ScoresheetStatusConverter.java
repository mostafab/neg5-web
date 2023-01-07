package neg5.domain.impl.entities.converters;

import javax.persistence.Converter;
import neg5.domain.api.enums.ScoresheetStatus;

@Converter(autoApply = true)
public class ScoresheetStatusConverter extends StringIdentifiableConverter<ScoresheetStatus> {}
