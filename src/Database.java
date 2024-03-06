import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Database implements Serializable {
    private final Table table;
    private final BucketOverflow bucketOverflow;

    public Database(Integer tableSize, Integer pageSize, Integer bucketSize) {
        this.table = new Table(tableSize, pageSize);
        int numberOfBuckets = (tableSize / bucketSize) + 1;

        this.bucketOverflow = new BucketOverflow(numberOfBuckets, bucketSize);
    }

    public void populateDatabase(BufferedReader reader) {
        try {
            System.out.println("Populating database...");

            for (String word; (word = reader.readLine()) != null; ) {
                Integer pageIndex = this.table.insert(word);
                Integer bucketIndex = hashFunction(word);
                bucketOverflow.insert(bucketIndex, new Tuple(word, pageIndex));
            }

            System.out.println(bucketOverflow.insertedValues);

            System.out.println("Database populated successfully!");
        } catch (Exception e) {
            System.out.println("Error reading file -> " + e.getMessage());
        }
    }

    private int hashFunction(String word) {
        int hash = 0;
        for (int i = 0; i < word.length(); i++) {
            hash = (hash * 31 + word.charAt(i)) % this.bucketOverflow.getBucketsSize();
        }
        return hash;
    }

    public void searchWord(String word){
        Integer bucketIndex = hashFunction(word);
        Bucket bucket = bucketOverflow.getBucket(bucketIndex);
        searchWordRecursively(word, bucket);
    }

    public void searchWordRecursively(String word, Bucket rootBucket){
        for (Tuple tuple : rootBucket.getTuples()){
            if(tuple.getValue().equals(word)){
                System.out.println("A palavra " + word + " foi encontrada no bucket "
                        + bucketOverflow.getBucketIndex(rootBucket) + " na página " + tuple.getKey());
                return;
            }
        }
        if(rootBucket.getNextBucket() != null){
            searchWordRecursively(word, rootBucket.getNextBucket());
        }
    }


    public void printBucketsRecursively(Bucket nextBucket, Integer father) {
        System.out.println("Este bucket é filho de " + father);
        for (Tuple tuple : nextBucket.getTuples()) {
            System.out.println(tuple.toString());
        }
        System.out.println();
        if (nextBucket.getNextBucket() != null) {
            printBucketsRecursively(nextBucket.getNextBucket(), father);
        }
    }

    public void printTable() {
        this.table.printTable();
    }

    public void printBuckets() {
        for (Bucket bucket : bucketOverflow.buckets) {
            System.out.println("Bucket: " + bucketOverflow.buckets.indexOf(bucket));
            for (Tuple tuple : bucket.getTuples()) {
                System.out.println(tuple.toString());
            }

            System.out.println();
            if (bucket.getNextBucket() != null) {
                printBucketsRecursively(bucket.getNextBucket(), bucketOverflow.buckets.indexOf(bucket));
            }
        }
    }
}
