package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Club;
import com.example.TransMarket.domain.League;
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
public class LeagueRepository {

    private final JdbcTemplate template;

    public LeagueRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void clear() throws SQLException {
        String sql = "delete from League";
        template.update(sql);
    }

    public void delete(String clubId) throws SQLException {
        String sql = "delete from League where Id=?";
        template.update(sql, clubId);
    }

    public League findById(String leagueId) throws SQLException {
        String sql = "select * from League where Id = ?";
        return template.queryForObject(sql, leagueRowMapper(), leagueId);
    }

    public List<League> findAll() {
        String sql = "select * from League";
        return template.query(sql, leagueRowMapper());
    }

    private RowMapper<League> leagueRowMapper() {
        return (rs, rowNum) -> {
            League league = new League();
            league.setLeagueId(rs.getString("Id"));
            league.setLeagueName(rs.getString("Name"));
            return league;
        };
    }

    public League save(League league) throws SQLException {
        String sql = "insert into League(Id, Name) values(?, ?)";
        template.update(sql, league.getLeagueId(),
                league.getLeagueName());
        return league;
    }

    public String findNameById(String leagueId) {
        String sql = "select League.Name from League where League.Id = ?";
        return template.queryForObject(sql, new Object[]{leagueId}, String.class);
    }

//    public String findClubNameByclubId(String clubId) {
//        String sql = "select Club.Name from Club where Id = ?";
//        return template.queryForObject(sql, new Object[]{clubId}, String.class);
//    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
