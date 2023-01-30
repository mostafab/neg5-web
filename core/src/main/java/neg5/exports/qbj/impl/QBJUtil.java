package neg5.exports.qbj.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.api.enums.PlayerYear;
import neg5.exports.qbj.api.QbjPlayerDTO;
import neg5.exports.qbj.api.QbjRegistrationDTO;
import neg5.exports.qbj.api.QbjTeamDTO;

public class QBJUtil {

    private QBJUtil() {}

    public static List<QbjRegistrationDTO> toRegistrations(
            List<TournamentTeamDTO> teams, List<TournamentTeamGroupDTO> teamGroups) {
        List<QbjRegistrationDTO> result = new ArrayList<>();

        Map<Long, TournamentTeamGroupDTO> teamGroupsById =
                teamGroups.stream()
                        .collect(
                                Collectors.toMap(
                                        TournamentTeamGroupDTO::getId, Function.identity()));
        List<TournamentTeamDTO> teamsWithNoGroup =
                teams.stream()
                        .filter(
                                t ->
                                        t.getTeamGroupId() == null
                                                || !teamGroupsById.containsKey(t.getTeamGroupId()))
                        .collect(Collectors.toList());
        teamsWithNoGroup.forEach(
                team -> result.add(toRegistration(team.getName(), Lists.newArrayList(team))));

        teams.stream()
                .filter(
                        t ->
                                t.getTeamGroupId() != null
                                        && teamGroupsById.containsKey(t.getTeamGroupId()))
                .collect(Collectors.groupingBy(TournamentTeamDTO::getTeamGroupId))
                .forEach(
                        (key, groupTeams) -> {
                            String groupName = teamGroupsById.get(key).getName();
                            result.add(toRegistration(groupName, groupTeams));
                        });

        return result.stream()
                .sorted(Comparator.comparing(QbjRegistrationDTO::getName))
                .collect(Collectors.toList());
    }

    private static QbjRegistrationDTO toRegistration(String name, List<TournamentTeamDTO> teams) {
        QbjRegistrationDTO registration = new QbjRegistrationDTO();
        registration.setId(String.format("registration_%s", name));
        registration.setName(name);
        registration.setTeams(
                teams.stream()
                        .map(
                                team -> {
                                    QbjTeamDTO qbjTeam = new QbjTeamDTO();
                                    qbjTeam.setId(String.format("team_%s", team.getId()));
                                    qbjTeam.setName(team.getName());
                                    qbjTeam.setPlayers(
                                            team.getPlayers().stream()
                                                    .map(
                                                            player -> {
                                                                QbjPlayerDTO qbjPlayer =
                                                                        new QbjPlayerDTO();
                                                                qbjPlayer.setId(
                                                                        String.format(
                                                                                "player_%s",
                                                                                player.getId()));
                                                                qbjPlayer.setName(player.getName());
                                                                if (player.getYear() != null
                                                                        && player.getYear()
                                                                                != PlayerYear.NA) {
                                                                    qbjPlayer.setYear(
                                                                            player.getYear()
                                                                                    .getQbjOrdinal());
                                                                }
                                                                return qbjPlayer;
                                                            })
                                                    .collect(Collectors.toList()));

                                    return qbjTeam;
                                })
                        .sorted(Comparator.comparing(QbjTeamDTO::getName))
                        .collect(Collectors.toList()));

        return registration;
    }
}
