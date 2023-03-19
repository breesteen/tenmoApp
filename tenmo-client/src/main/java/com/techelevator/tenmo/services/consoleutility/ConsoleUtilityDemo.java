package com.techelevator.tenmo.services.consoleutility;

public class ConsoleUtilityDemo {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("*****************************");
        System.out.println("****** CONSOLE UTILITY ******");
        System.out.println("*****************************");
        System.out.println();


        /* output full palette */
        ConsoleUtility.outputPalette();

        /* print an error */
        //ConsoleUtility.printError("An error occurred");

        /* print a message w/up to two colors / whether to return after message / whether to reset after output */
        //ConsoleUtility.printMessage("Test Message", ConsoleUtility.ANSI_BLACK, ConsoleUtility.ANSI_LIGHT_GRAY_BACKGROUND, true, true);


        /* Example of using methods to print a header */
        /*
        System.out.println();

        ConsoleUtility.printMessage("                                                    ", ConsoleUtility.ANSI_YELLOW, ConsoleUtility.ANSI_LIGHT_GRAY_BACKGROUND, true, true);
        System.out.print(ConsoleUtility.ANSI_BOLD);
        ConsoleUtility.printMessage("                HAPPY CAPSTONE!!!                   ", ConsoleUtility.ANSI_YELLOW, ConsoleUtility.ANSI_LIGHT_GRAY_BACKGROUND, true, true);
        ConsoleUtility.printMessage("                                                    ", ConsoleUtility.ANSI_YELLOW, ConsoleUtility.ANSI_LIGHT_GRAY_BACKGROUND, true, true);

         */
    }


}
