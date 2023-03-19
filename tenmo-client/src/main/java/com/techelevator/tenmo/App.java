package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.tenmo.services.consoleutility.ConsoleUtility;

import java.math.BigDecimal;
import java.util.List;

public class App {

    ConsoleUtility consoleUtility;

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TenmoService tenmoService = new TenmoService(API_BASE_URL);
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        BigDecimal balance = tenmoService.getAccountBalanceByUserId(currentUser);
        System.out.println("");
        ConsoleUtility.printMessage("---------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_WHITE,true, true);
        System.out.println("");
        ConsoleUtility.printMessage("Your Current Balance is: $" + balance, ConsoleUtility.ANSI_CYAN, ConsoleUtility.ANSI_GREEN, true, true);
        System.out.println("");
        ConsoleUtility.printMessage("---------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_WHITE,true, true);

    }

    private void viewTransferHistory() {
		// TODO Auto-generated method stub
		Transfer[] transfers = tenmoService.getTransfersByUserId(currentUser);
        displayTransferHistory(transfers);

        System.out.println(" ");


        int specificTransfer = consoleService.promptForInt("Enter Transfer Id for Specific Details about Transfer: ");

        viewTransferByTransferId(specificTransfer);
        System.out.println(" ");

	}

    private void displayTransferHistory(Transfer[] transfers) {

        if (transfers != null) {
            System.out.println("");
            ConsoleUtility.printMessage("------------------------------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_LIGHT_GREEN, true, true);
            System.out.println("         Here Is Your Transfer History ");
            System.out.println("");
            System.out.println("ID          From/To          Amount ");
            ConsoleUtility.printMessage("------------------------------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_LIGHT_GREEN, true, true);

            for (Transfer transfer : transfers) {
                if (transfer.getAccountToId().equals(tenmoService.getAccountIdByUserId(currentUser.getUser().getId(), currentUser))) {
                    System.out.println(transfer.getTransferId() + "    " + "    From: " + tenmoService.getUsernameFromAccountId(transfer.getAccountFromId(), currentUser)
                            + "         $" + transfer.getAmount());
                } else
//                    if (transfer.getAccountFromId().equals(tenmoService.getAccountIdByUserId(currentUser.getUser().getId(), currentUser))) {
                        System.out.println(transfer.getTransferId() + "    " + "    To: " + tenmoService.getUsernameFromAccountId(transfer.getAccountToId(), currentUser)
                                + "         $" + transfer.getAmount());
                    }
                }

            }

    public void viewTransferByTransferId(Integer transferId){

       Transfer transfer =tenmoService.getTransferByTransferId(transferId, currentUser);

        System.out.println(" ");
        ConsoleUtility.printMessage("---------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_LIGHT_GREEN, true, true);
        System.out.println("    Transfer Details");
        ConsoleUtility.printMessage("---------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_LIGHT_GREEN, true, true);
        System.out.println("Id: " + transfer.getAccountToId());
        System.out.println("From: " + tenmoService.getUsernameFromAccountId(transfer.getAccountFromId(), currentUser));
        System.out.println("To: " + tenmoService.getUsernameFromAccountId(transfer.getAccountToId(), currentUser));
        System.out.println("Type: " + "Send");
        System.out.println("Status: " + "Approved");
        System.out.println("Amount: $" +transfer.getAmount());
    }


	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        // TODO Auto-generated method stub
 //     //SEE ALL USERS
        User[] users = tenmoService.getOtherUsers(currentUser);
        displayOtherUsers(users);

//      //CREATE TRANSFER
//        Boolean noRecipient = true;
//        while (noRecipient) {
        System.out.println(" ");
        Integer recipientId = consoleService.promptForInt("Please enter the recipient's ID: ");
        BigDecimal transferAmount = consoleService.promptForBigDecimal("Please enter amount to send ($0.00): $");

//      //Check sender account for sufficient balance & check valid recipient ID (exists in users)
        BigDecimal currentBalance = tenmoService.getAccountBalanceByUserId(currentUser);
        if (transferAmount.compareTo(currentBalance) > 0)
            System.out.println("Error: Insufficient funds.");

        //Create new, valid Transfer object
        Transfer newTransfer = new Transfer();
        newTransfer.setTransferTypeId(newTransfer.getTransferTypeId());
        newTransfer.setTransferStatusId(newTransfer.getTransferStatusId());

        Integer fromAccountId = tenmoService.getAccountIdByUserId(currentUser.getUser().getId(), currentUser);
        Integer toAccountId = tenmoService.getAccountIdByUserId(recipientId, currentUser);

        newTransfer.setAccountFromId(fromAccountId);
        newTransfer.setAccountToId(toAccountId);
        newTransfer.setAmount(transferAmount);

        //Post new Transfer and UPDATE BOTH ACCOUNTS INVOLVED WITH TRANSACTION
        tenmoService.postTransfer(newTransfer, currentUser);

	}

    private void displayOtherUsers(User[] users) {
        if (users != null) {
            System.out.println(" ");
            ConsoleUtility.printMessage("-----------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_LIGHT_GREEN, true ,true);
            System.out.println("         Users     ");
            System.out.println("ID               Name");
            ConsoleUtility.printMessage("-----------------------------", ConsoleUtility.ANSI_LIGHT_GREEN, ConsoleUtility.ANSI_LIGHT_GREEN, true ,true);
            for (User user: users) {
                System.out.println(user.getId() + "             " + user.getUsername());
            }
        }
    }

    private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
