package assignment1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static final String filepath = "src/main/resources/User.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static List<Menu> menus = new ArrayList<>();
    private static Cart currentCart = new Cart();
    private static OrderHistory orderHistory = new OrderHistory();
    private static boolean loop = true;

    public static void main(String[] args) {
        init();
        mainMenu(scanner);

    }

    private static void init(){
        try {
            File categoriesFile = new File("src/main/resources/Categories.txt");
            Scanner fileScanner = new Scanner(categoriesFile);

            while (fileScanner.hasNextLine()) {
                String category = fileScanner.nextLine().trim();
                String formattedCategory = Character.toUpperCase(category.charAt(0))
                        + category.substring(1).replace(".txt", "");
                Menu menu = new Menu(formattedCategory);
                menu.loadFromFile("src/main/resources/categories/" + category);
                menus.add(menu);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void mainMenu(Scanner scanner) {

        System.out.println("Pick from these options:\n1. Menu Ordering\n2. Order History\n3. Login as admin");
        int option = scanner.nextInt();
        if (option == 1) {
            handleMenuOrdering();
        } else if (option == 2) {
            printOrderHistory();
        } else if (option == 3) {
            handleAdminLogin(scanner);
        } else{
            System.out.println("Exiting Program...");
            return;
        }
    }
    
    private static void handleMenuItems(Menu menu,Scanner scanner) {
        List<MenuItem> items = menu.getItems();
        while (true) {
            System.out.println("Here are the items in the selected menu:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i).getName());
            }
            System.out.println("Press 0 to go back to menu categories\nPress C to go to cart");
            String option = scanner.next();
            if (option.equals("0")) {
                return;
            } else if (option.equalsIgnoreCase("C")) {
                handleViewCart();
                return;
            } else {
                int itemIndex = Integer.parseInt(option) - 1;
                handleOrderItem(items.get(itemIndex),scanner);
            }
        }
    }

    private static void handleOrderItem(MenuItem item,Scanner scanner) {
        System.out.println("You have selected " + item.getName() + "\nSelect quantity:");
        int quantity = scanner.nextInt();
        for (int i = 0; i < quantity; i++) {
            currentCart.addItem(item);
        }
        System.out.println("Item added to cart");
    }
    
    private static void handleMenuOrdering() {
        while (true) {
            System.out.println(
                    "Menu ordering [pick option]\n1. List menu categories\n2. View cart\n3. Back to main menu");
            int option = scanner.nextInt();
            if (option == 1) {
                handleMenuCategories(scanner);
            } else if (option == 2) {
                handleViewCart();
            } else if (option == 3) {
                return;
            }
        }
    }
    private static void handleMenuCategories(Scanner scanner) {
        while (true) {
            System.out.println("Here are the following menu categories, select from below to order:");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println((i + 1) + ". " + menus.get(i).getName());
            }
            System.out.println("Press 0 to go back to categories\nPress C to go to cart");
            String option = scanner.next();
            if (option.equals("0")) {
                return;
            } else if (option.equalsIgnoreCase("C")) {
                handleViewCart();
                return;
            } else {
                int menuIndex = Integer.parseInt(option) - 1;
                handleMenuItems(menus.get(menuIndex),scanner);
            }
        }
    }
    private static void printOrderHistory() {
        try {
            String filepath = "src/main/resources/OrderHistory.txt";
            FileProcessor fp = new FileProcessor();
            List<String> data = fp.readMenuData(filepath);
            for (String order : data) {
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleAdminLogin(Scanner scanner) {
        ArrayList<String[]> adminUsers = getAdminUsers();

        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        boolean isValidAdmin = false;
        for (String[] user : adminUsers) {
            if (user[0].equals(username) && user[1].equals(password)) {
                isValidAdmin = true;
                break;
            }
        }
        if (isValidAdmin) {
            handleAdminMenu(scanner);
            System.out.println("Admin menu");
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static ArrayList<String[]> getAdminUsers() {
        ArrayList<String[]> adminUsers = new ArrayList<>();
        try {
            File file = new File("./src/main/resources/User.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                String[] user = line.split(",");
                if (user[2].equals("A")) {
                    adminUsers.add(new String[] { user[0], user[1] });
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return adminUsers;
    }

    private static void handleAdminMenu(Scanner scanner) {
        System.out.println("Welcome Admin");
        System.out.println("So far there have been " + orderHistory.getOrders().size() + " orders processed");

        while (true) {
            System.out.println(
                    "Pick from these options:\n1. Add menu item\n2. Update menu item\n3. Delete menu item\n4. Back to main menu");
            int option = scanner.nextInt();
            if (option == 1) {
                handleAddMenuItem(scanner);
            } else if (option == 2) {
                handleUpdateMenuItem();
            } else if (option == 3) {
                handleDeleteMenuItem();
            } else if (option == 4) {
                return;
            }
        }
    }

    private static void handleCheckout() {
        System.out.println("Select Delivery or pickup:\n1. Delivery\n2. Pickup");
        int deliveryOption = scanner.nextInt();
        System.out.println("Confirm order? yes or no");
        String confirm = scanner.next();
        if (confirm.equalsIgnoreCase("yes")) {
            System.out.println("Congrats for your successful order");
            handleOrderHistory();
            currentCart.clearItems();
            mainMenu(scanner);
        }
    }

    private static void handleAddMenuItem(Scanner scanner) {
        while (true) {
            System.out.println("Pick a menu to add item to or press 0 to go back:");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println((i + 1) + ". " + menus.get(i).getName());
            }
            int option = scanner.nextInt();
            if (option == 0) {
                return;
            } else {
                Menu selectedMenu = menus.get(option - 1);
                System.out.println("Enter name of the menu item:");
                String name = scanner.next();
                System.out.println("Enter description of the menu item:");
                String description = scanner.next();
                System.out.println("Enter price of the menu item:");
                double price = scanner.nextDouble();
                MenuItem newItem = new MenuItem(name, description, price);
                selectedMenu.addMenuItem(newItem);
                System.out.println("Menu item added successfully");
                return;
            }
        }
    }

    private static void handleUpdateMenuItem() {
        while (true) {
            System.out.println("Pick a menu to update item from or press 0 to go back:");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println((i + 1) + ". " + menus.get(i).getName());
            }
            int option = scanner.nextInt();
            if (option == 0) {
                return;
            } else {
                Menu selectedMenu = menus.get(option - 1);
                System.out.println("Pick a menu item to update:");
                List<MenuItem> items = selectedMenu.getItems();
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ". " + items.get(i).getName());
                }
                int itemOption = scanner.nextInt();
                MenuItem selectedItem = items.get(itemOption - 1);
                System.out.println("Enter field to edit (name, description, price):");
                String field = scanner.next();
                System.out.println("Enter new value:");
                if (field.equalsIgnoreCase("name")) {
                    selectedItem.setName(scanner.next());
                } else if (field.equalsIgnoreCase("description")) {
                    selectedItem.setDescription(scanner.next());
                } else if (field.equalsIgnoreCase("price")) {
                    selectedItem.setPrice(scanner.nextDouble());
                }
                System.out.println("Menu item updated successfully");
                return;
            }
        }
    }

    private static void handleDeleteMenuItem() {
        while (true) {
            System.out.println("Pick a menu to delete item from or press 0 to go back:");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println((i + 1) + ". " + menus.get(i).getName());
            }
            int option = scanner.nextInt();
            if (option == 0) {
                return;
            } else {
                Menu selectedMenu = menus.get(option - 1);
                System.out.println("Pick a menu item to delete:");
                List<MenuItem> items = selectedMenu.getItems();
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i + 1) + ". " + items.get(i).getName());
                }
                int itemOption = scanner.nextInt();
                MenuItem selectedItem = items.get(itemOption - 1);
                System.out.println("Are you sure you want to delete this item? (yes/no)");
                String confirm = scanner.next();
                if (confirm.equalsIgnoreCase("yes")) {
                    selectedMenu.removeMenuItem(selectedItem);
                    System.out.println("Menu item deleted successfully");
                }
                return;
            }
        }
    }

    private static void handleOrderHistory() {
        String filepath = "src/main/resources/OrderHistory.txt";
        try {
            Writer writer = new FileWriter(filepath, true);
            writer.write(currentCart.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void handleViewCart() {
        List<MenuItem> cartItems = currentCart.getItems();
        while (true) {
            System.out.println("Here are the items in your cart:");
            for (int i = 0; i < cartItems.size(); i++) {
                System.out.println((i + 1) + ". " + cartItems.get(i).getName() + " - " + cartItems.get(i).getPrice());
            }
            System.out.println(
                    "Press 0 to go back to main menu\nPress D to delete an item from the cart\nPress C to checkout");
            String option = scanner.next();
            if (option.equalsIgnoreCase("C")) {
                handleCheckout();
                break;
            }
            if (option.equalsIgnoreCase("D")) {
                System.out.println("Enter the item number to delete:");
                int itemNumber = scanner.nextInt();
                option = "D" + itemNumber;
            }
            if (option.equals("0")) {
                return;
            } else if (option.startsWith("D")) {
                int itemIndex = Integer.parseInt(option.substring(1)) - 1;
                currentCart.removeItem(cartItems.get(itemIndex));
                System.out.println("Item removed from cart");
            }
        }
    }

}