package com.techelevator;

import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

public class NoahsClientScratchpad {

//    public User[] getOtherUsers(AuthenticatedUser user) {
//        try {
////            return restTemplate.getForObject(baseUrl + "departments/" + deptId + "/employees", Employee[].class);
//            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "users" , HttpMethod.GET,
//                    makeGetEntity(user), User[].class);
//            return response.getBody();
//        } catch (RestClientResponseException | ResourceAccessException ex) {
//            BasicLogger.log(ex.getMessage());
//            System.out.println(ex.getMessage());
//            return null;
//        }

//List of users returned will have usernames & user id's
//    getOtherUsers needs to be called in console service to print list of all other users and ids
//Server:
//    endpoint defined to receive the API request (for users/user ids)
//
//

}
