import java.io.*;
import java.util.Scanner;

public class Main {
//    public static void main(String[] args) throws Exception {
//        BufferedReader reader = new BufferedReader(new FileReader("./files/words.txt"));
//
//        Integer tableSize = 470000;
//        Integer pageSize = 20000;
//        Integer bucketSize = 8000;
//
//        long startTime = System.currentTimeMillis();
//        Database database = new Database(tableSize, pageSize, bucketSize);
//        database.populateDatabase(reader);
//        long endTime = System.currentTimeMillis();
//        System.out.println("Total execution time: " + ((endTime - startTime) / 1000) + " seconds");
//
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./files/database.ser"))) {
//            oos.writeObject(database);
//            System.out.println("Database saved successfully!");
//        } catch (IOException e) {
//            System.out.println("Error saving database -> " + e);
//        }
//    }

    public static void serializeDatabase() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("./files/words.txt"));
        Integer tableSize = 470000;
        Integer pageSize = 20000;
        Integer bucketSize = 8000;

        long startTime = System.currentTimeMillis();
        Database database = new Database(tableSize, pageSize, bucketSize);
        database.populateDatabase(reader);
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + ((endTime - startTime) / 1000) + " seconds");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./files/database.ser"))) {
            oos.writeObject(database);
            System.out.println("Database saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving database -> " + e);
        }
    }

    public static Database deserializeDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./files/database.ser"))) {
            Database database = (Database) ois.readObject();
            System.out.println("Database loaded successfully!");
            return database;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading database -> " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - Serialize database");
        System.out.println("2 - Use serialized database");

        int option = scanner.nextInt();
        if(option == 1) {
            serializeDatabase();
        } else if(option == 2) {
            Database database = deserializeDatabase();
            if(database != null) {
                System.out.println("Enter a word to search: ");
                String word = scanner.next();
                System.out.println("Searching for " + word + "...");
                database.searchWord(word);
            }
        }
    }
}