package org.neg5.managers;

import com.google.inject.persist.Transactional;
import org.neg5.daos.AbstractDAO;

import org.neg5.data.AbstractDataObject;
import org.neg5.data.CompositeIdObject;
import org.neg5.data.IdDataObject;
import org.neg5.mappers.AbstractObjectMapper;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDTOManager<T extends AbstractDataObject<T>
        & IdDataObject<IdType>, DTO, IdType extends Serializable> {

    protected abstract AbstractDAO<T, IdType> getRwDAO();

    protected abstract AbstractObjectMapper<T, DTO> getMapper();

    protected abstract IdType getIdFromDTO(DTO dto);

    @Transactional
    public DTO get(IdType id) {
        T entity = getRwDAO().get(id);
        if (entity == null) {
            String clazzName = getRwDAO().getPersistentClass().getSimpleName();
            throw new NoResultException("No result found for " + clazzName + " with id " + id);
        }
        return getMapper().toDTO(entity.copyOf());
    }

    @Transactional
    public DTO create(DTO dto) {
        T entity = getMapper().mergeToEntity(dto);

        /* Assumption is that if the entity doesn't have a composite primary key,
        the id will be auto-generated by Hibernate.
        */
        if (!(entity instanceof CompositeIdObject)) {
            entity.setId(null);
        }
        T createdEntity = getRwDAO().save(entity);
        getRwDAO().flush();
        return getMapper().toDTO(getRwDAO().get(createdEntity.getId()));
    }

    @Transactional
    public DTO update(DTO dto) {
        T originalEntity = getRwDAO().get(getIdFromDTO(dto));
        T updated = getMapper().mergeToEntity(dto, originalEntity);
        return getMapper().toDTO(getRwDAO().save(updated).copyOf());
    }

    @Transactional
    public List<DTO> findAllByTournamentId(String tournamentId) {
        return getRwDAO().findAllByTournamentId(tournamentId)
                .stream()
                .map(entity -> getMapper().toDTO(entity))
                .collect(Collectors.toList());
    }
}
