package org.neg5.mappers;

import neg5.domain.impl.mappers.TournamentTossupValueMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.neg5.TournamentTossupValueDTO;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentTossupValue;
import org.neg5.data.embeddables.TournamentTossupValueId;
import org.neg5.enums.TossupAnswerType;

@ExtendWith(MockitoExtension.class)
public class TournamentTossupValueMapperTest {

    @InjectMocks private TournamentTossupValueMapper tournamentTossupValueMapper;

    private static final String TOURNAMENT_ID = "12345";

    @Test
    public void testMappingFromEntityToDTO() {
        TournamentTossupValue entity = buildEntity();

        TournamentTossupValueDTO dto = tournamentTossupValueMapper.toDTO(entity);

        Assert.assertEquals(entity.getAnswerType(), dto.getAnswerType());
        Assert.assertEquals(entity.getTournament().getId(), dto.getTournamentId());

        Assert.assertNotNull(dto.getValue());
        Assert.assertEquals(entity.getId().getValue(), dto.getValue());
    }

    @Test
    public void testMappingFromEntityToDTOIfNullTournament() {
        TournamentTossupValue entity = buildEntity();
        entity.getId().setTournament(null);

        TournamentTossupValueDTO dto = tournamentTossupValueMapper.toDTO(entity);
        Assert.assertNull(dto.getTournamentId());
    }

    @Test
    public void testMappingFromDTOToEntity() {
        TournamentTossupValueDTO dto = buildDTO();

        TournamentTossupValue entity = tournamentTossupValueMapper.mergeToEntity(dto);

        Assert.assertNotNull(entity.getId());
        Assert.assertEquals(dto.getValue(), entity.getId().getValue());
        Assert.assertEquals(dto.getTournamentId(), entity.getId().getTournament().getId());

        Assert.assertEquals(dto.getAnswerType(), entity.getAnswerType());
    }

    @Test
    public void testMappingFromDTOToEntityNullTournament() {
        TournamentTossupValueDTO dto = buildDTO();
        dto.setTournamentId(null);

        TournamentTossupValue entity = tournamentTossupValueMapper.mergeToEntity(dto);
        Assert.assertNull(entity.getId().getTournament());
    }

    private TournamentTossupValue buildEntity() {
        TournamentTossupValue entity = new TournamentTossupValue();
        entity.setAnswerType(TossupAnswerType.POWER);

        entity.setId(new TournamentTossupValueId());
        entity.getId().setValue(10);
        entity.getId().setTournament(new Tournament());
        entity.getId().getTournament().setId(TOURNAMENT_ID);

        return entity;
    }

    private TournamentTossupValueDTO buildDTO() {
        TournamentTossupValueDTO dto = new TournamentTossupValueDTO();
        dto.setTournamentId(TOURNAMENT_ID);
        dto.setAnswerType(TossupAnswerType.POWER);
        dto.setValue(10);

        return dto;
    }
}
