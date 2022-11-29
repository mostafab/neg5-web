package neg5.exports.qbj.api;

import org.neg5.qbj.TournamentQbjDTO;

public interface QbjApi {

    TournamentQbjDTO exportToQbjFormat(String tournamentId);
}
