package neg5.domain.impl.mappers;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import neg5.domain.impl.entities.CompositeId;
import neg5.domain.impl.entities.CompositeIdObject;
import neg5.domain.impl.entities.IdDataObject;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AbstractObjectMapper<Entity, DTO> {

    private final Class<Entity> entityClass;
    private final Class<DTO> dtoClass;

    private final ModelMapper entityToDTOModelMapper;
    private final TypeMap<Entity, DTO> entityToDTOTypeMap;

    private final ModelMapper dtoToEntityModelMapper;
    private final TypeMap<DTO, Entity> dtoToEntityTypeMap;

    protected AbstractObjectMapper(Class<Entity> entityClass, Class<DTO> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;

        entityToDTOModelMapper = new ModelMapper();
        entityToDTOModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        entityToDTOTypeMap = entityToDTOModelMapper.createTypeMap(entityClass, dtoClass);

        dtoToEntityModelMapper = new ModelMapper();
        dtoToEntityModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dtoToEntityTypeMap = dtoToEntityModelMapper.createTypeMap(dtoClass, entityClass);

        addMappings();
    }

    public DTO toDTO(Entity entity) {
        DTO dto = entityToDTOModelMapper.map(entity, dtoClass);
        copyEntitiesToIds(entity, dto);
        enrichDTO(dto, entity);
        return dto;
    }

    public Entity mergeToEntity(DTO dto) {
        try {
            return mergeToEntity(dto, entityClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Unable to map dto to entity.");
        }
    }

    public Entity mergeToEntity(DTO dto, Entity entity) {
        dtoToEntityModelMapper.map(dto, entity);
        copyIdsToEntities(dto, entity);
        return entity;
    }

    protected void enrichDTO(DTO dto, Entity entity) {}

    protected void addMappings() {

    }

    protected Set<String> getIgnoredEntityPropertyNames() {
        return new HashSet<>();
    }

    protected ModelMapper getEntityToDTOModelMapper() {
        return entityToDTOModelMapper;
    }

    protected TypeMap<Entity, DTO> getEntityToDTOTypeMap() {
        return entityToDTOTypeMap;
    }

    protected TypeMap<DTO, Entity> getDtoToEntityTypeMap() {
        return dtoToEntityTypeMap;
    }

    private void copyEntitiesToIds(Entity source, DTO target) {
        List<PropertyDescriptor> idEntityProps = getIdEntityProperties(source);
        idEntityProps.stream()
                // Copy over the property if it's an id data object, and the target has a corresponding id
                .filter(property -> PropertyUtils.isWriteable(target, getIdPropertyName(property)))
                .forEach(property -> {
                    try {
                        IdDataObject dataObject
                                = (IdDataObject) PropertyUtils.getSimpleProperty(source, property.getName());
                        if (dataObject != null) {
                            BeanUtils.copyProperty(target, getIdPropertyName(property), dataObject.getId());
                        }
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        // Not worth logging an exception here, just move on
                    }
                });

        if (source instanceof CompositeIdObject) {
            CompositeId compositeId = (CompositeId) ((CompositeIdObject) source).getId();
            if (compositeId != null) {
                getCompositeIdDataObjectProperties(compositeId).stream()
                        .filter(property -> PropertyUtils.isWriteable(target, getIdPropertyName(property)))
                        .forEach(property -> {
                            try {
                                IdDataObject dataObject
                                        = (IdDataObject) PropertyUtils.getSimpleProperty(compositeId, property.getName());
                                if (dataObject != null) {
                                    BeanUtils.copyProperty(target, getIdPropertyName(property), dataObject.getId());
                                }
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                // Not worth logging an exception here, just move on
                            }
                        });
            }
        }
    }

    private void copyIdsToEntities(DTO source, Entity target) {
        getIdEntityProperties(target).stream()
                .filter(propertyDescriptor -> PropertyUtils.isReadable(source, getIdPropertyName(propertyDescriptor)))
                .forEach(property -> {
                    try {
                        Object id = PropertyUtils
                                .getSimpleProperty(source, getIdPropertyName(property));
                        IdDataObject idDataObject = null;
                        Constructor<?> constructor = property.getPropertyType().getDeclaredConstructor();
                        if (id != null && constructor != null && id instanceof Serializable) {
                            //This allows us to use non-public default constructors, can be dangerous, but is needed
                            constructor.setAccessible(true);
                            idDataObject = (IdDataObject) constructor.newInstance();
                            idDataObject.setId((Serializable) id);
                        }
                        //if the id is null, assume we are trying to remove it
                        BeanUtils.copyProperty(target, property.getName(), idDataObject);
                    } catch (IllegalAccessException
                            | InvocationTargetException
                            | NoSuchMethodException
                            | InstantiationException e) {
                        // Don't bother logging anything, move on
                    }
                });

        if (target instanceof CompositeIdObject) {
            List<PropertyDescriptor> compositeIdProperties = getCompositeIdPropertiesOfEntity((CompositeIdObject) target);
            for (PropertyDescriptor property : compositeIdProperties) {
                try {
                    Constructor<?> constructor = property.getPropertyType().getDeclaredConstructor();
                    constructor.setAccessible(true);
                    CompositeId idInstance = (CompositeId) constructor.newInstance();
                    copyDtoIdsToCompositeIdEntities(idInstance, source);
                    BeanUtils.copyProperty(target, property.getName(), idInstance);
                } catch (NoSuchMethodException
                        | IllegalAccessException
                        | InvocationTargetException
                        | InstantiationException e) {
                    // Don't log anything, move forward
                }
            }
        }
    }

    private void copyDtoIdsToCompositeIdEntities(CompositeId idInstance, DTO source) {
        List<PropertyDescriptor> idDataObjProperties = getCompositeIdDataObjectProperties(idInstance);
        for (PropertyDescriptor idDataObjProp : idDataObjProperties) {
            try {
                Object id = PropertyUtils
                        .getSimpleProperty(source, getIdPropertyName(idDataObjProp));
                if (id != null) {
                    Constructor<?> propConstructor = idDataObjProp.getPropertyType().getDeclaredConstructor();
                    propConstructor.setAccessible(true);
                    IdDataObject dataObject = (IdDataObject) propConstructor.newInstance();
                    dataObject.setId((Serializable) id);
                    BeanUtils.copyProperty(idInstance, idDataObjProp.getName(), dataObject);
                }
            } catch (NoSuchMethodException
                    | IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException e) {

            }
        }
    }

    private String getIdPropertyName(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor.getName() + "Id";
    }

    private List<PropertyDescriptor> getIdEntityProperties(@Nonnull Entity entity) {
        // Returns all property descriptors on the specified entity that implement IdDataObject
        Set<String> ignoredPropNames = getIgnoredEntityPropertyNames();
        return Lists.newArrayList(PropertyUtils.getPropertyDescriptors(entity)).stream()
                .filter(propertyDescriptor ->
                        IdDataObject.class.isAssignableFrom(propertyDescriptor.getPropertyType())
                )
                .filter(propertyDescriptor -> !ignoredPropNames.contains(propertyDescriptor.getName()))
                .collect(Collectors.toList());
    }

    private List<PropertyDescriptor> getCompositeIdDataObjectProperties(@Nonnull CompositeId compositeIdObject) {
        return Lists.newArrayList(PropertyUtils.getPropertyDescriptors(compositeIdObject)).stream()
                .filter(propertyDescriptor -> IdDataObject.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
                .collect(Collectors.toList());
    }

    private List<PropertyDescriptor> getCompositeIdPropertiesOfEntity(@Nonnull CompositeIdObject compositeIdObject) {
        return Lists.newArrayList(PropertyUtils.getPropertyDescriptors(compositeIdObject)).stream()
                .filter(propertyDescriptor -> CompositeId.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
                .collect(Collectors.toList());
    }
}
