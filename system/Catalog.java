package system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class Catalog {
    private Vector<Item> allItems = new Vector<Item>();
    public int itemsCount = 0;

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
        JdbcSQLServerConnection JdbcConnection = new JdbcSQLServerConnection("toffee");
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


    public void displayItems() {
        int counter = 0;
        for (Item item : allItems) {
            counter++;
            System.out.println("\n\n" + counter + ")");
            item.printItemData();
        }
        itemsCount = counter;
    }

    public Item getItem(int itemIndex) {
        //// Expected a 1 based index !
        return allItems.get(itemIndex - 1);
    }
}
