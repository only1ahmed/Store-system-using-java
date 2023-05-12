package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class Catalog {
    private Vector<Item> allItems;

    public Catalog() {
        getItems();
    }

    public void getItems() {
        ResultSet answer = getIDs();
        try {
            while (answer.next()) {
                allItems.add(new Item(answer.getInt(1)));
            }
        } catch (Exception e) {
            System.err.println("Error reading the item ids");
        }
    }

    public ResultSet getIDs() {
        JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("items");
        Connection databaseConnection = JdbcConnection.getConnection();
        String query = "SELECT id from items";
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            return statement.executeQuery();
        } catch (Exception e) {
            System.err.println("Getting item's info exception");
        }
        return null;
    }

    private void printItemData(int indexOfItem) {
        Item item = allItems.get(indexOfItem);
        System.out.println("\nName: " + item.name);
        System.out.println("\nDescription: " + item.description);
        System.out.println("\nUnit type: " + item.unitType.name());
        System.out.println("\nQuantity available: " + Integer.toString(item.quantityAvailable));
        System.out.println("\nMax quantity per order: " + Integer.toString(item.maxQuantity));
        System.out.println("\nStatus: " + item.status.name());
        System.out.println("\nPrice before discount: " + Double.toString(item.price));
        System.out.println("\nPrice after discount: " + Double.toString(item.price - (item.price * (item.discount / 100))));
    }

    public void displayItems() {
        int counter = 1;
        for (Item item : allItems) {
            System.out.println(Integer.toString(counter) + ") " + item.name + '\n');
        }
    }
}
