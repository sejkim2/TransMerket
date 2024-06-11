package com.example.TransMarket.repository;

import com.example.TransMarket.dto.trophyDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Repository
public class AvailablePositionRepository {
    private final JdbcTemplate template;

    public AvailablePositionRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<String> displayAvailablePostionByPlayerId(String playerId) {

        String sql = "SELECT p.Name as position\n" +
                "FROM `Position` p\n" +
                "INNER JOIN Available_Position ap ON p.Id = ap.PositionId\n" +
                "WHERE ap.PlayerId = ?";

        /* return position as string */

        return template.query(sql, positionRowMapper(), playerId);
    }

    private RowMapper<String> positionRowMapper() {
        return (rs, rowNum) -> {
            return (rs.getString("position"));
        };
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
