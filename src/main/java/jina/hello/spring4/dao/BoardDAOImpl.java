package jina.hello.spring4.dao;

import jina.hello.spring4.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("bdao")     /*관리대상*/
public class BoardDAOImpl implements BoardDAO{
    @Value("#{sql['selectBoard']}") private String selectSQL;
    @Value("#{sql['selectOneBoard']}") private String selectOneSQL;
    @Value("#{sql['viewCountBoard']}") private String viewCountSQL;
    @Value("#{sql['insertBoard']}") private String insertSQL;

    @Autowired JdbcTemplate jdbcTemplate;
    @Override
    public List<Board> selectBoard(int snum) {
        Object[] params=new Object[] {snum};
        RowMapper<Board> mapper = new SelectMapper();

        return jdbcTemplate.query(selectSQL, params, mapper);
    }

    @Override
    public Board selectOneBoard(String bno) {
        Object[] params = new Object[] {bno};
        RowMapper<Board> mapper = new SelectOneMapper();

        // 조회수 증가
        jdbcTemplate.update(viewCountSQL,params);
        return jdbcTemplate.queryForObject(selectOneSQL, params, mapper);
    }

    @Override
    public int insertBoard(Board bd) {
        Object[] params = new Object[] {
                bd.getTitle(),bd.getUserid(),
                bd.getContents()
        };

        return jdbcTemplate.update(insertSQL, params);
    }

    private class SelectMapper implements RowMapper<Board> {
        @Override
        public Board mapRow(ResultSet rs, int num) throws SQLException {
            Board bd = new Board(rs.getString(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),
                    rs.getString(5),null);
            /*불러온 board의 sql문 순서대로*/
            return bd;
        }
    }   // selectMapper

    private class SelectOneMapper implements RowMapper<Board> {
        @Override
        public Board mapRow(ResultSet rs, int num) throws SQLException {
            Board bd = new Board(rs.getString(1),rs.getString(2),
                                    rs.getString(3),rs.getString(4),
                                    rs.getString(5), rs.getString(6));
            /*불러온 board의 sql문 순서대로*/
            return bd;
        }
    }   // selectOneMapper
}   // class