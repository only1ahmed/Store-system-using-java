package App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginCustomer extends Login {
    protected boolean verifyLogin(String columnName, String columnValue, String password) {
        try {
            String query = "SELECT username FROM users WHERE " + columnName + "= ? and password = ? and role = 'user'";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setString(1, columnValue);
            statement.setString(2, password);
            ResultSet answer = statement.executeQuery();
            if (answer.isBeforeFirst()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("verify login query exception");
        }
        return false;
    }
}
