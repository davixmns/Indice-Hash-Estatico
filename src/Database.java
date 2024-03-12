import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Database implements Serializable {
    private final Table table;
    private final BucketOverflow bucketOverflow;

    public Database(Integer tableSize, Integer pageSize, Integer bucketSize) {
        this.table = new Table(tableSize, pageSize);
        int numberOfBuckets = (tableSize / bucketSize) + 1;
        this.bucketOverflow = new BucketOverflow(numberOfBuckets, bucketSize);
        System.out.println("Database created successfully!");
    }

//    public Database(Integer tableSize, Integer pageSize, Integer bucketSize, Integer numberOfBuckets) {
//        this.table = new Table(tableSize, pageSize);
//        this.bucketOverflow = new BucketOverflow(numberOfBuckets, bucketSize);
//        System.out.println("Database created successfully!");
//    }

    public void populateDatabase(BufferedReader reader) {
        try {
            System.out.println("Populating database...");
            for (String word; (word = reader.readLine()) != null; ) {
                Integer pageIndex = this.table.insert(word);
                Integer bucketIndex = hashFunction(word);
                bucketOverflow.insert(bucketIndex, new Tuple(word, pageIndex));
            }
            System.out.println("Database populated successfully!");
        } catch (Exception e) {
            System.out.println("Error reading file -> " + e.getMessage());
        }
    }

    private int hashFunction(String word) {
        int hash = 0;
        int bucketsSize = this.bucketOverflow.getBucketsSize();
        for (int i = 0; i < word.length(); i++) {
            hash = (hash * 31 + word.charAt(i)) % bucketsSize;
        }
        return hash;
    }

    public String searchWord(String word) {
        Integer bucketIndex = hashFunction(word);
        Bucket bucket = bucketOverflow.getBucket(bucketIndex);
        Integer depth = 0;
        return searchWordRecursively(word, bucket, depth, bucketIndex);
    }

    public String searchWordRecursively(String word, Bucket bucketNode, Integer depth, Integer rootBucketIndex) {
        try {
            for (Tuple tuple : bucketNode.getTuples()) {
                String wordInTuple = tuple.getValue();

                if (Objects.equals(wordInTuple, word)) {
                    return ("A palavra " + word + " foi encontrada no bucket "
                            + rootBucketIndex + " com profundidade " + depth + " na página " + tuple.getKey());
                }

                if(tuple.getValue() == null){
                    return "A palavra " + word + " não foi encontrada";
                }
            }
            if (bucketNode.getNextBucket() != null) {
                depth++;
                Bucket nextBucket = bucketNode.getNextBucket();
                return searchWordRecursively(word, nextBucket, depth, rootBucketIndex);
            }
        } catch (Exception e) {
            System.err.println("ERRO AO PROCURAR A PALAVRA " + word);
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return null;
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

    public void printBucketsRecursively(Bucket bucketNode, Integer father) {
        System.out.println("Este bucket é filho de " + father);
        for (Tuple tuple : bucketNode.getTuples()) {
            System.out.println(tuple.toString());
        }
        System.out.println();
        Bucket nextBucket = bucketNode.getNextBucket();
        if (nextBucket != null) {
            printBucketsRecursively(nextBucket, father);
        }
    }

    public Integer getOverflowPercentage() {
        return bucketOverflow.getOverflowPercentage();
    }
}
