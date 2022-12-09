package neg5.domain.impl.entities.transformers;

import com.google.gson.Gson;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import neg5.domain.impl.entities.transformers.data.Match;
import neg5.domain.impl.entities.transformers.data.Phase;
import neg5.domain.impl.entities.transformers.data.TeamInMatch;
import neg5.domain.impl.entities.transformers.data.TeamMatchPlayer;
import neg5.gson.GsonProvider;
import org.hibernate.transform.ResultTransformer;
import org.postgresql.util.PGobject;

public class MatchTransformer implements ResultTransformer {

    private final Gson gson;

    public static final MatchTransformer INSTANCE = new MatchTransformer();

    private MatchTransformer() {
        gson = new GsonProvider().get();
    }

    @Override
    public Match transformTuple(Object[] tuple, String[] aliases) {
        Match match = new Match();
        match.setId((String) tuple[1]);
        match.setTournamentId((String) tuple[2]);
        match.setRound((Integer) tuple[3]);
        match.setTossupsHeard((Integer) tuple[4]);
        match.setPhases(getPhases((Object[]) tuple[0]));
        match.setTeams(getTeams((Object[]) tuple[5], (Object[]) tuple[10]));
        match.setModerator((String) tuple[6]);
        match.setPacket((String) tuple[7]);
        match.setNotes((String) tuple[8]);
        match.setSerialId((String) tuple[9]);
        match.setAddedAt((Timestamp) tuple[11]);
        match.setIsTiebreaker((Boolean) tuple[12]);
        return match;
    }

    @Override
    public List<Match> transformList(List collection) {
        return new ArrayList<>(collection);
    }

    private Set<Phase> getPhases(Object[] phasesArray) {
        return Arrays.stream(phasesArray)
                .map(
                        object -> {
                            String json =
                                    object instanceof PGobject
                                            ? ((PGobject) object).getValue()
                                            : (String) object;
                            return gson.fromJson(json, Phase.class);
                        })
                .collect(Collectors.toSet());
    }

    private Set<TeamInMatch> getTeams(Object[] teamArray, Object[] playersArray) {
        Set<TeamMatchPlayer> players = getPlayers(playersArray);
        return Arrays.stream(teamArray)
                .map(
                        object -> {
                            String json =
                                    object instanceof PGobject
                                            ? ((PGobject) object).getValue()
                                            : (String) object;
                            TeamInMatch team = gson.fromJson(json, TeamInMatch.class);
                            team.setPlayers(
                                    players.stream()
                                            .filter(p -> p.getTeamId().equals(team.getTeamId()))
                                            .collect(Collectors.toSet()));
                            return team;
                        })
                .collect(Collectors.toSet());
    }

    private Set<TeamMatchPlayer> getPlayers(Object[] playersArray) {
        return Arrays.stream(playersArray)
                .map(
                        object -> {
                            String json =
                                    object instanceof PGobject
                                            ? ((PGobject) object).getValue()
                                            : (String) object;
                            return gson.fromJson(json, TeamMatchPlayer.class);
                        })
                .collect(Collectors.toSet());
    }
}
