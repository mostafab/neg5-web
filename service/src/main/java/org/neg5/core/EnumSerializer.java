package org.neg5.core;

import neg5.domain.api.enums.Identifiable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumSerializer {

    private Set<Class<? extends Enum>> enumClazzes = new HashSet<>();

    private static final String ID_TO_OBJ_KEY = "idToObj";
    private static final String PREFIX = "g_";

    private static final String ENUMS_VALUE_NAME = "$VALUES";

    public <T extends Enum<T> & Identifiable> EnumSerializer add(Class<T> enumClazz) {
        this.enumClazzes.add(enumClazz);
        return this;
    }

    @SuppressWarnings("unchecked")
    public Map<String, ?> serialize() {
        return enumClazzes.stream()
                .collect(Collectors.toMap(this::buildKey, this::buildValue));
    }

    private <T extends Enum<T>> String buildKey(Class<T> enumClazz) {
        String simpleName = enumClazz.getSimpleName();
        return PREFIX.concat(simpleName.substring(0, 1).toLowerCase().concat(simpleName.substring(1)));
    }

    private <T extends Enum<T>> Map<String, ?> buildValue(Class<T> enumClazz) {
        try {
            Map<String, Object> enumMap = new HashMap<>();
            Map<String, Object> idToEnumMap = new HashMap<>();
            for (T enumValue : enumClazz.getEnumConstants()) {
                if (enumValue instanceof Identifiable) {
                    Identifiable identifiable = (Identifiable) enumValue;
                    enumMap.put(enumValue.name(), identifiable.getId());
                    Map<String, Object> fields = new HashMap<>();
                    for (Field field : enumValue.getClass().getDeclaredFields()) {
                        if (!field.isEnumConstant() && !ENUMS_VALUE_NAME.equals(field.getName())) {
                            field.setAccessible(true);
                            fields.put(field.getName(), field.get(enumValue));
                        }
                    }
                    idToEnumMap.put(identifiable.getId().toString(), fields);
                }
            }
            enumMap.put(ID_TO_OBJ_KEY, idToEnumMap);
            return enumMap;
        } catch (Exception e) {
            return null;
        }
    }
}
