package com.gunes.model.vo.mapper;


import com.gunes.model.entity.Board;
import com.gunes.model.vo.BoardVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper extends BaseMapper<BoardVO, Board> {

    Board mapToEntity(BoardVO boardVO);

    BoardVO mapToVO(Board board);
}
