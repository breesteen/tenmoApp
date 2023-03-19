package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

//    List<Transfer> getTransfersByUserId(Integer userId, String username);

    Transfer getTransferByTransferId(Integer transferId);

    Transfer createTransfer(Transfer newTransfer);

    List<Transfer> getAllLoggedInUserTransfers(Integer userId);

}
