package org.neg5.data;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDataObject<T, PrimaryKeyType> {

    public abstract PrimaryKeyType getId();

    public T copyOf() {
        try {
            T that = (T) this.getClass().newInstance();
            BeanUtilsBean.getInstance().getPropertyUtils().copyProperties(that, this);
            return that;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException("Unable to make a copy of " + this.getClass().toString() + ".", e);
        }
    }
}
