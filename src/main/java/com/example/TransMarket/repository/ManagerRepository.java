package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Manager;
import com.example.TransMarket.domain.Player;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ManagerRepository {
    private final JdbcTemplate template;

    public ManagerRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void clear() throws SQLException {
        String sql = "delete from Manager";
        template.update(sql);
    }

    public void delete(String managerId) throws SQLException {
        String sql = "delete from Manager where Id=?";
        template.update(sql, managerId);
    }

    public String findManagerByClubId(String clubId) {
        String sql = "select Manager.Name from Manager where Manager.ClubId = ?";

    }
//    public Manager findById(String managerId) throws SQLException {
//        String sql = "select * from Manager where Id = ?";
//        return template.queryForObject(sql, managerRowMapper(), managerId);
//    }
//
//    private RowMapper<Manager> managerRowMapper() {
//        return null;
//    }


    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
