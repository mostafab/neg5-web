package neg5.exports.qbj.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentTeamApi;
import neg5.exports.qbj.api.QbjApi;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.enums.TossupAnswerType;
import neg5.exports.qbj.api.AnswerTypeDTO;
import neg5.exports.qbj.api.RegistrationDTO;
import neg5.exports.qbj.api.ScoringRulesDTO;
import neg5.exports.qbj.api.TournamentQbjDTO;
import neg5.exports.qbj.api.TournamentSiteDTO;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class QbjApiImpl implements QbjApi {

    private final TournamentApi tournamentManager;
    private final TournamentTeamApi teamApi;

    @Inject
    public QbjApiImpl(TournamentApi tournamentManager,
                      TournamentTeamApi teamApi) {
        this.tournamentManager = tournamentManager;
        this.teamApi = teamApi;
    }

    public TournamentQbjDTO exportToQbjFormat(String tournamentId) {
        TournamentDTO tournament = tournamentManager.get(tournamentId);

        TournamentQbjDTO qbj = new TournamentQbjDTO();
        qbj.setName(tournament.getName());
        qbj.setQuestionSet(tournament.getQuestionSet());
        qbj.setTournamentSite(getSite(tournament));
        qbj.setScoringRules(getScoringRules(tournament));

        qbj.setRegistrations(getRegistrations(tournamentId));

        return qbj;
    }

    private TournamentSiteDTO getSite(TournamentDTO tournament) {
        TournamentSiteDTO site = new TournamentSiteDTO();
        site.setName(tournament.getLocation());

        return site;
    }

    private ScoringRulesDTO getScoringRules(TournamentDTO tournament) {
        ScoringRulesDTO rules = new ScoringRulesDTO();
        rules.setBonusesBounceBack(tournament.getUsesBouncebacks());
        rules.setMinimumPartsPerBonus(tournament.getPartsPerBonus());
        rules.setPointsPerBonusPart(tournament.getBonusPointValue());

        if (tournament.getPartsPerBonus() != null
                && tournament.getBonusPointValue() != null) {
            rules.setMaximumBonusScore(tournament.getPartsPerBonus() * tournament.getBonusPointValue());
        }

        rules.setAnswerTypes(
                tournament.getTossupValues().stream()
                        .map(tv -> {
                            AnswerTypeDTO answerType = new AnswerTypeDTO();
                            answerType.setValue(tv.getValue());
                            answerType.setAwardsBonus(TossupAnswerType.NEG != tv.getAnswerType()
                                    && tv.getAnswerType() != null);
                            return answerType;
                        })
                        .collect(Collectors.toList())
        );

        return rules;
    }

    private List<RegistrationDTO> getRegistrations(String tournamentId) {
        List<TournamentTeamDTO> teams = teamApi.findAllByTournamentId(tournamentId);
        return QBJUtil.toRegistrations(teams);
    }
}
