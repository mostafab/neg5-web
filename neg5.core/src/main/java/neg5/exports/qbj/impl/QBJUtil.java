package neg5.exports.qbj.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentTeamDTO;
import neg5.exports.qbj.api.QbjPlayerDTO;
import neg5.exports.qbj.api.QbjTeamDTO;
import neg5.exports.qbj.api.RegistrationDTO;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequence;

public class QBJUtil {

    private QBJUtil() {}

    public static List<RegistrationDTO> toRegistrations(List<TournamentTeamDTO> teams) {

        Set<String> seenTeams = new HashSet<>();

        LevenshteinDistance distanceCalculator = new LevenshteinDistance();
        LongestCommonSubsequence nameCalculator = new LongestCommonSubsequence();

        Map<String, List<TournamentTeamDTO>> teamsByCommonName = new HashMap<>();

        teams.forEach(
                team -> {
                    if (!seenTeams.contains(team.getId())) {
                        List<TournamentTeamDTO> matchingTeams =
                                teams.stream()
                                        .filter(
                                                otherTeam ->
                                                        distanceCalculator.apply(
                                                                        team.getName(),
                                                                        otherTeam.getName())
                                                                < 2)
                                        .collect(Collectors.toList());

                        matchingTeams.forEach(t -> seenTeams.add(t.getId()));

                        String commonName = null;
                        if (matchingTeams.size() == 1) {
                            commonName = matchingTeams.get(0).getName();
                        } else if (matchingTeams.size() > 1) {
                            commonName =
                                    nameCalculator
                                            .longestCommonSubsequence(
                                                    matchingTeams.get(0).getName(),
                                                    matchingTeams.get(1).getName())
                                            .toString();
                        }

                        if (commonName != null) {
                            teamsByCommonName.put(commonName.trim(), matchingTeams);
                        }
                    }
                });

        return teamsByCommonName.entrySet().stream()
                .map(entry -> toRegistration(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private static RegistrationDTO toRegistration(String name, List<TournamentTeamDTO> teams) {
        RegistrationDTO registration = new RegistrationDTO();
        registration.setName(name);
        registration.setTeams(
                teams.stream()
                        .map(
                                team -> {
                                    QbjTeamDTO qbjTeam = new QbjTeamDTO();
                                    qbjTeam.setName(team.getName());
                                    qbjTeam.setPlayers(
                                            team.getPlayers().stream()
                                                    .map(
                                                            player -> {
                                                                QbjPlayerDTO qbjPlayer =
                                                                        new QbjPlayerDTO();
                                                                qbjPlayer.setName(player.getName());
                                                                return qbjPlayer;
                                                            })
                                                    .collect(Collectors.toList()));

                                    return qbjTeam;
                                })
                        .collect(Collectors.toList()));

        return registration;
    }
}
