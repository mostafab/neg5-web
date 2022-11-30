package neg5.domain.impl.entities;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;

public abstract class AbstractDataObject<T> {

    public T copyOf() {
        try {
            T that = (T) this.getClass().newInstance();
            BeanUtilsBean.getInstance().getPropertyUtils().copyProperties(that, this);
            return that;
        } catch (IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | InstantiationException e) {
            throw new RuntimeException(
                    "Unable to make a copy of " + this.getClass().toString() + ".", e);
        }
    }
}
