package assignment1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testMenuOrderingDoNothing(){
        String input = "1\n3\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        App.init();
        App.mainMenu(new Scanner(System.in));
//        throw new RuntimeException("Actual output: " + outContent.toString());
        assertTrue(outContent.toString().contains("Exiting Program..."));
    }

    @Test
    public void testCheckout(){
        String input = "1\n2\nc\n1\nyes\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        App.init();
        App.mainMenu(new Scanner(System.in));
//        throw new RuntimeException("Actual output: " + outContent.toString());
        assertTrue(outContent.toString().contains("Exiting Program..."));
    }

    @Test
    public void testViewCartBackMainMenu(){
        String input = "1\n2\n0\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        App.init();
        App.mainMenu(new Scanner(System.in));
//        throw new RuntimeException("Actual output: " + outContent.toString());
        assertTrue(outContent.toString().contains("Exiting Program..."));
    }
    @Test
    public void testMainMenuOption3Admin(){
        String input = "3\nTimmy\n123456\n4\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        App.mainMenu(scanner);
//        throw new RuntimeException("Actual output: " + outContent.toString());
        assertTrue(outContent.toString().contains("Admin menu"));
    }

    @Test
    public void testMainMenuOption3NotAdmin(){
        String input = "3\nadmin\npassword\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        App.mainMenu(scanner);
//        throw new RuntimeException("Actual output: " + outContent.toString());
//        System.out.println("Actual output: " + outContent.toString());
        assertTrue(outContent.toString().contains("Invalid username or password"));
//        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));


    }
    @Test
    public void testMainMenuOptionFalse() {
        String input = "4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        App.mainMenu(scanner);
        assertTrue(outContent.toString().contains("Exiting Program..."));
    }

    @Test
    void testDeleteMenuItem() {
        String input = "3\nTimmy\n123456\n3\n1\n1\nyes\n4\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        App.init(); // Add this line
        App.mainMenu(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Menu item deleted successfully"));
    }


    @Test
    void testUpdateMenu() {
        String input = "3\nTimmy\n123456\n2\n1\n1\nname\nhotDog\n4\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        App.init();
        App.mainMenu(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Menu item updated successfully"));
    }

    @Test
    void testOrderItems(){
        String input = "1\n1\n1\n1\n1\n0\n0\n3\n4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        App.init();
        App.mainMenu(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Item added to cart"));
    }

    @Test
    void testPrintOrderHistory(){

        App.init();
        App.printOrderHistory();
        assertTrue(outContent.toString().contains("Burger, Delicious beef burger, 5.99"));
    }
}
