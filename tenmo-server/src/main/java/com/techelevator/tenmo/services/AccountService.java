package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountService {

    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public BigDecimal returnBalanceByUserId(Integer UserId) {
        return accountDao.returnBalanceByUserId(UserId);
    }

    public void addToBalanceByUserId(Account account, Integer userId, BigDecimal transferAmount){
        accountDao.addToBalanceByUserId(userId, transferAmount);
    }

    public String getUsernameFromAccountId(Integer accountId, String username){
       return accountDao.getUsernameFromAccountId(accountId);
    }

    public void withdrawFromBalanceByUserId(Account account, Integer userId, BigDecimal transferAmount){
         accountDao.withdrawFromBalanceByUserId(userId, transferAmount);
    }

    public Integer getAccountIdByUserId (Integer userId, String username) {
        return accountDao.getAccountIdByUserId(userId, username);
    }

}
