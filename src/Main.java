import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("./files/teste.txt"));

        Integer tableSize = 40;
        Integer pageSize = 10;
        Integer bucketSize = 5;

        Database database = new Database(tableSize, pageSize, bucketSize);

        database.populateDatabase(reader);

//        database.printTable();
//        database.printBuckets();
    }
}