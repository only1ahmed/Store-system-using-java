package system;

import java.util.Scanner;

public class Run {
    static boolean logged_in = false;
    public static Scanner input = new Scanner(System.in);
    static LoginCustomer loginUtility = new LoginCustomer();
    static RegisterCustomer registerUtility = new RegisterCustomer();
    private static String loggedInID;
    private static final Customer userData = new Customer();

    public static void main(String[] args) {
        System.out.println("Welcome to Toffee store.\n");
        int choice;
        while (!logged_in) {
            try {

                System.out.println("What do you want to do?\n1) Login\n2) Register\n3) Exit");
                choice = input.nextInt();
                if (choice == 1) {
                    logged_in = menuLogin();
                    if (!logged_in) {
                        System.out.println("Username or password are incorrect.\n");
                    }
                } else if (choice == 2) {
                    menuRegister();
                } else if (choice == 3) {
                    System.out.println("\nThank you for using Toffee store!");
                    System.exit(0);
                } else {
                    System.out.println("\nPlease enter a valid option..");
                }
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid option..");
            }
        }

        System.out.println("Login done successfully\n");


        userData.setUserData(loggedInID);
        Store store = new Store(userData);
        store.run();

    }

    public static boolean menuLogin() {
        input.nextLine();
        String loginID, loginPassword;
        System.out.println("\nPlease enter your username or password..\n");
        System.out.println("\nUsername or email: ");
        loginID = input.nextLine();
        System.out.println("\nPassword: ");
        loginPassword = input.nextLine();
        if (loginUtility.Login(loginID, loginPassword)) {
            loggedInID = loginID;
            return true;
        }
        return false;
    }

    public static void menuRegister() {
        input.nextLine();
        String registerUsername, registerPassword, registerEmail, registerFirstname, registerLastname;
        System.out.println("please enter the following data: (username, password, email, first name , last name)\n");
        System.out.println("\nUsername:");
        registerUsername = input.nextLine();
        System.out.println("\nPassword: ");
        registerPassword = input.nextLine();
        System.out.println("\nEmail: ");
        registerEmail = input.nextLine();
        System.out.println("\nFirst name: ");
        registerFirstname = input.nextLine();
        System.out.println("\nLast name: ");
        registerLastname = input.nextLine();
        int code = registerUtility.register(registerUsername, registerPassword, registerEmail, registerFirstname, registerLastname);
        if (code == 1) {
            System.out.println("Registration done successfully\n");
        } else if (code == 0) {
            System.out.println("Please enter a stronger password..\n");
        } else if (code == -1) {
            System.out.println("Username or email already exists.\n");
        } else if (code == -2) {
            System.out.println("Unexpected error happened, please try again later.\n");
        }
    }
}
