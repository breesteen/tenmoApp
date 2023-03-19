package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TenmoService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public TenmoService(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public BigDecimal getAccountBalanceByUserId(AuthenticatedUser user) {
        try {
        ResponseEntity<BigDecimal> response = restTemplate.exchange(baseUrl + "myaccount", HttpMethod.GET, makeGetEntity(user), BigDecimal.class);
        return response.getBody();
         } catch (RestClientResponseException | ResourceAccessException ex) {
             BasicLogger.log(ex.getMessage());
             System.out.println(ex.getMessage());
             return null;
        }
    }

    public Integer getAccountIdByUserId(Integer userId, AuthenticatedUser user) {
        try {
            ResponseEntity<Integer> response = restTemplate.exchange(baseUrl + "accounts/"
                    + userId, HttpMethod.GET, makeGetEntity(user), Integer.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }
    public String getUsernameFromAccountId(Integer accountId, AuthenticatedUser user){
        try {
            ResponseEntity<String> response = restTemplate.exchange(baseUrl + "user?accountId=" + accountId, HttpMethod.GET,
                    makeGetEntity(user), String.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public User[] getOtherUsers( AuthenticatedUser user) {
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users",  HttpMethod.GET,
                    makeGetEntity(user), User[].class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }


    public void postTransfer(Transfer newTransfer, AuthenticatedUser user) {
    try {
        Transfer transfer = restTemplate.postForObject(baseUrl + "transfer",
                makeTransferEntity(newTransfer, user), Transfer.class);
        System.out.println("");
        System.out.println("Transfer Completed.");
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public Transfer[] getTransfersByUserId(AuthenticatedUser user) {
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(baseUrl + "mytransfers",
                    HttpMethod.GET, makeGetEntity(user), Transfer[].class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Transfer getTransferByTransferId(Integer transferId, AuthenticatedUser user) {
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "mytransfers/" + transferId,
                    HttpMethod.GET, makeGetEntity(user), Transfer.class);
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }


    public HttpEntity<Transfer> makeTransferEntity(Transfer transfer, AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());

        return new HttpEntity<>(transfer, headers);
    }

    public HttpEntity<Void> makeGetEntity(AuthenticatedUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());

        return new HttpEntity<>(headers);
    }
}