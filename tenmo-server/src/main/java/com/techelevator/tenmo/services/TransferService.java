package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TransferService {

    private TransferDao transferDao;
    private AccountDao accountDao;
    private AccountService accountService;
    private UserService userService;
    private UserDao userDao;

    public TransferService(TransferDao transferDao, AccountService accountService, AccountDao accountDao, UserService userService, UserDao userDao){
        this.transferDao = transferDao;
        this.accountService = accountService;
        this.accountDao = accountDao;
        this.userService = userService;
        this.userDao = userDao;
    }
        //ORIGINAL GET TRANSFER FOR LOGGED IN USER
//    public List<Transfer> getTransfersByUserId(Integer userId, String username){
//      return  transferDao.getTransfersByUserId(userId, username);
//
//    }


    public Transfer getTransferByTransferId(Integer transferId, String name){
        return  transferDao.getTransferByTransferId(transferId);
    }

//********************************************************************
    public List<Transfer> getAllLoggedInUserTransfers(Integer userId){
        return transferDao.getAllLoggedInUserTransfers(userId);
    }


    @Transactional
    public Transfer createTransfer(Transfer transfer) {
        int fromUserId = accountDao.getUserIdFromAccountId(transfer.getAccountFromId());
        int toUserId = accountDao.getUserIdFromAccountId(transfer.getAccountToId());
        BigDecimal curBalance = accountDao.returnBalanceByUserId(fromUserId);
        if (transfer.getAmount().compareTo(curBalance) <= 0) {
            accountDao.withdrawFromBalanceByUserId(fromUserId, transfer.getAmount());
            accountDao.addToBalanceByUserId(toUserId, transfer.getAmount());
            transferDao.createTransfer(transfer);
        }
        return transfer;
    }

}
