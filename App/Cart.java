package App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;

public class Cart {
    JdbcSQLServerConnection JdbcConnection;
    Connection databaseConnection;
    public static Scanner input = new Scanner(System.in);
    int orderID;
    private Vector<Pair<Item, Integer>> cartItems = new Vector<Pair<Item, Integer>>();

    public Cart() {
        JdbcConnection = new JdbcSQLServerConnection("toffee");
        databaseConnection = JdbcConnection.getConnection();

    }

    public void add(Item item, int itemAmount) {
        cartItems.add(new Pair<Item, Integer>(item, itemAmount));
    }

    public void display() {
        if (cartItems.isEmpty()) {
            System.out.println("\nCart is empty.");
            return;
        }
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.println(Integer.toString(i + 1) + ") " + cartItems.get(i).getFirst().name + ": " + cartItems.get(i).getSecond() + " " + cartItems.get(i).getFirst().unitType.name());
            System.out.println("\n");
        }
    }

    public void checkout(User user) {
        if (cartItems.isEmpty()) {
            System.out.println("\nCart is empty.");
            return;
        }
        String address;
        System.out.println("\nPlease enter your address: ");
        address = input.nextLine();
        if (sendDataToDatabase(user, address)) {
            System.out.println("\nYour order had been submitted successfully!");
        } else {
            System.out.println("\nThere was an error submitting your order, please try again later.");
        }

    }

    private boolean sendDataToDatabase(User user, String address) {
        orderID = generateOrder(user, address);

        try {
            String query = "INSERT INTO ordered_items (order_id, item_id, item_amount) VALUES (?, ?, ?)";
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            for (int i = 0; i < cartItems.size(); i++) {
                statement.setInt(1, orderID);
                statement.setInt(2, cartItems.get(i).getFirst().itemID);
                statement.setInt(3, cartItems.get(i).getSecond());
                statement.execute();
            }

        } catch (Exception e) {
            System.err.println("\nSubmitting orders error");
            return false;
        }
        return true;
    }

    // returns order id
    private int generateOrder(User user, String address) {
        String query = "INSERT INTO orders (user_id, address) OUTPUT inserted.order_id VALUES (?, ?) ";
        int id = -1;
        try {
            PreparedStatement statement = databaseConnection.prepareStatement(query);
            statement.setInt(1, user.getID());
            statement.setString(2, address);
            ResultSet answer = statement.executeQuery();
            if (answer.next()) {
                id = answer.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("\nError generating an order");
        }
        return id;
    }

}
