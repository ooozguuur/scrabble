package com.gunes.model.vo.mapper;


import com.gunes.model.entity.Word;
import com.gunes.model.vo.WordVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordMapper extends BaseMapper<WordVO, Word> {

    Word mapToEntity(WordVO wordVO);

    WordVO mapToVO(Word word);
}
