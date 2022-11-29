package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import neg5.domain.api.MatchPlayerApi;
import neg5.domain.api.MatchTeamApi;
import neg5.domain.impl.entities.embeddables.MatchTeamId;
import org.neg5.MatchTeamDTO;
import neg5.domain.impl.dataAccess.MatchTeamDAO;
import neg5.domain.impl.entities.MatchTeam;
import neg5.domain.impl.mappers.MatchTeamMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MatchTeamApiImpl extends AbstractApiLayerImpl<MatchTeam, MatchTeamDTO, MatchTeamId>
        implements MatchTeamApi {

    private final MatchTeamDAO matchTeamDAO;
    private final MatchTeamMapper matchTeamMapper;
    private final MatchPlayerApi matchPlayerManager;

    @Inject
    public MatchTeamApiImpl(MatchTeamDAO matchTeamDAO,
                            MatchTeamMapper matchTeamMapper,
                            MatchPlayerApi matchPlayerManager) {
        this.matchTeamDAO = matchTeamDAO;
        this.matchTeamMapper = matchTeamMapper;
        this.matchPlayerManager = matchPlayerManager;
    }

    @Override
    @Transactional
    public MatchTeamDTO create(MatchTeamDTO matchTeamDTO) {
        MatchTeamDTO createdMatchTeam = super.create(matchTeamDTO);
        createdMatchTeam.setPlayers(matchTeamDTO.getPlayers() == null
            ? new ArrayList<>()
            : matchTeamDTO.getPlayers().stream()
                .map(player -> {
                    player.setMatchId(matchTeamDTO.getMatchId());
                    player.setTournamentId(matchTeamDTO.getTournamentId());
                    return matchPlayerManager.create(player);
                })
                .collect(Collectors.toList())
        );
        return createdMatchTeam;
    }

    @Override
    protected MatchTeamDAO getDao() {
        return matchTeamDAO;
    }

    @Override
    protected MatchTeamMapper getMapper() {
        return matchTeamMapper;
    }
}
