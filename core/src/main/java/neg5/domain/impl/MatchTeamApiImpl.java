package neg5.domain.impl;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.MatchPlayerApi;
import neg5.domain.api.MatchTeamApi;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.impl.dataAccess.MatchTeamDAO;
import neg5.domain.impl.entities.MatchTeam;
import neg5.domain.impl.entities.compositeIds.MatchTeamId;
import neg5.domain.impl.mappers.MatchTeamMapper;

public class MatchTeamApiImpl extends AbstractApiLayerImpl<MatchTeam, MatchTeamDTO, MatchTeamId>
        implements MatchTeamApi {

    private final MatchTeamDAO matchTeamDAO;
    private final MatchTeamMapper matchTeamMapper;
    private final MatchPlayerApi matchPlayerManager;

    @Inject
    public MatchTeamApiImpl(
            MatchTeamDAO matchTeamDAO,
            MatchTeamMapper matchTeamMapper,
            MatchPlayerApi matchPlayerManager) {
        this.matchTeamDAO = matchTeamDAO;
        this.matchTeamMapper = matchTeamMapper;
        this.matchPlayerManager = matchPlayerManager;
    }

    @Override
    @Transactional
    public MatchTeamDTO create(@Nonnull MatchTeamDTO matchTeamDTO) {
        MatchTeamDTO createdMatchTeam = super.create(matchTeamDTO);
        createdMatchTeam.setPlayers(
                matchTeamDTO.getPlayers() == null
                        ? new ArrayList<>()
                        : matchTeamDTO.getPlayers().stream()
                                // Filter out players that don't have any tossups heard so that they
                                // don't show up in this match
                                .filter(player -> player.getTossupsHeard() != null)
                                .map(
                                        player -> {
                                            player.setMatchId(matchTeamDTO.getMatchId());
                                            player.setTournamentId(matchTeamDTO.getTournamentId());
                                            return matchPlayerManager.create(player);
                                        })
                                .collect(Collectors.toList()));
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
