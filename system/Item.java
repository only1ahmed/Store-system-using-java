package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Item {
    public String name, description, brand;
    Units unitType;
    public static int itemID;
    public int quantityAvailable, maxQuantity;
    ItemStatus status;
    double price;
    double discount;

    Item(int ID) {
        itemID = ID;
        getItemInfo();
    }

    public void getItemInfo() {
        ResultSet answer = getSet();
        if (answer == null) {
            System.out.println("item does not exist");
            return;
        }

        try {
            name = answer.getString(1);
            description = answer.getString(2);
            brand = answer.getString(3);
            itemID = answer.getInt(4);
            quantityAvailable = answer.getInt(5);
            {
                String unit = answer.getString(6);
                if (unit == "kg") {
                    unitType = Units.KG;
                } else {
                    unitType = Units.BOX;
                }
            }
            maxQuantity = answer.getInt(7);
            price = answer.getDouble(8);
            discount = answer.getDouble(9);
            {
                String s = answer.getString(10);
                if (s == "available") {
                    status = ItemStatus.available;
                } else if (s == "unavailable") {
                    status = ItemStatus.unavailable;
                } else if (s == "out_of_stock") {
                    status = ItemStatus.out_of_stock;
                }
            }

        } catch (Exception e) {
            System.err.println("Problem with items info reading from the Result set");
        }
    }

    public ResultSet getSet() {
        JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("items");
        Connection databaseConnection = JdbcConnection.getConnection();
        String query = "SELECT * FROM items WHERE id = ?";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, itemID);
            return statement.executeQuery();
        } catch (Exception e) {
            System.err.println("Getting item's info exception");
        }
        return null;
    }

}
