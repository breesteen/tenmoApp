package com.techelevator.tenmo.dao.mappers;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferRowMapper implements RowMapper<Transfer> {

    @Override
    public Transfer mapRow(ResultSet resultSet, int i) throws SQLException {
        Transfer transfer = new Transfer();
        transfer.setTransferId(resultSet.getInt("transfer_id"));
        transfer.setAccountFromId(resultSet.getInt("account_from"));
        transfer.setAccountToId(resultSet.getInt("account_to"));
        transfer.setAmount(resultSet.getBigDecimal("amount"));
        return transfer;
    }

}
