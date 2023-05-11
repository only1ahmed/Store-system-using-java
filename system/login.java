package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login {
    private final String username;
    private final String password;
    private String id;
    private String role;
    private JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("toffee");
    boolean verified = false;

    public login(String username, String password) {
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
                verified = true;
            }
        } catch (Exception e) {
            System.out.println("verify login query exception");
        }
    }

    public Boolean verifyLogin() {

        return verified;
    }
}
