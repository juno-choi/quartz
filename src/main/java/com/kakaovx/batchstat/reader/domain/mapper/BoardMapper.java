package com.kakaovx.batchstat.reader.domain.mapper;

import com.kakaovx.batchstat.reader.domain.vo.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardVo> selectBoard();
    int insertBoard(BoardVo boardVo);
}
