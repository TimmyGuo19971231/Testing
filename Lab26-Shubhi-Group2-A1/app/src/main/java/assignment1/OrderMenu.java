package assignment1;
import assignment1.FileProcessor;

import java.util.List;

public class OrderMenu {
    public static final String filepath = "src/main/resources/Menu.txt";
    public void userInterface(){
        FileProcessor fp = new FileProcessor();
        List<String> menu = fp.readMenuData(filepath);
        for (String data : menu){
            System.out.println(data);
        }
    }
}
