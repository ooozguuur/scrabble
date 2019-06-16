package com.gunes.model.vo.mapper;


import com.gunes.model.entity.Move;
import com.gunes.model.vo.MoveVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoveMapper extends BaseMapper<MoveVO, Move> {

    Move mapToEntity(MoveVO moveVO);

    MoveVO mapToVO(Move move);
}
