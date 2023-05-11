package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterCustomer extends Register {

    private int insertUser(String username, String password, String email, String FirstName, String LastName) {
        try {
            String query = "INSERT INTO users (username, password, email, firstname, lastname) VALUES (?,?,?,?,?) ";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, FirstName);
            statement.setString(5, LastName);
            statement.execute();
            //success code
            return 1;
        } catch (Exception e) {
            System.err.println("insertion failed");
            //code for registration failure
            return -2;
        }

    }

    public int Register(String username, String password, String email, String FirstName, String LastName) {
        if (verifyIfExists(username, email)) {
            //code for that username exists
            return -1;
        } else {
            if (verifyPasswordStrength(password)) {
                return insertUser(username, password, email, FirstName, LastName);
            } else {
                //code for weak passwords
                return 0;
            }

        }
    }

    private boolean verifyPasswordStrength(String password) {
        //todo
        //search for a standard password strength rule and apply it
        return true;
    }
}