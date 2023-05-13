package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class Item {
    public String name, description, brand;
    Units unitType;
    public int itemID;
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
            answer.next();
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
                if (Objects.equals(s, "available")) {
                    status = ItemStatus.available;
                } else if (Objects.equals(s, "unavailable")) {
                    status = ItemStatus.unavailable;
                } else if (Objects.equals(s, "Out of stock")) {
                    status = ItemStatus.out_of_stock;
                }
            }

        } catch (Exception e) {
            System.err.println("Problem with items info reading from the Result set");
        }
    }

    public ResultSet getSet() {
        JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("toffee");
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

    public void printItemData() {
        System.out.println("\nName: " + this.name);
        System.out.println("\nDescription: " + this.description);
        System.out.println("\nUnit type: " + this.unitType.name());
        System.out.println("\nQuantity available: " + Integer.toString(this.quantityAvailable));
        System.out.println("\nMax quantity per order: " + Integer.toString(this.maxQuantity));
        System.out.println("\nStatus: " + this.status.name());
        System.out.println("\nPrice before discount: " + Double.toString(this.price));
        System.out.println("\nPrice after discount: " + Double.toString(this.price - (this.price * (this.discount / 100))));
    }
}
