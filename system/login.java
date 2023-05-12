package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class login {
    protected JdbcSQLServerConnection JdbcConnection;
    protected Connection databaseConnection;

    public login() {
        JdbcConnection = new JdbcSQLServerConnection("toffee");
        databaseConnection = JdbcConnection.getConnection();
    }

    public boolean Login(String id, String password) {
        return verifyLogin("username", id, password) || verifyLogin("email", id, password);
    }

    protected abstract boolean verifyLogin(String columnName, String columnValue, String password);

}
