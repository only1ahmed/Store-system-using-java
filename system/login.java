package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login {
    protected JdbcSQLServerConnection JdbcConnection;
    protected Connection databaseConnection;

    public login() {
        JdbcConnection = new JdbcSQLServerConnection("toffee");
        databaseConnection = JdbcConnection.getConnection();
    }

    public boolean Login(String id, String password) {
        return verifyLogin("username", id, password) || verifyLogin("email", id, password);
    }

    protected boolean verifyLogin(String columnName, String columnValue, String password) {
        try {
            String query = "SELECT username FROM users WHERE" + columnName + "= ? and password = ?";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, columnValue);
            statement.setString(2, password);
            ResultSet answer = statement.executeQuery();
            if (answer.next()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("verify login query exception");
        }
    }

}
