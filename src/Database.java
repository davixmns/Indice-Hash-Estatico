import java.io.BufferedReader;

public class Database {
    private final Table table;
    private final BucketOverflow bucketOverflow = new BucketOverflow();

    public Database(Integer tableSize, Integer pageSize, Integer bucketSize) {
        this.table = new Table(tableSize, pageSize);
        int numberOfBuckets = (tableSize / bucketSize) + 1;

        for (int i = 0; i < numberOfBuckets; i++) {
            bucketOverflow.buckets.add(new Bucket(bucketSize));
        }
    }

    public void populateDatabase(BufferedReader reader) {
        try {
            for (String line; (line = reader.readLine()) != null; ) {
                Integer pageIndex = this.table.insert(line);
                Integer hashedLine = hashFunction(line);
                System.out.println(hashedLine);
                bucketOverflow.insert(hashedLine, new Tuple(line, pageIndex));
            }
        } catch (Exception e) {
            System.out.println("Error reading file -> " + e.getMessage());
        }
    }

    private int hashFunction(String key) {
        int hash = 0;
        int primo = 47;
        for (int i = 0; i < key.length(); i++) {
            hash = (31 * hash + key.charAt(i)) % primo;
        }
        return hash;
    }

    public void printTable() {
        this.table.printTable();
    }

    public void printBuckets() {
        for (Bucket bucket : bucketOverflow.buckets) {
            System.out.println("Bucket: " + bucketOverflow.buckets.indexOf(bucket) + " -------------------------");
            for (Tuple tuple : bucket.getTuples()) {
                System.out.println(tuple.toString());
            }
        }
    }
}
