package com.techelevator.tenmo.model;

public class TransferType {

    private Integer transferTypeId;
    private String transferTypeDesc;

    public TransferType(){}

    public TransferType(Integer transferTypeId, String transferTypeDesc) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDesc = transferTypeDesc;
    }

    public Integer getTransferTypeId() {
        return transferTypeId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

}
