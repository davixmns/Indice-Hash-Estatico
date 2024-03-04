import java.util.ArrayList;

public class Database {
    private final ArrayList<Bucket> buckets = new ArrayList<>();
    private Table table;
    private final Integer tableSize;

    public Database(Integer tableSize, Integer numberOfPages, Integer pageSize, Integer bucketSize) {
        this.tableSize = tableSize;
        this.table = new Table(numberOfPages, pageSize);
        int numberOfBuckets = tableSize / bucketSize;

        //create buckets
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new Bucket(pageSize));
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
