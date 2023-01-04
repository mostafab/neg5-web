package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentRulesDTO;

@Singleton
public class TournamentToTournamentRulesMapper
        extends AbstractObjectMapper<TournamentDTO, TournamentRulesDTO> {

    protected TournamentToTournamentRulesMapper() {
        super(TournamentDTO.class, TournamentRulesDTO.class);
    }

    @Override
    protected void addMappings() {
        // This mapping was causing an issue like: Cannot convert List to set:
        // NoSuchElementException. We don't need it on the reverse mapping, so just ignore for now.
        getDtoToEntityTypeMap()
                .addMappings(
                        mp -> {
                            mp.skip(TournamentDTO::setTossupValues);
                        });
    }
}
