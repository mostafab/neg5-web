package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentTossupValueDTO;
import neg5.domain.impl.entities.TournamentTossupValue;

@Singleton
public class TournamentTossupValueMapper
        extends AbstractObjectMapper<TournamentTossupValue, TournamentTossupValueDTO> {

    protected TournamentTossupValueMapper() {
        super(TournamentTossupValue.class, TournamentTossupValueDTO.class);
    }

    @Override
    protected void enrichDTO(
            TournamentTossupValueDTO dto, TournamentTossupValue tournamentTossupValue) {
        dto.setValue(tournamentTossupValue.getId().getValue());
    }

    @Override
    public TournamentTossupValue mergeToEntity(TournamentTossupValueDTO tournamentTossupValueDTO) {
        TournamentTossupValue entity = super.mergeToEntity(tournamentTossupValueDTO);
        entity.getId().setValue(tournamentTossupValueDTO.getValue());
        return entity;
    }

    @Override
    protected void addMappings() {
        getEntityToDTOTypeMap()
                .addMappings(
                        mapper -> {
                            mapper.map(
                                    tournamentTossupValue ->
                                            tournamentTossupValue.getId().getValue(),
                                    TournamentTossupValueDTO::setValue);
                        });
    }
}
