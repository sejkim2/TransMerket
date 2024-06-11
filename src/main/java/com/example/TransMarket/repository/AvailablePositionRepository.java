package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Position;
import com.example.TransMarket.dto.trophyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
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

    public Position save(String playerId, String positionId) throws SQLException {

        String sql = "INSERT INTO TransferMarket.Available_Position\n" +
                "(PlayerId, PositionId, Proficiency, Preference)\n" +
                "VALUES(?, ?, 'A', 'A');";
        try {
            template.update(sql, playerId, positionId);
        } catch (DataAccessException e) {
//            SQLExceptionTranslator translator = new SQLStateSQLExceptionTranslator();
//            Throwable rootCause = translator.translate("Player Save", sql, (SQLException) e.getCause());
            return null;
        }
        return new Position();
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
