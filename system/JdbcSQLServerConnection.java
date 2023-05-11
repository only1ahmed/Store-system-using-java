package system;

import javax.xml.transform.Result;
import java.sql.*;

public class JdbcSQLServerConnection {
    private String databaseName;
    private Connection connection;

    public JdbcSQLServerConnection(String databaseName) {
        try {
            this.databaseName = databaseName;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String databaseUsername = "mido";
            String databasePassword = "12345678";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=";
            url += databaseName;
            url += ";encrypt=false";
            connection = DriverManager.getConnection(url, databaseUsername, databasePassword);
        } catch (Exception e) {
            System.out.println("database connection exception");

        }
    }
    // another method to connect:
    // Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=users;integratedSecurity=false;encrypt=false;trustServerCertificate=false;user=mido;password=12345678");


    public ResultSet queryToDatabase(String query, boolean expectedResultSet) {
        ResultSet answer = null;
        try {
            Statement q = connection.createStatement();
            if (expectedResultSet) {
                answer = q.executeQuery(query);
            } else {
                q.execute(query);
            }
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return answer;
    }
}