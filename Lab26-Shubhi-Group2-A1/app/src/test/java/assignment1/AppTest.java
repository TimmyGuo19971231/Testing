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
    public void testMainMenuOption3AdminDoNothing(){
        String input = "3\nTimmy\n123456\n4";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        App.mainMenu(scanner);
//        throw new RuntimeException("Actual output: " + outContent.toString());
        assertTrue(outContent.toString().contains("Admin menu"));
    }

    @Test
    public void testMainMenuOption3NotAdmin(){
        String input = "3\nadmin\npassword";
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
}
