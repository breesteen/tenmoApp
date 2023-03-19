package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    BigDecimal returnBalanceByUserId (Integer userId);

    Integer getUserIdFromAccountId (Integer accountId);

    String getUsernameFromAccountId(Integer accountId);

    void addToBalanceByUserId (Integer userId, BigDecimal transferAmount);

    void withdrawFromBalanceByUserId (Integer userId, BigDecimal transferAmount);

    Integer getAccountIdByUserId (Integer userId, String username);


    }
