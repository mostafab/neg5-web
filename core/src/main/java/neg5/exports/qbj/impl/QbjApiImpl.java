package neg5.exports.qbj.impl;

import static neg5.exports.qbj.impl.QBJUtil.getReferenceId;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentMatchApi;
import neg5.domain.api.TournamentMatchDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.api.enums.TossupAnswerType;
import neg5.exports.qbj.api.AnswerTypeDTO;
import neg5.exports.qbj.api.QbjApi;
import neg5.exports.qbj.api.QbjObjectType;
import neg5.exports.qbj.api.QbjPhaseDTO;
import neg5.exports.qbj.api.QbjReferenceDTO;
import neg5.exports.qbj.api.QbjRegistrationDTO;
import neg5.exports.qbj.api.QbjRootDTO;
import neg5.exports.qbj.api.QbjRoundDTO;
import neg5.exports.qbj.api.QbjScoringRulesDTO;
import neg5.exports.qbj.api.QbjTournamentDTO;
import neg5.exports.qbj.api.QbjTournamentSiteDTO;

@Singleton
public class QbjApiImpl implements QbjApi {

    private final TournamentApi tournamentManager;
    private final TournamentTeamApi teamApi;
    private final TournamentTeamGroupApi teamGroupApi;
    private final TournamentMatchApi matchApi;

    @Inject
    public QbjApiImpl(
            TournamentApi tournamentManager,
            TournamentTeamApi teamApi,
            TournamentTeamGroupApi teamGroupApi,
            TournamentMatchApi matchApi) {
        this.tournamentManager = tournamentManager;
        this.teamApi = teamApi;
        this.teamGroupApi = teamGroupApi;
        this.matchApi = matchApi;
    }

    public QbjRootDTO exportToQbjFormat(String tournamentId) {
        QbjRootDTO root = new QbjRootDTO();
        root.setVersion("2.1");
        root.setObjects(new ArrayList<>());

        TournamentDTO tournament = tournamentManager.get(tournamentId);

        QbjTournamentDTO tournamentQbjObject = new QbjTournamentDTO();
        tournamentQbjObject.setName(tournament.getName());
        tournamentQbjObject.setQuestionSet(tournament.getQuestionSet());
        tournamentQbjObject.setTournamentSite(getSite(tournament));
        tournamentQbjObject.setScoringRules(getScoringRules(tournament));

        List<QbjRegistrationDTO> registrations = getRegistrations(tournamentId);

        tournamentQbjObject.setRegistrations(
                registrations.stream()
                        .map(r -> QbjReferenceDTO.fromRef(r.getId()))
                        .collect(Collectors.toList()));

        List<TournamentMatchDTO> matches = matchApi.findAllByTournamentId(tournamentId);
        List<QbjPhaseDTO> phases = getPhases(tournament, matches);

        tournamentQbjObject.setPhases(
                phases.stream()
                        .map(r -> QbjReferenceDTO.fromRef(r.getId()))
                        .collect(Collectors.toList()));

        root.getObjects().add(tournamentQbjObject);
        root.getObjects().addAll(registrations);
        root.getObjects().addAll(QBJUtil.toMatches(matches));
        root.getObjects().addAll(phases);

        return root;
    }

    private QbjTournamentSiteDTO getSite(TournamentDTO tournament) {
        QbjTournamentSiteDTO site = new QbjTournamentSiteDTO();
        site.setName(tournament.getLocation());

        return site;
    }

    private QbjScoringRulesDTO getScoringRules(TournamentDTO tournament) {
        QbjScoringRulesDTO rules = new QbjScoringRulesDTO();
        rules.setBonusesBounceBack(tournament.getUsesBouncebacks());
        rules.setMinimumPartsPerBonus(tournament.getPartsPerBonus());
        rules.setPointsPerBonusPart(tournament.getBonusPointValue());
        rules.setMaximumPlayersPerTeam(tournament.getMaxActivePlayersPerTeam());

        if (tournament.getPartsPerBonus() != null && tournament.getBonusPointValue() != null) {
            rules.setMaximumBonusScore(
                    tournament.getPartsPerBonus() * tournament.getBonusPointValue());
        }

        rules.setAnswerTypes(
                tournament.getTossupValues().stream()
                        .map(
                                tv -> {
                                    AnswerTypeDTO answerType = new AnswerTypeDTO();
                                    answerType.setId(
                                            getReferenceId(
                                                    QbjObjectType.ANSWER_TYPE,
                                                    tv.getValue().toString()));
                                    answerType.setValue(tv.getValue());
                                    answerType.setAwardsBonus(
                                            TossupAnswerType.NEG != tv.getAnswerType()
                                                    && tv.getAnswerType() != null);
                                    return answerType;
                                })
                        .collect(Collectors.toList()));

        return rules;
    }

    private List<QbjRegistrationDTO> getRegistrations(String tournamentId) {
        List<TournamentTeamDTO> teams = teamApi.findAllByTournamentId(tournamentId);
        List<TournamentTeamGroupDTO> groups = teamGroupApi.findAllByTournamentId(tournamentId);
        return QBJUtil.toRegistrations(teams, groups);
    }

    private List<QbjPhaseDTO> getPhases(
            TournamentDTO tournament, List<TournamentMatchDTO> matches) {
        Map<String, List<TournamentMatchDTO>> matchesByPhase =
                matches.stream()
                        .filter(match -> match.getPhases() != null && !match.getPhases().isEmpty())
                        .collect(
                                Collectors.groupingBy(
                                        match -> match.getPhases().stream().findFirst().get()));
        return tournament.getPhases().stream()
                .map(
                        phase -> {
                            QbjPhaseDTO qbjPhase = new QbjPhaseDTO();
                            qbjPhase.setId(getReferenceId(QbjObjectType.PHASE, phase.getId()));
                            qbjPhase.setName(phase.getName());
                            List<TournamentMatchDTO> phaseMatches =
                                    matchesByPhase.get(phase.getId());
                            if (phaseMatches == null) {
                                return qbjPhase;
                            }
                            qbjPhase.setRounds(new ArrayList<>());
                            Map<Long, List<TournamentMatchDTO>> matchesByRound =
                                    phaseMatches.stream()
                                            .filter(m -> m.getRound() != null)
                                            .collect(
                                                    Collectors.groupingBy(
                                                            TournamentMatchDTO::getRound));
                            matchesByRound.forEach(
                                    (round, roundMatches) -> {
                                        QbjRoundDTO qbjRound = new QbjRoundDTO();
                                        qbjRound.setName(String.format("Round %d", round));
                                        qbjRound.setMatches(
                                                roundMatches.stream()
                                                        .map(
                                                                rm ->
                                                                        QbjReferenceDTO.fromRef(
                                                                                getReferenceId(
                                                                                        QbjObjectType
                                                                                                .MATCH,
                                                                                        rm
                                                                                                .getId())))
                                                        .collect(Collectors.toList()));

                                        qbjPhase.getRounds().add(qbjRound);
                                    });
                            return qbjPhase;
                        })
                .collect(Collectors.toList());
    }
}
