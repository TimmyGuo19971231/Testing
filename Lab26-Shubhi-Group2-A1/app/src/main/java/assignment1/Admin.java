package assignment1;

import assignment1.*;
import java.util.*;
import java.io.*;

public class Admin {
    private Menu menu;
    private OrderHistory orderHistory;

    public Admin(Menu menu, OrderHistory orderHistory) {
        this.menu = menu;
        this.orderHistory = orderHistory;
    }

    public void addMenuItem(MenuItem item) {
        menu.addMenuItem(item);
    }

    public void removeMenuItem(MenuItem item) {
        menu.removeMenuItem(item);
    }

    public List<Cart> viewOrderHistory() {
        return orderHistory.getOrders();
    }
}