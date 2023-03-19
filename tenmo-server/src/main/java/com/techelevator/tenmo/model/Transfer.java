package com.techelevator.tenmo.model;

import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private final Integer TRANSFER_STATUS_PENDING = 1;
    private final Integer TRANSFER_STATUS_APPROVED = 2;
    private final Integer TRANSFER_STATUS_REJECTED = 3;

    private final Integer TRANSFER_TYPE_ID_REQUEST = 1;
    private final Integer TRANSFER_TYPE_ID_SEND = 2;

    private Integer transferId;
    private Integer transferTypeId = TRANSFER_TYPE_ID_SEND;
    private Integer transferStatusId = TRANSFER_STATUS_APPROVED;
    private Integer accountFromId;
    private Integer accountToId;
    private BigDecimal amount;

    public Transfer(){}

    public Transfer(Integer accountFromId, Integer accountToId, BigDecimal amount){
        this.accountToId = accountToId;
        this.accountFromId = accountFromId;
        this.amount = amount;
    }

    public Transfer(Integer transferId, Integer transferTypeId, Integer transferStatusId,
                    Integer accountFromId, Integer accountToId, BigDecimal amount){
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
    }


    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Integer transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Integer transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Integer getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Integer accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Integer getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Integer accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(transferId, transfer.transferId) && Objects.equals(transferTypeId, transfer.transferTypeId) && Objects.equals(transferStatusId, transfer.transferStatusId) && Objects.equals(accountFromId, transfer.accountFromId) && Objects.equals(accountToId, transfer.accountToId) && Objects.equals(amount, transfer.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, transferTypeId, transferStatusId, accountFromId, accountToId, amount);
    }
}
