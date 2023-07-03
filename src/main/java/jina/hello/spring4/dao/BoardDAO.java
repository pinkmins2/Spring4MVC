package jina.hello.spring4.dao;

import jina.hello.spring4.model.Board;

import java.util.List;

public interface BoardDAO {
    List<Board> selectBoard(int snum);
    Board selectOneBoard(String bno);
}