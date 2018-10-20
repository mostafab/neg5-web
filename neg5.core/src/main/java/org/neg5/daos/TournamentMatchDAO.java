package org.neg5.daos;

import com.google.inject.Singleton;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.neg5.data.TournamentMatch;
import org.neg5.data.transformers.MatchTransformer;
import org.neg5.data.transformers.data.Match;

import java.util.List;

@Singleton
public class TournamentMatchDAO extends AbstractDAO<TournamentMatch> {

    private static final String QUERY = "SELECT match_information.*, COALESCE(match_phases.phases, '{}') as phases\n" +
            "FROM\n" +
            "\n" +
            "(\n" +
            "    SELECT\n" +
            "\n" +
            "    M.id,\n" +
            "    M.tournament_id as \"tournamentId\",\n" +
            "    M.round,\n" +
            "    M.room,\n" +
            "    M.moderator,\n" +
            "    M.packet,\n" +
            "    M.notes,\n" +
            "    M.serial_id as \"serialId\",\n" +
            "    M.tossups_heard as \"tossupsHeard\",\n" +
            "    match_teams.teams\n" +
            "\n" +
            "    FROM\n" +
            "\n" +
            "    (\n" +
            "        SELECT \n" +
            "\n" +
            "        player_team_join.match_id,\n" +
            "        array_agg(\n" +
            "            json_build_object(\n" +
            "                'matchId', player_team_join.match_id,\n" +
            "                'teamId', player_team_join.team_id,\n" +
            "                'score', player_team_join.score,\n" +
            "                'bouncebackPoints', COALESCE(player_team_join.bounceback_points, 0),\n" +
            "                'overtimeTossups', COALESCE(player_team_join.overtime_tossups_gotten, 0),\n" +
            "                'players', player_team_join.player_match_totals\n" +
            "            )\n" +
            "        ) as teams\n" +
            "\n" +
            "        FROM\n" +
            "\n" +
            "        (\n" +
            "            SELECT\n" +
            "            TTM.tournament_id,\n" +
            "            TTM.team_id,\n" +
            "            TTM.match_id,\n" +
            "            TTM.score,\n" +
            "            TTM.bounceback_points,\n" +
            "            TTM.overtime_tossups_gotten,\n" +
            "            array_agg(\n" +
            "                json_build_object(\n" +
            "                    'playerId', TP.id,\n" +
            "                    'matchId', PPM.match_id,\n" +
            "                    'teamId', TP.team_id,\n" +
            "                    'tossupsHeard', COALESCE(PPM.tossups_heard, 0),\n" +
            "                    'tossupValues', COALESCE(player_match_values.tossup_values, '{}')\n" +
            "                )\n" +
            "            ) as player_match_totals\n" +
            "\n" +
            "            FROM\n" +
            "            \n" +
            "            team_plays_in_tournament_match TTM\n" +
            "\n" +
            "            INNER JOIN\n" +
            "\n" +
            "            player_plays_in_tournament_match PPM \n" +
            "\n" +
            "            ON TTM.match_id = PPM.match_id AND TTM.tournament_id = PPM.tournament_id\n" +
            "\n" +
            "            INNER JOIN tournament_player TP\n" +
            "\n" +
            "            ON TP.team_id = TTM.team_id AND TP.id = PPM.player_id\n" +
            "\n" +
            "            INNER JOIN\n" +
            "\n" +
            "            (\n" +
            "                SELECT \n" +
            "                PMT.player_id,\n" +
            "                PMT.match_id,\n" +
            "                array_agg(\n" +
            "                    json_build_object('value', PMT.tossup_value, 'number', PMT.number_gotten, 'playerId', PMT.player_id, 'matchId', PMT.match_id)\n" +
            "                ) as tossup_values\n" +
            "                \n" +
            "                FROM\n" +
            "\n" +
            "                player_match_tossup PMT\n" +
            "\n" +
            "                WHERE PMT.tournament_id = :tournamentId\n" +
            "\n" +
            "                GROUP BY PMT.player_id, PMT.match_id\n" +
            "\n" +
            "            ) as player_match_values\n" +
            "\n" +
            "            ON PPM.player_id = player_match_values.player_id AND PPM.match_id = player_match_values.match_id\n" +
            "\n" +
            "            WHERE TTM.tournament_id = :tournamentId\n" +
            "\n" +
            "            GROUP BY TTM.tournament_id, TTM.team_id, TTM.match_id, TTM.score, TTM.bounceback_points, TTM.overtime_tossups_gotten\n" +
            "\n" +
            "        ) as player_team_join\n" +
            "\n" +
            "        WHERE player_team_join.tournament_id = :tournamentId\n" +
            "\n" +
            "        GROUP BY player_team_join.match_id\n" +
            "\n" +
            "    ) as match_teams\n" +
            "\n" +
            "    INNER JOIN\n" +
            "\n" +
            "    tournament_match M\n" +
            "\n" +
            "    ON M.id = match_teams.match_id\n" +
            "\n" +
            "    WHERE M.tournament_id = :tournamentId\n" +
            "\n" +
            ") as match_information\n" +
            "\n" +
            "LEFT JOIN\n" +
            "\n" +
            "(\n" +
            "    SELECT\n" +
            "    MP.match_id,\n" +
            "    array_agg(\n" +
            "        json_build_object(\n" +
            "            'id', P.id,\n" +
            "            'name', P.name\n" +
            "        )\n" +
            "    ) as phases\n" +
            "\n" +
            "    FROM\n" +
            "\n" +
            "    match_is_part_of_phase MP\n" +
            "\n" +
            "    INNER JOIN\n" +
            "\n" +
            "    tournament_phase P\n" +
            "\n" +
            "    ON MP.tournament_id = P.tournament_id AND MP.phase_id = P.id\n" +
            "\n" +
            "    WHERE P.tournament_id = :tournamentId\n" +
            "\n" +
            "    GROUP BY MP.match_id\n" +
            "\n" +
            ") as match_phases\n" +
            "\n" +
            "ON match_information.id = match_phases.match_id";

    protected TournamentMatchDAO() {
        super(TournamentMatch.class);
    }

    public List<Match> findMatchesByTournamentIdWithRawQuery(String tournamentId) {
        List<Match> matches = getEntityManager().createNativeQuery(QUERY)
                .unwrap(NativeQuery.class)
                .setParameter("tournamentId", tournamentId)
                .addScalar("phases", StringArrayType.INSTANCE)
                .addScalar("id", StandardBasicTypes.STRING)
                .addScalar("tournamentId", StandardBasicTypes.STRING)
                .addScalar("round", StandardBasicTypes.INTEGER)
                .addScalar("tossupsHeard", StandardBasicTypes.INTEGER)
                .addScalar("teams", StringArrayType.INSTANCE)
                .addScalar("moderator", StandardBasicTypes.STRING)
                .addScalar("packet", StandardBasicTypes.STRING)
                .addScalar("notes", StandardBasicTypes.STRING)
                .addScalar("serialId", StandardBasicTypes.STRING)
                .setResultTransformer(new MatchTransformer())
                .getResultList();
        return matches;
    }
}
