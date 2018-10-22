package org.neg5.data.embeddables;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MatchPlayerAnswerId implements Serializable {

    private String playerId;
    private String matchId;
    private String tournamentId;

    private Integer tossupValue;

    @Column(name = "player_id")
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Column(name = "match_id")
    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    @Column(name = "tournament_id")
    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Column(name = "tossup_value")
    public Integer getTossupValue() {
        return tossupValue;
    }

    public void setTossupValue(Integer tossupValue) {
        this.tossupValue = tossupValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MatchPlayerAnswerId)) {
            return false;
        }
        MatchPlayerAnswerId that = (MatchPlayerAnswerId) obj;
        return that.getPlayerId().equals(playerId)
                && that.getTournamentId().equals(tournamentId)
                && that.getMatchId().equals(matchId)
                && that.getTossupValue().equals(tossupValue);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 3)
                .append(playerId)
                .append(tournamentId)
                .append(matchId)
                .append(tossupValue)
                .toHashCode();
    }
}
