package neg5.domain.impl.mappers;

import com.google.inject.Singleton;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.impl.entities.TournamentTeamGroup;

@Singleton
public class TournamentTeamGroupMapper
        extends AbstractObjectMapper<TournamentTeamGroup, TournamentTeamGroupDTO> {

    public TournamentTeamGroupMapper() {
        super(TournamentTeamGroup.class, TournamentTeamGroupDTO.class);
    }
}
