package com.gunes.model.vo.mapper;

import com.gunes.model.entity.base.IdBaseEntity;
import com.gunes.model.vo.IdBaseVO;

public interface BaseMapper<V extends IdBaseVO, E extends IdBaseEntity> {

    V mapToVO(E e);

    E mapToEntity(V v);
}