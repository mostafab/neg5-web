package neg5.exports.qbj.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.api.enums.PlayerYear;
import neg5.exports.qbj.api.QbjMatchDTO;
import neg5.exports.qbj.api.QbjMatchTeamDTO;
import neg5.exports.qbj.api.QbjObjectType;
import neg5.exports.qbj.api.QbjPlayerDTO;
import neg5.exports.qbj.api.QbjReferenceDTO;
import neg5.exports.qbj.api.QbjRegistrationDTO;
import neg5.exports.qbj.api.QbjTeamDTO;

public class QBJUtil {

    private QBJUtil() {}

    public static List<QbjMatchDTO> toMatches(List<TournamentMatchDTO> matches) {
        return matches.stream()
                .map(
                        match -> {
                            QbjMatchDTO qbjMatch = new QbjMatchDTO();
                            qbjMatch.setId(getReferenceId(QbjObjectType.MATCH, match.getId()));
                            qbjMatch.setLocation(match.getRoom());
                            qbjMatch.setModerator(match.getModerator());
                            qbjMatch.setTiebreaker(match.getIsTiebreaker());
                            qbjMatch.setTossupsRead(match.getTossupsHeard());
                            qbjMatch.setNotes(match.getNotes());
                            qbjMatch.setSerial(match.getSerialId());

                            qbjMatch.setMatchTeams(
                                    match.getTeams().stream()
                                            .map(
                                                    team -> {
                                                        QbjMatchTeamDTO qbjTeam =
                                                                new QbjMatchTeamDTO();
                                                        String reference =
                                                                getReferenceId(
                                                                        QbjObjectType.TEAM,
                                                                        team.getTeamId());
                                                        qbjTeam.setTeam(
                                                                QbjReferenceDTO.fromRef(reference));

                                                        return qbjTeam;
                                                    })
                                            .collect(Collectors.toList()));

                            return qbjMatch;
                        })
                .collect(Collectors.toList());
    }

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
        registration.setId(getReferenceId(QbjObjectType.REGISTRATION, name));
        registration.setName(name);
        registration.setTeams(
                teams.stream()
                        .map(
                                team -> {
                                    QbjTeamDTO qbjTeam = new QbjTeamDTO();
                                    qbjTeam.setId(getReferenceId(QbjObjectType.TEAM, team.getId()));
                                    qbjTeam.setName(team.getName());
                                    qbjTeam.setPlayers(
                                            team.getPlayers().stream()
                                                    .map(
                                                            player -> {
                                                                QbjPlayerDTO qbjPlayer =
                                                                        new QbjPlayerDTO();
                                                                qbjPlayer.setId(
                                                                        getReferenceId(
                                                                                QbjObjectType
                                                                                        .PLAYER,
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

    public static String getReferenceId(QbjObjectType objectType, String id) {
        return String.format("%s_%s", objectType.getId(), id);
    }
}
