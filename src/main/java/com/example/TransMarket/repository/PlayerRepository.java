package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Player;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class PlayerRepository {

    private final JdbcTemplate template;

    public PlayerRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void clear() throws SQLException {
        String sql = "delete from Player";
        template.update(sql);
    }

    public void delete(String playerId) throws SQLException {
        String sql = "delete from Player where Id=?";
        template.update(sql, playerId);
    }

//    public void update(String memberId, int money) throws SQLException {
//        String sql = "update member set money=? where member_id=?";
//        template.update(sql, money, memberId);
//    }


    public Player findById(String playerId) throws SQLException {
        String sql = "select * from Player where Id = ?";
        return template.queryForObject(sql, playerRowMapper(), playerId);
    }

    public List<Player> findAll() {
        String sql = "select * from Player";
        return template.query(sql, playerRowMapper());
    }

    private RowMapper<Player> playerRowMapper() {
        return (rs, rowNum) -> {
            Player player = new Player();
            player.setPlayerId(rs.getString("Id"));
            player.setPlayerName(rs.getString("Name"));
            player.setAge(rs.getInt("Age"));
            player.setNationality(rs.getString("Nationality"));
            player.setClubId(rs.getString("ClubId"));
            String importanceStr = rs.getString("Importance");
            Character importance = (importanceStr != null && !importanceStr.isEmpty()) ? importanceStr.charAt(0) : null;
            player.setImportance(importance);
            return player;
        };
    }

    public Player save(Player player) throws SQLException {
        String sql = "insert into Player(Id, Name, Age, Nationality, ClubId) values(?, ?, ?, ?, ?)";
        try {
            template.update(sql, player.getPlayerId(),
                    player.getPlayerName(),
                    player.getAge(),
                    player.getNationality(),
                    player.getClubId());
        } catch (DataAccessException e) {
            SQLExceptionTranslator translator = new SQLStateSQLExceptionTranslator();
            Throwable rootCause = translator.translate("Player Save", sql, (SQLException) e.getCause());
            return null;
        }
        return player;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
