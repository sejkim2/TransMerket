package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Club;
import com.example.TransMarket.domain.Player;
import com.example.TransMarket.domain.Trophy;
import com.example.TransMarket.dto.trophyDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class TrophyRepository {
    private final JdbcTemplate template;

    public TrophyRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<trophyDTO> findTrophyByPlayerId(String playerId) {

        /* return {"trophyName", "Season"}*/

        String sql = "SELECT \n" +
                "    t.Name AS trophyName,\n" +
                "    c.Season\n" +
                "FROM \n" +
                "    TransferMarket.Career c\n" +
                "JOIN \n" +
                "    TransferMarket.Trophy t ON c.TrophyId = t.Id\n" +
                "WHERE \n" +
                "    c.PlayerId = ?\n" +
                "ORDER BY \n" +
                "    c.Season";
        return template.query(sql, trophyRowMapper(), playerId);
    }

    private RowMapper<trophyDTO> trophyRowMapper() {
        return (rs, rowNum) -> {
            trophyDTO t = new trophyDTO();
            t.setTrophyName(rs.getString("trophyName"));
            t.setSeason(rs.getString("Season"));
            return t;
        };
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
