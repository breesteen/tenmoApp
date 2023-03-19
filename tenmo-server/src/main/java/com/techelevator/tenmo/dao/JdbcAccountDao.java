package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public BigDecimal returnBalanceByUserId (Integer userId) {
        String sql = "SELECT balance FROM account a " +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id " +
                "WHERE a.user_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    @Override
    public Integer getUserIdFromAccountId (Integer accountId) {
        String sql = "SELECT user_id FROM account a " +
                "WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, accountId);
    }

    public String getUsernameFromAccountId(Integer accountId){
        String sql ="SELECT username FROM tenmo_user tu " +
                "JOIN account a ON a.user_id = tu.user_id " +
                "WHERE account_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, accountId);
    }

    @Override
    public void addToBalanceByUserId(Integer userId, BigDecimal transferAmount) {
        String sql = "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE account_id = " +
                "(SELECT account_id FROM account WHERE user_id = ?)";
         jdbcTemplate.update(sql, transferAmount, userId);
    }

    @Override
    public void withdrawFromBalanceByUserId(Integer userId, BigDecimal transferAmount) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE account_id = " +
                "(SELECT account_id FROM account WHERE user_id = ?)";
        jdbcTemplate.update(sql, transferAmount, userId);
    }


    @Override
    public Integer getAccountIdByUserId (Integer userId, String username) {
        String sql = "SELECT account_id FROM account a " +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id " +
                "WHERE a.user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

//######################################################################################
    public Integer getAccountIdByUserIdUsingPrincipal (Integer userId) {
        String sql = "SELECT account_id FROM account a " +
                "JOIN tenmo_user tu ON a.user_id = tu.user_id " +
                "WHERE a.user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);

    }




//
//    public  BigDecimal addAmountToBalanceByUserId (int userId, BigDecimal amount){
//        String sql = "UPDATE account SET balance = ? WHERE user_id = ?";
//
//    }
}
