package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Register {
    protected JdbcSQLServerConnection JdbcConnection;
    protected Connection databaseConnection;


    protected void buildDatabaseConnection() {
        databaseConnection = JdbcConnection.getConnection();
        JdbcConnection = new JdbcSQLServerConnection("toffee");
    }


    public Register() {
        buildDatabaseConnection();
    }

    public boolean verifyIfExists(String username) {
        boolean exists = false;
        try {
            String query = "SELECT username FROM users WHERE username = ? and password = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet answer = statement.executeQuery();
            if (answer.next()) {
                exists = true;
            }
        } catch (Exception e) {
            System.err.println("searching for user in database exception");
        }
        return exists;
    }


    private boolean verifyPasswordStrength(String password) {
        //todo
        //search for a standard password strength rule and apply it
        return true;
    }
}
