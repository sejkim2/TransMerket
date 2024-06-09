package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Club;
import com.example.TransMarket.domain.Player;
import com.example.TransMarket.dto.playerDTO;
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
public class ClubRepository {

    private final JdbcTemplate template;

    public ClubRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void clear() throws SQLException {
        String sql = "delete from Club";
        template.update(sql);
    }

    public void delete(String clubId) throws SQLException {
        String sql = "delete from Club where Id=?";
        template.update(sql, clubId);
    }

//    public void update(String memberId, int money) throws SQLException {
//        String sql = "update member set money=? where member_id=?";
//        template.update(sql, money, memberId);
//    }


    public Club findById(String clubId) throws SQLException {
        String sql = "select * from Club where Id = ?";
        return template.queryForObject(sql, clubRowMapper(), clubId);
    }

    public List<Club> findAll() {
        String sql = "select * from Club";
        return template.query(sql, clubRowMapper());
    }

    private RowMapper<Club> clubRowMapper() {
        return (rs, rowNum) -> {
            Club club = new Club();
            club.setClubId(rs.getString("Id"));
            club.setClubName(rs.getString("Name"));
            club.setLeagueId(rs.getString("LeagueId"));
            club.setWins(rs.getInt("Wins"));
            return club;
        };
    }

    public Club save(Club club) throws SQLException {
        String sql = "insert into Club(Id, Name, LeagueId, Wins) values(?, ?, ?, ?)";
        template.update(sql, club.getClubId(),
            club.getClubName(),
            club.getLeagueId(),
            club.getWins());
        return club;
    }

//    public String findClubNameByclubId(String clubId) {
//        String sql = "select Club.Name from Club where Id = ?";
//        return template.queryForObject(sql, new Object[]{clubId}, String.class);
//    }

    public String findClubNameByclubId(String clubId) {
        try {
            String sql = "select Club.Name from Club where Id = ?";
            return template.queryForObject(sql, new Object[]{clubId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // 결과가 없는 경우 null 반환
            return null;
        }
    }

    public String findClubIdByClubName(String clubName) {
        try {
            String sql = "select Club.Id from Club where Club.Name = ?";
            return template.queryForObject(sql, new Object[]{clubName}, String.class);
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
