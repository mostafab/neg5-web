package neg5.domain.impl.entities.converters;

import javax.persistence.Converter;
import neg5.domain.api.enums.AccountSource;

@Converter(autoApply = true)
public class AccountSourceConverter extends StringIdentifiableConverter<AccountSource> {}
