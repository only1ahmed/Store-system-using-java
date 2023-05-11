package system;

import java.sql.ResultSet;

public class register {
    private final String username;
    private final String password;
    private JdbcSQLServerConnection databaseConnection = new JdbcSQLServerConnection("toffee");
    private boolean exists = false;

    public register(String username, String password) {
        this.username = username;
        this.password = password;

        try {
            String query = "SELECT username FROM users WHERE username = '";
            query += username;
            query += "' and password = '";
            query += password;
            query += "'";
            ResultSet answer = databaseConnection.queryToDatabase(query, true);
            if (answer != null && answer.next()) {
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
                    String query = "INSERT INTO users (username, password) VALUES ";
                    query += "( '" + username + "' , '" + password + "' )";
                    databaseConnection.queryToDatabase(query, false);
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
