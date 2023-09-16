package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<MenuItem> items = new ArrayList<>();
    private String name;

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMenuItem(MenuItem item) {
        items.add(item);
    }

    public void removeMenuItem(MenuItem item) {
        items.remove(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void loadFromFile(String categoryFilename) throws FileNotFoundException {
        File file = new File(categoryFilename);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + categoryFilename);
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length < 3) {
                    continue;
                }
                items.add(new MenuItem(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
        }
    }

    public void saveToFile(String categoryFilename) throws IOException {
        try (PrintWriter out = new PrintWriter(categoryFilename)) {
            for (MenuItem item : items) {
                out.println(item.getName() + "," + item.getDescription() + "," + item.getPrice());
            }
        }
    }
}