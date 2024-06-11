package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Position;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class PositionRepository {

    private final JdbcTemplate template;

    public PositionRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public List<Position> displayAllPosition() {
        String sql = "select * from `Position`";
        return template.query(sql, positionRowMapper());
    }

    private RowMapper<Position> positionRowMapper() {
        return (rs, rowNum) -> {
            Position p = new Position();
            p.setPositionId(rs.getString("Id"));
            p.setPositionName(rs.getString("Name"));
            return p;
        };
    }

    public String findPositionIdByName(String positionName) {
        String sql = "select Id from `Position` p where p.Name = ?";
        try {
            return template.queryForObject(sql, new Object[]{positionName}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
