package neg5.exports.qbj.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import neg5.domain.api.TournamentApi;
import neg5.domain.api.TournamentDTO;
import neg5.domain.api.TournamentTeamApi;
import neg5.domain.api.TournamentTeamDTO;
import neg5.domain.api.TournamentTeamGroupApi;
import neg5.domain.api.TournamentTeamGroupDTO;
import neg5.domain.api.enums.TossupAnswerType;
import neg5.exports.qbj.api.AnswerTypeDTO;
import neg5.exports.qbj.api.QbjApi;
import neg5.exports.qbj.api.QbjRegistrationDTO;
import neg5.exports.qbj.api.QbjRootDTO;
import neg5.exports.qbj.api.QbjScoringRulesDTO;
import neg5.exports.qbj.api.QbjTournamentDTO;
import neg5.exports.qbj.api.QbjTournamentSiteDTO;

@Singleton
public class QbjApiImpl implements QbjApi {

    private final TournamentApi tournamentManager;
    private final TournamentTeamApi teamApi;
    private final TournamentTeamGroupApi teamGroupApi;

    @Inject
    public QbjApiImpl(
            TournamentApi tournamentManager,
            TournamentTeamApi teamApi,
            TournamentTeamGroupApi teamGroupApi) {
        this.tournamentManager = tournamentManager;
        this.teamApi = teamApi;
        this.teamGroupApi = teamGroupApi;
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

        root.getObjects().add(tournamentQbjObject);
        root.getObjects().addAll(getRegistrations(tournamentId));

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

        if (tournament.getPartsPerBonus() != null && tournament.getBonusPointValue() != null) {
            rules.setMaximumBonusScore(
                    tournament.getPartsPerBonus() * tournament.getBonusPointValue());
        }

        rules.setAnswerTypes(
                tournament.getTossupValues().stream()
                        .map(
                                tv -> {
                                    AnswerTypeDTO answerType = new AnswerTypeDTO();
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
}
