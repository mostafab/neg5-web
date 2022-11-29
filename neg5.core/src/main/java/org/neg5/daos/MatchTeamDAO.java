package org.neg5.daos;

import neg5.domain.impl.entities.MatchTeam;
import org.neg5.data.embeddables.MatchTeamId;

public class MatchTeamDAO extends AbstractDAO<MatchTeam, MatchTeamId> {

    protected MatchTeamDAO() {
        super(MatchTeam.class);
    }
}
