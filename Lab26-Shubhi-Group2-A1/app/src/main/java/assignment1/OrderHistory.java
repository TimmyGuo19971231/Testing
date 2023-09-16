package assignment1;
import assignment1.*;
import java.util.*;
import java.io.*;

public class OrderHistory {
    private List<Cart> orders = new ArrayList<>();

    public List<Cart> getOrders() {
        return this.orders;
    }

    public void addOrder(Cart order) {
        this.orders.add(order);
    }

    public void loadFromFile(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                // parse the line to create an Order object and add it to orders
            }
        }
    }
}