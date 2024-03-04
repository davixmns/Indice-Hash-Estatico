import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter page size: ");
        Integer pageSize = scanner.nextInt();
        System.out.println("Enter number of pages on table: ");
        Integer numberOfPages = scanner.nextInt();

        Database database = new Database(numberOfPages, pageSize, 5, 200);

    }
}