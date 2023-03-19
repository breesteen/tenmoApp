package com.techelevator.tenmo.model;

public class TransferStatus {

    private Integer transferStatusId;
    private String transferStatusDesc;

    public TransferStatus(){}

    public TransferStatus(Integer transferStatusId, String transferStatusDesc) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDesc = transferStatusDesc;
    }

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }
}
