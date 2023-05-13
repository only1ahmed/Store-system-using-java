package App;

import java.util.Scanner;

public class Store {
    private User user;
    private Catalog catalog = new Catalog();
    private Cart cart = new Cart();
    public static Scanner input = new Scanner(System.in);

    public Store(User userData) {
        user = userData;
    }

    public void run() {
        boolean running = true;
        int choice = 0;
        while (running) {
            showMainMenu();
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid option..");
                continue;
            }
            if (choice == 1) {
                displayCatalog();
            } else if (choice == 2) {
                cart.display();
            } else if (choice == 3) {
                cart.checkout(user);
            } else if (choice == 4) {
                System.out.println("\nThank you for using Toffee store!");
                System.exit(0);
            } else {
                System.out.println("\nPlease enter a valid option..");
            }

        }
    }

    private void showMainMenu() {
        System.out.println("\n1) Show catalog of items");
        System.out.println("\n2) Show cart");
        System.out.println("\n3) Checkout");
        System.out.println("\n4) Exit");
    }

    private void displayCatalog() {
        catalog.displayItems();
        int choice = 0;
        while (true) {
            System.out.println("\n1) Add an item to cart");
            System.out.println("\n2) Return to main menu");
            System.out.println("\n3) Exit");
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid option..");
                continue;
            }
            if (choice == 1) {
                addItemToCart();
                break;
            } else if (choice == 2) {
                return;
            } else if (choice == 3) {
                System.out.println("\nThank you for using Toffee store!");
                System.exit(0);
            } else {
                System.out.println("\nPlease enter a valid option..");
            }
        }
    }

    private void addItemToCart() {
        int itemIndex = 0;
        System.out.println("\nEnter the index of the item you want to add to cart: ");
        while (true) {
            try {
                itemIndex = input.nextInt();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid option..");
                continue;
            }
            if (itemIndex > catalog.itemsCount || itemIndex < 1) {
                System.out.println("\nPlease enter a valid option..");
            } else {
                Item requiredItem = catalog.getItem(itemIndex);
                int itemAmount = getItemCountToCart(requiredItem);
                cart.add(requiredItem, itemAmount);
                break;
            }
        }
    }

    private int getItemCountToCart(Item requiredItem) {
        int itemAmount = 0;
        System.out.println("\nThe maximum amount available per order for " + requiredItem.name + " is: " + Integer.toString(requiredItem.maxQuantity));
        System.out.println("\nPlease enter the amount you want from " + requiredItem.name + ": ");
        while (true) {
            try {
                itemAmount = input.nextInt();
            } catch (Exception e) {
                System.out.println("\nPlease enter a valid option..");
                continue;
            }
            if (itemAmount > requiredItem.maxQuantity || itemAmount < 1) {
                System.out.println("\nPlease enter a valid Amount..");
            } else {
                return itemAmount;
            }
        }
    }
}
