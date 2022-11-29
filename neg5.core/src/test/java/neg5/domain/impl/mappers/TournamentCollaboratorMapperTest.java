package neg5.domain.impl.mappers;

import neg5.domain.impl.entities.compositeIds.TournamentCollaboratorId;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import neg5.domain.api.TournamentCollaboratorDTO;
import neg5.domain.impl.entities.Account;
import neg5.domain.impl.entities.Tournament;
import neg5.domain.impl.entities.TournamentCollaborator;

@ExtendWith(MockitoExtension.class)
public class TournamentCollaboratorMapperTest {

    @InjectMocks private TournamentCollaboratorMapper collaboratorMapper;

    @Test
    public void testMapToDto() {
        TournamentCollaborator collaborator = buildEntity();

        TournamentCollaboratorDTO dto = collaboratorMapper.toDTO(collaborator);

        Assert.assertNotNull(dto.getUserId());
        Assert.assertEquals(collaborator.getId().getUser().getId(), dto.getUserId());

        Assert.assertNotNull(dto.getTournamentId());
        Assert.assertEquals(collaborator.getTournament().getId(), dto.getTournamentId());

        Assert.assertNotNull(dto.getIsAdmin());
        Assert.assertEquals(collaborator.getIsAdmin(), dto.getIsAdmin());
    }

    @Test
    public void testMergeToEntity_full() {
        TournamentCollaboratorDTO dto = buildDto();

        TournamentCollaborator entity = collaboratorMapper.mergeToEntity(dto);

        Assert.assertNotNull(entity.getIsAdmin());
        Assert.assertEquals(dto.getIsAdmin(), entity.getIsAdmin());

        Assert.assertNotNull(entity.getTournament());
        Assert.assertEquals(dto.getTournamentId(), entity.getTournament().getId());

        Assert.assertNotNull(entity.getId());
        Assert.assertNotNull(entity.getId().getTournament());
        Assert.assertEquals(dto.getTournamentId(), entity.getId().getTournament().getId());

        Assert.assertNotNull(entity.getId().getUser());
        Assert.assertEquals(dto.getUserId(), entity.getId().getUser().getId());
    }

    @Test
    public void testMergeToEntity_noIds() {
        TournamentCollaboratorDTO dto = buildDto();
        dto.setUserId(null);
        dto.setTournamentId(null);

        TournamentCollaborator entity = collaboratorMapper.mergeToEntity(dto);

        Assert.assertNull(entity.getId().getUser());
        Assert.assertNull(entity.getId().getTournament());
    }

    private TournamentCollaborator buildEntity() {
        TournamentCollaborator collaborator = new TournamentCollaborator();

        TournamentCollaboratorId id = new TournamentCollaboratorId();
        id.setTournament(new Tournament());
        id.getTournament().setId("TEST_ID");
        id.setUser(new Account());
        id.getUser().setId("TEST");

        collaborator.setId(id);
        collaborator.setIsAdmin(false);

        return collaborator;
    }

    private TournamentCollaboratorDTO buildDto() {
        TournamentCollaboratorDTO dto = new TournamentCollaboratorDTO();
        dto.setIsAdmin(false);
        dto.setTournamentId("Tournament_ID");
        dto.setUserId("USER_ID");
        return dto;
    }
}
