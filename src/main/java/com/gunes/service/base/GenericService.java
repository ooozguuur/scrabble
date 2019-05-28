package com.gunes.service.base;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable> {

    Class<T> getPersistentClass();

    T createEntityObject();

}
