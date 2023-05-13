package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Register {
    protected JdbcSQLServerConnection JdbcConnection;
    protected Connection databaseConnection;


    protected void buildDatabaseConnection() {
        JdbcConnection = new JdbcSQLServerConnection("toffee");
        databaseConnection = JdbcConnection.getConnection();
    }


    public Register() {
        buildDatabaseConnection();
    }

    protected boolean verifyIfExists(String username, String email) {
        return ID_exists("username", username) || ID_exists("email", email);
    }

    protected boolean ID_exists(String columnName, String columnValue) {
        try {
            String query = "SELECT username FROM users WHERE " + columnName + "= ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, columnValue);
            ResultSet answer = statement.executeQuery();
            if (answer.next()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("searching for user in database exception");
        }
        return false;
    }

    private boolean verifyPasswordStrength(String password) {
        //todo
        //search for a standard password strength rule and apply it
        return true;
    }
}
