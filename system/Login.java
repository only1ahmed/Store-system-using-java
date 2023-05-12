package system;

import java.sql.Connection;

public abstract class Login {
    protected JdbcSQLServerConnection JdbcConnection;
    protected Connection databaseConnection;

    public Login() {
        JdbcConnection = new JdbcSQLServerConnection("toffee");
        databaseConnection = JdbcConnection.getConnection();
    }

    public boolean Login(String id, String password) {
        return verifyLogin("username", id, password) || verifyLogin("email", id, password);
    }

    protected abstract boolean verifyLogin(String columnName, String columnValue, String password);

}
