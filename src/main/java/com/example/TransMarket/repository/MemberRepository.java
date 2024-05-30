package com.example.TransMarket.repository;

import com.example.TransMarket.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
public class MemberRepository {

    private final JdbcTemplate template;

    public MemberRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void clear() throws SQLException {
        String sql = "delete from Member";
        template.update(sql);
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";
        template.update(sql, memberId);
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";
        template.update(sql, money, memberId);
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        return template.queryForObject(sql, memberRowMapper(), memberId);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";
        template.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }


}
