package neg5.exports.qbj.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.exports.qbj.api.QbjPlayerDTO;
import neg5.exports.qbj.api.QbjTeamDTO;
import neg5.exports.qbj.api.RegistrationDTO;

public class QBJUtil {

    private QBJUtil() {}

    public static List<RegistrationDTO> toRegistrations(
            List<TournamentTeamDTO> teams, List<TournamentTeamGroupDTO> teamGroups) {
        List<RegistrationDTO> result = new ArrayList<>();

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

        return result;
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
