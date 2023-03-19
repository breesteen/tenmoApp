package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.dao.mappers.TransferRowMapper;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

//    @Override
//    public List<Transfer> getTransfersByUserId(Integer userId, String username) {
//        String sql = "SELECT transfer_id, account_from, account_to, amount FROM transfer t " +
//                "JOIN account a ON t.account_from = a.account_id " +
//                "WHERE a.user_id = ? ";
//
//
////  Query is looking for an account_id and we are giving it a userId below:
//        List<Transfer> transferList = jdbcTemplate.query(sql, new Object[] { userId },
//                new TransferRowMapper());
//        return transferList;
//    }

    //***********************POWER OF PRINCIPAL***********************************
    @Override
    public List<Transfer> getAllLoggedInUserTransfers(Integer userId){
        String sql1 = "SELECT account_id FROM account WHERE user_id = ?";
        int loggedInAccountId = jdbcTemplate.queryForObject(sql1, Integer.class, userId);


      List<Transfer> principalTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, account_from, account_to, amount FROM transfer t " +
                "JOIN account a ON t.account_from = a.account_id " +
                "WHERE account_from= ? OR account_to = ? ";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, loggedInAccountId, loggedInAccountId);
        while(result.next()){
            principalTransfers.add(mapPrincipalTransfers(result));
        }
        return principalTransfers;
    }


    @Override
    public Transfer getTransferByTransferId (Integer transferId) {
        String sql = "SELECT * FROM transfer t WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        Transfer transfer = new Transfer();
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

@Override
    public Transfer createTransfer(Transfer newTransfer){
        String sql = "INSERT INTO transfer VALUES (DEFAULT, ?, ? ,?,?,?) RETURNING transfer_id";
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(),
            newTransfer.getAccountFromId(), newTransfer.getAccountToId(), newTransfer.getAmount());
        newTransfer.setTransferId(transferId);
        return newTransfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet transferRowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(transferRowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(transferRowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(transferRowSet.getInt("transfer_status_id"));
        transfer.setAccountFromId(transferRowSet.getInt("account_from"));
        transfer.setAccountToId(transferRowSet.getInt("account_to"));
        transfer.setAmount(transferRowSet.getBigDecimal("amount"));
        return transfer;
    }

    public Transfer mapPrincipalTransfers(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setAccountFromId(rowSet.getInt("account_from"));
        transfer.setAccountToId(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }





}
