package system;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class user {
    private String username;
    private String FirstName;
    private String LastName;
    private String email;
    private int ID;


    public int getID() {
        return ID;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUserData(String id) {
        try {
            String query = "SELECT * FROM users WHERE username = ? ";
            JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("users");
            Connection databaseConnection = JdbcConnection.getConnection();

            ResultSet answer = getUserRow("username", id, databaseConnection);
            if (!answer.isBeforeFirst()) {
                answer = getUserRow("email", id, databaseConnection);
            }

            username = answer.getString(1);
            email = answer.getString(4);
            FirstName = answer.getString(5);
            LastName = answer.getString(6);
            ID = answer.getInt(7);

        } catch (Exception e) {
            System.err.println("Error with loading user's data");
        }
    }

    ResultSet getUserRow(String columnName, String columnValue, Connection databaseConnection) {
        try {
            String query = "SELECT username FROM users WHERE " + columnName + "= ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, columnValue);
            return statement.executeQuery();
        } catch (Exception e) {
            System.err.println("searching for user in database exception");
            return null;
        }
    }
}
