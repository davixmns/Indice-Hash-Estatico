import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Database {
    private final ArrayList<Bucket> buckets = new ArrayList<>();
    private Table table;
    private final Integer tableSize;

    public Database(Integer tableSize, Integer numberOfPages, Integer pageSize, Integer bucketSize) {
        this.tableSize = tableSize;
        this.table = new Table(numberOfPages, pageSize);
        int numberOfBuckets = (tableSize / bucketSize) + 1;

        for (int i = 0; i < numberOfBuckets; i++) {
            this.buckets.add(new Bucket(bucketSize));
        }

        populateDatabase();
        this.table.printTable();
    }

    private void populateDatabase() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./files/entrada_teste.txt"));
            for (String line; (line = reader.readLine()) != null; ) {
                this.table.insert(line);
            }
        } catch (Exception e) {
            System.out.println("Error reading file -> " + e.getMessage());
        }
    }

    private int hashFunction(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (31 * hash + key.charAt(i)) % tableSize;
        }
        return hash;
    }

    public void insert(String text) {
        int index = hashFunction(text);
        Bucket bucket = buckets.get(index);
        bucket.insert(index, text);
    }
}
