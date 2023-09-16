package assignment1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileProcessor {
    public boolean writeData(String username,String password,String filepath){
        try{
            List<List<String>> userData = readUserData(filepath);
            for (List<String> user: userData){
                if (user.get(0).equals(username)){
                    return false;
                }
            }
            Writer writer = new FileWriter(filepath,true);
            writer.write("\n" + username + "," + password + ",U");
            writer.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public List<String> readMenuData(String filepath){
        try{
            Reader r = new FileReader(filepath);
            Scanner sc = new Scanner(r);
            List<String> menu = new ArrayList<>();
            while (sc.hasNextLine()){
                String data = sc.nextLine();
                menu.add(data);
            }
            r.close();
            return menu;
        }catch (Exception e){
            return null;
        }
    }

    public List<List<String>> readUserData(String filepath){
        try{
            Reader reader = new FileReader(filepath);
            Scanner sc = new Scanner(reader);
            List<List<String>> data = new ArrayList<>();
            while (sc.hasNextLine()){
                String userData = sc.nextLine();
                String[] splitData = userData.split(",");
                data.add(Arrays.asList(splitData));
            }
            reader.close();
            return data;
        }catch (Exception e){
            System.err.println("Exception: " + e);
        }
        return null;
    }
}
