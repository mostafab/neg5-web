package org.neg5.validation;

import org.neg5.FieldValidationError;
import org.neg5.TournamentMatchDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface EnhancedMatchValidator {

    List<FieldValidationError> getErrors(@Nonnull List<TournamentMatchDTO> allMatches, @Nonnull TournamentMatchDTO subject);
}
