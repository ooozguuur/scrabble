package com.gunes.model.vo.mapper;


import com.gunes.model.entity.Cell;
import com.gunes.model.vo.CellVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CellMapper extends BaseMapper<CellVO, Cell> {

    Cell mapToEntity(CellVO cellVO);

    CellVO mapToVO(Cell cell);
}
