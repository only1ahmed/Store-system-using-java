package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class register {
    private final String username;
    private final String password;
    private JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("toffee");
    private boolean exists = false;

    public register(String username, String password) {
        this.username = username;
        this.password = password;

        try {
            String query = "SELECT username FROM users WHERE username = ? and password = ?";
            Connection databaseConnection = JdbcConnection.getConnection();
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet answer = statement.executeQuery();
            if (answer.next()) {
                exists = true;
            }
        } catch (Exception e) {
            System.out.println("searching for user in database exception");
        }
    }

    public int verifyRegistration() {
        if (exists) {
            //code for that username exists
            return -1;
        } else {
            boolean isStrongPassword = verifyPasswordStrength();
            if (isStrongPassword) {
                try {
                    String query = "INSERT INTO users (username, password) VALUES (?,?) ";
                    Connection databaseConnection = JdbcConnection.getConnection();
                    PreparedStatement statement = databaseConnection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.execute();
                    //success code
                    return 1;
                } catch (Exception e) {
                    System.out.println("insertion failed");
                    return 2;
                }
            } else {
                //code for weak passwords
                return 0;
            }

        }
    }

    private boolean verifyPasswordStrength() {
        //todo
        //search for a standard password strength rule and apply it
        return true;
    }
}
