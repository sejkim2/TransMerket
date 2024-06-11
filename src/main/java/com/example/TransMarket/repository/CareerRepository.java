package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Career;
import com.example.TransMarket.domain.Player;
import com.example.TransMarket.dto.trophyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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

@Repository
@Slf4j
public class CareerRepository {
    private final JdbcTemplate template;

    public CareerRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public Career save(Career career) throws SQLException {
        String sql = "INSERT INTO TransferMarket.Career\n" +
                "(PlayerId, TrophyId, Season)\n" +
                "VALUES(?, ?, ?)";
        try {
            template.update(sql, career.getPlayerId(), career.getTrophyId(), career.getSeason());
        } catch (DataAccessException e) {
            SQLExceptionTranslator translator = new SQLStateSQLExceptionTranslator();
            Throwable rootCause = translator.translate("Career Save", sql, (SQLException) e.getCause());
            return null;
        }
        return career;
    }

    public int GetPlayerTrophyCount(String playerName) {
        String sql = "SELECT GetPlayerTrophyCount(?)";
        return template.queryForObject(sql, Integer.class, playerName);
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
