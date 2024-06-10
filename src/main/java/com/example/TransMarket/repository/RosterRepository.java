package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Player;
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
public class RosterRepository {

    private final JdbcTemplate template;

    public RosterRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<String> showAllPlayerName(String clubName) {
        String sql = "SELECT p.Name " +
                "FROM Roster r " +
                "JOIN Player p ON r.PlayerId = p.Id " +
                "WHERE r.ClubId = (SELECT Id FROM Club WHERE Name = ?)";
        return template.query(sql, playerNameRowMapper(), clubName);
    }

    private RowMapper<String> playerNameRowMapper() {
        return (rs, rowNum) -> {
            return (rs.getString("Name"));
        };
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
