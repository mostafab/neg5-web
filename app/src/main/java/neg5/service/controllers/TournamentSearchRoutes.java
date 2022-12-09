package neg5.service.controllers;

import com.google.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import neg5.domain.api.FieldValidationErrors;
import neg5.domain.api.TournamentSearchApi;
import neg5.validation.ObjectValidationException;

public class TournamentSearchRoutes extends AbstractJsonRoutes {

    private final TournamentSearchApi tournamentSearchApi;

    @Inject
    public TournamentSearchRoutes(TournamentSearchApi tournamentSearchApi) {
        this.tournamentSearchApi = tournamentSearchApi;
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/search/tournaments";
    }

    @Override
    public void registerRoutes() {
        get(
                "/name",
                (req, res) ->
                        tournamentSearchApi.findByMatchingPrefix(req.queryParams("name"), false));
        get(
                "/days",
                (req, res) -> {
                    try {
                        int days = Integer.parseInt(req.queryParams("days"));
                        return tournamentSearchApi.findTournamentsWithDateSinceDays(days, false);
                    } catch (NumberFormatException exception) {
                        throw new ObjectValidationException(
                                new FieldValidationErrors().add("days", "days must be an integer"));
                    }
                });
        get(
                "/dates",
                (req, res) -> {
                    LocalDate start = parseDate(req.queryParams("start"), "start");
                    if (start == null) {
                        throw new ObjectValidationException(
                                new FieldValidationErrors()
                                        .add("start", "start date must be specified."));
                    }
                    LocalDate end = parseDate(req.queryParams("end"), "end");
                    if (end != null && start.isAfter(end)) {
                        throw new ObjectValidationException(
                                new FieldValidationErrors()
                                        .add("start", "start date must be before end date."));
                    }
                    return tournamentSearchApi.findTournamentsWithDateInRange(start, end, false);
                });
    }

    private LocalDate parseDate(String input, String field) {
        if (input == null) {
            return null;
        }
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException ex) {
            getLogger().error("Encountered error parsing LocalDate from input", ex);
            throw new ObjectValidationException(
                    new FieldValidationErrors()
                            .add(field, "dates should be formatted as YYYY-MM-DD"));
        }
    }
}
