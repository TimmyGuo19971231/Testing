package assignment1;

import assignment1.*;
import java.util.*;
import java.io.*;

public class Cart {
    public static final String filepath = "src/main/resources/Cart.txt";
    private List<MenuItem> items = new ArrayList<>();
    public static int counter = 1;

    public void addItem(MenuItem item) {
        items.add(item);
        try {
            saveToFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
        try {
            saveToFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();  // or some other meaningful action
        }
    }

    public void clearItems() {
        this.items = new ArrayList<>();
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (MenuItem item : items) {
                System.out.println(item.getName() + "," + item.getDescription() + "," + item.getPrice());
            }
        }
    }

    public void loadFromFile(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                addItem(new MenuItem(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
        }
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(counter).append(". ").append("Cart contains:\n");
        for (MenuItem item : items) {
            sb.append(item.getName()).append(", ").append(item.getDescription()).append(", ").append(item.getPrice()).append("\n");
        }
        sb.append("Total amount: ").append(getTotalAmount());
        sb.append("\n");
        sb.append("\n");
        counter++;
        return sb.toString();
    }
    
}