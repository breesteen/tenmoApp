package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests{
    protected static final Transfer TRANSFER_1 = new Transfer(3002, 2, 2, 2001,2002, new BigDecimal(10.00));
    protected static final Transfer TRANSFER_2 = new Transfer(3003, 2, 2, 2003,2004, new BigDecimal(15.00));
    private static final Transfer TRANSFER_3 = new Transfer(3004, 2, 2, 2004,2003, new BigDecimal(20.00));

    private JdbcTransferDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }


    @Test
    public void getAllLoggedInUserTransfers_given_valid_user_returns_transfer(){
        List<Transfer> transferList = sut.getAllLoggedInUserTransfers(1001);

        Assert.assertNotNull(transferList);


    }


}
