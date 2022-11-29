package neg5.domain.impl.entities.transformers;

import com.google.gson.Gson;
import neg5.domain.impl.entities.transformers.data.Match;
import neg5.domain.impl.entities.transformers.data.Phase;
import neg5.domain.impl.entities.transformers.data.TeamInMatch;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayer;
import org.hibernate.transform.ResultTransformer;
import neg5.gson.GsonProvider;
import org.postgresql.util.PGobject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchTransformer implements ResultTransformer {

    private final Gson gson;

    public MatchTransformer() {
        gson = new GsonProvider().get();
    }

    @Override
    public Match transformTuple(Object[] tuple, String[] aliases) {
        Match match = new Match();
        match.setId((String) tuple[1]);
        match.setTournamentId((String) tuple[2]);
        match.setRound((Integer) tuple[3]);
        match.setTossupsHeard((Integer) tuple[4]);
        match.setPhases(getPhases(tuple));
        match.setTeams(getTeams(tuple));
        match.setModerator((String) tuple[6]);
        match.setPacket((String) tuple[7]);
        match.setNotes((String) tuple[8]);
        match.setSerialId((String) tuple[9]);
        match.setAddedAt((Timestamp) tuple[11]);
        return match;
    }

    @Override
    public List<Match> transformList(List collection) {
        return new ArrayList<>(collection);
    }

    private Set<Phase> getPhases(Object[] tuple) {
        return Arrays.stream((Object[]) tuple[0])
                .map(object -> {
                    PGobject phaseObj = (PGobject) object;
                    return gson.fromJson(phaseObj.getValue(), Phase.class);
                })
                .collect(Collectors.toSet());
    }

    private Set<TeamInMatch> getTeams(Object[] tuple) {
        Set<TeamMatchPlayer> players = getPlayers(tuple);
        return Arrays.stream((Object[]) tuple[5])
                .map(object -> {
                    PGobject phaseObj = (PGobject) object;
                    TeamInMatch team = gson.fromJson(phaseObj.getValue(), TeamInMatch.class);
                    team.setPlayers(players.stream()
                        .filter(p -> p.getTeamId().equals(team.getTeamId()))
                        .collect(Collectors.toSet())
                    );
                    return team;
                })
                .collect(Collectors.toSet());
    }

    private Set<TeamMatchPlayer> getPlayers(Object[] tuple) {
        return Arrays.stream((Object[]) tuple[10])
                .map(object -> {
                    PGobject phaseObj = (PGobject) object;
                    return gson.fromJson(phaseObj.getValue(), TeamMatchPlayer.class);
                })
                .collect(Collectors.toSet());
    }
}
