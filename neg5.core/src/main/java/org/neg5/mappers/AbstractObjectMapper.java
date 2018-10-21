package org.neg5.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

public class AbstractObjectMapper<Entity, DTO> {

    private final Class<Entity> entityClass;
    private final Class<DTO> dtoClass;

    private final ModelMapper modelMapper;
    private final TypeMap<Entity, DTO> typeMap;

    protected AbstractObjectMapper(Class<Entity> entityClass, Class<DTO> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        typeMap = modelMapper.createTypeMap(entityClass, dtoClass);

        addMappings();
    }

    public DTO toDTO(Entity entity) {
        DTO dto = modelMapper.map(entity, dtoClass);
        enrichDTO(dto, entity);
        return dto;
    }

    protected void enrichDTO(DTO dto, Entity entity) {}

    protected void addMappings() {

    }

    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected TypeMap<Entity, DTO> getTypeMap() {
        return typeMap;
    }
}
