package com.example.TransMarket.repository;

import com.example.TransMarket.connection.ConnectionConst;
import com.example.TransMarket.domain.Member;
import com.example.TransMarket.domain.Player;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.example.TransMarket.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryTest {

    PlayerRepository repository;

    @BeforeEach
    void beforeEach() throws Exception {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new PlayerRepository(dataSource);
    }

//    @AfterEach
//    void clear() throws SQLException {
//        repository.clear();
//    }

    @Test
    void crud() throws SQLException {
        //save
        Player player = new Player("P26", "test", 20, "Korea", "C2");
        repository.save(player);

        //findById
        Player findPlayer = repository.findById(player.getPlayerId());
        Assertions.assertThat(findPlayer).isEqualTo(player);
//
//        //update : money:10000->20000
//        repository.update(.getMemberId(), 20000);
//        Member updateMember = repository.findById(member.getMemberId());
//        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);
//
//        //delete
        repository.delete(player.getPlayerId());
//        Assertions.assertThatThrownBy(() -> repository.findById(member.getMemberId()))
//                .isInstanceOf(NoSuchElementException.class);
    }
}