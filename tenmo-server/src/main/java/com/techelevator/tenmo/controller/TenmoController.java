package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
public class TenmoController {

    private final TransferService transferService;
    private final AccountService accountService;
    private final UserService userService;

    public TenmoController(TransferService transferService, AccountService accountService, UserService userService) {
        this.transferService = transferService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("transfer")
    @PreAuthorize("hasRole('USER')")
    public Transfer createTransfer(@RequestBody Transfer transfer){
        return transferService.createTransfer(transfer);
    }

    @GetMapping("myaccount")
    //MAKE SURE TO CORRECT PERMISSIONS
    @PreAuthorize("hasRole('USER')")
   public BigDecimal getAccountBalanceByUserId (Principal principal){
        int loggedInUserId = userService.findIdByUsername(principal.getName());
        return accountService.returnBalanceByUserId(loggedInUserId);
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('USER')")
    public List<User> getOtherUsers (Principal principal) {

//        List<User> otherUsers = new List<User>;
        List<User> allUsers = userService.findAll();
//        int principalId = userService.findIdByUsername(principal.getName());
        for (User user : allUsers) {
            String user1 = user.getUsername();
            String userp = principal.getName();
            if (user1.equals(userp)) {
                allUsers.remove(user);
            }
        }
        return allUsers;
    }


    @GetMapping("accounts/{userId}")
    @PreAuthorize("hasRole('USER')")
    public Integer getAccountIdfromUserId (@PathVariable Integer userId, Principal principal) {
        return accountService.getAccountIdByUserId(userId, principal.getName());

    }
    //#########################POWER OF PRINCIPAL###################################
//    public Integer getAccountIdfromUserId ( Principal principal) {
//
//        int loggedInUserId = userService.findIdByUsername(principal.getName());
//        int loggedInAccountId = accountService.getAccountIdByUserId(loggedInUserId, principal.getName());
//        return loggedInAccountId;
//    }

    @GetMapping("user")
    @PreAuthorize("hasRole('USER')")
    public String getUsernameFromAccountId(@RequestParam Integer accountId, Principal principal){
        return accountService.getUsernameFromAccountId(accountId, principal.getName());
    }


    // *************OLD ENDPOINT FOR FINDING TRANSFER FOR LOGGED IN USER**********
//    @GetMapping("transfer/{userId}")
//    @PreAuthorize("hasRole('USER')")
//    public List<Transfer> getTransfersByUserId (@PathVariable Integer userId, Principal principal) {
//        return transferService.getTransfersByUserId(userId, principal.getName());
//    }


    @GetMapping("mytransfers")
    @PreAuthorize("hasRole('USER')")
    public List<Transfer> getAllPrincipalTransfers(Principal principal){
        int loggedInUserId = userService.findIdByUsername(principal.getName());
        return transferService.getAllLoggedInUserTransfers(loggedInUserId);
    }


    @GetMapping("mytransfers/{transferId}")
    @PreAuthorize("hasRole('USER')")
    public Transfer getTransferByTransferId (@PathVariable Integer transferId, Principal principal) {
        return transferService.getTransferByTransferId (transferId, principal.getName());
    }
}
