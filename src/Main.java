import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Integer tableSize = 107;
        Integer numberOfPages = 5;
        Integer pageSize = 10;
        Integer bucketSize = 3;

        Database database = new Database(tableSize, numberOfPages, pageSize, bucketSize);

    }
}