package org.neg5.matchValidators;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.neg5.FieldValidationErrors;
import org.neg5.MatchTeamDTO;
import org.neg5.TournamentMatchDTO;
import org.neg5.TournamentTeamDTO;
import org.neg5.managers.TournamentTeamManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class SingleMatchPerRoundValidator implements EnhancedMatchValidator {

    private final TournamentTeamManager teamManager;

    @Inject
    public SingleMatchPerRoundValidator(TournamentTeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public FieldValidationErrors getErrors(@Nonnull List<TournamentMatchDTO> allMatches,
                                           @Nonnull TournamentMatchDTO subject) {
        FieldValidationErrors errors = new FieldValidationErrors();
        if (subject.getRound() == null || subject.getTeams() == null || subject.getTournamentId() == null) {
            return errors;
        }
        Map<String, String> teamNamesById = teamManager.findAllByTournamentId(subject.getTournamentId())
                .stream()
                .collect(Collectors.toMap(TournamentTeamDTO::getId, TournamentTeamDTO::getName));
        List<TournamentMatchDTO> otherMatchesInSameRound = allMatches.stream()
                .filter(match -> !match.getId().equals(subject.getId()))
                .filter(match -> subject.getRound().equals(match.getRound()))
                .collect(Collectors.toList());
        aggregateNonUniqueRoundErrors(otherMatchesInSameRound, subject, subject.getRound(), errors, teamNamesById);

        return errors;
    }

    private void aggregateNonUniqueRoundErrors(List<TournamentMatchDTO> otherMatchesInRound,
                                               TournamentMatchDTO subject,
                                               Long round,
                                               FieldValidationErrors errors,
                                               Map<String, String> teamNamesById) {
        final Set<String> teamIdsInSubjectMatch = subject.getTeams().stream()
                .map(MatchTeamDTO::getTeamId)
                .collect(Collectors.toSet());
        for (TournamentMatchDTO match : otherMatchesInRound) {
            for (MatchTeamDTO team : match.getTeams()) {
                if (teamIdsInSubjectMatch.contains(team.getTeamId())) {
                    String message = String.format("%s has already played a match in round %d: %s", teamNamesById.get(team.getTeamId()), round, formatTeamVsString(match, teamNamesById));
                    errors.add("round", message);
                }
            }
        }
    }

    private String formatTeamVsString(TournamentMatchDTO match, Map<String, String> teamNamesById) {
        List<String> teamNamesAndScore = match.getTeams().stream()
                .map(team -> String.format("%s (%d)", teamNamesById.get(team.getTeamId()), team.getScore()))
                .collect(Collectors.toList());
        return String.join(" vs ", teamNamesAndScore);
    }
}
