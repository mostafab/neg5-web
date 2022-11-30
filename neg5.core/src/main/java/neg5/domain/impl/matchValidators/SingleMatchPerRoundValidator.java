package neg5.domain.impl.matchValidators;

import com.google.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.MatchTeamDTO;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentTeamDTO;

@Singleton
public class SingleMatchPerRoundValidator implements TournamentMatchValidator {

    @Override
    @Nonnull
    public FieldValidationErrors getErrors(@Nonnull MatchValidationContext validationContext) {
        FieldValidationErrors errors = new FieldValidationErrors();
        TournamentMatchDTO subject = validationContext.getSubject();
        List<TournamentMatchDTO> allMatches = validationContext.getAllMatches();
        Map<String, TournamentTeamDTO> teamNamesById = validationContext.getTeamNamesById();
        if (subject.getRound() == null
                || subject.getTeams() == null
                || subject.getTournamentId() == null
                || teamNamesById == null) {
            return errors;
        }
        List<TournamentMatchDTO> otherMatchesInSameRound =
                allMatches.stream()
                        .filter(match -> !match.getId().equals(subject.getId()))
                        .filter(match -> subject.getRound().equals(match.getRound()))
                        .collect(Collectors.toList());
        aggregateNonUniqueRoundErrors(
                otherMatchesInSameRound, subject, subject.getRound(), errors, teamNamesById);

        return errors;
    }

    private void aggregateNonUniqueRoundErrors(
            List<TournamentMatchDTO> otherMatchesInRound,
            TournamentMatchDTO subject,
            Long round,
            FieldValidationErrors errors,
            Map<String, TournamentTeamDTO> teamsById) {
        final Set<String> teamIdsInSubjectMatch =
                subject.getTeams().stream()
                        .map(MatchTeamDTO::getTeamId)
                        .collect(Collectors.toSet());
        for (TournamentMatchDTO match : otherMatchesInRound) {
            for (MatchTeamDTO team : match.getTeams()) {
                if (teamIdsInSubjectMatch.contains(team.getTeamId())) {
                    String message =
                            String.format(
                                    "%s has already played a match in round %d: %s",
                                    teamsById.get(team.getTeamId()).getName(),
                                    round,
                                    formatTeamVsString(match, teamsById));
                    errors.add("round", message);
                }
            }
        }
    }

    private String formatTeamVsString(
            TournamentMatchDTO match, Map<String, TournamentTeamDTO> teamNamesById) {
        List<String> teamNamesAndScore =
                match.getTeams().stream()
                        .map(
                                team ->
                                        String.format(
                                                "%s (%d)",
                                                teamNamesById.get(team.getTeamId()).getName(),
                                                team.getScore()))
                        .collect(Collectors.toList());
        return String.join(" vs ", teamNamesAndScore);
    }
}
