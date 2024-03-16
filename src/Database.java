import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Database implements Serializable {
    private Table table;
    private BucketOverflow bucketOverflow;

    public Database(BufferedReader reader, Integer pageSize) {
        try {
            ArrayList<String> words = new ArrayList<>();
            for (String word; (word = reader.readLine()) != null; ) {
                words.add(word);
            }

            Integer tableSize = words.size();
            Integer bucketSize = pageSize / 5;
            Integer numberOfBuckets = (tableSize / bucketSize) + 1;

            this.table = new Table(tableSize, pageSize);
            this.bucketOverflow = new BucketOverflow(numberOfBuckets, bucketSize);

            System.out.println("Database created successfully!");

            populateDatabase(words);

        } catch (Exception e) {
            System.out.println("Error creating database -> " + e.getMessage());
        }

    }

    private void populateDatabase(ArrayList<String> words) {
        try {
            System.out.println("Populating database...");
            for (String word : words) {
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
        int numberOfBuckets = this.bucketOverflow.getBucketsSize();
        for (int i = 0; i < word.length(); i++) {
            hash = (hash * 31 + word.charAt(i)) % numberOfBuckets;
        }
        return hash;
    }

//    public int hashFunction(String word) {
//        HashFunction hf = Hashing.murmur3_32();
//        int hash = hf.hashBytes(word.getBytes()).asInt();
//        int numberOfBuckets = this.bucketOverflow.getBucketsSize();
//        Integer result = Math.abs(hash % numberOfBuckets);
//        return result;
//    }

    public String searchWord(String word) {
        Integer bucketIndex = hashFunction(word);
        Bucket bucket = bucketOverflow.getBucket(bucketIndex);
        Integer depth = 0;
        return searchWordRecursively(word, bucket, depth, bucketIndex);
    }

    private String searchWordRecursively(String word, Bucket bucketNode, Integer depth, Integer rootBucketIndex) {
        try {
            for (Tuple tuple : bucketNode.getTuples()) {
                String wordInTuple = tuple.getValue();

                if (Objects.equals(wordInTuple, word)) {
                    return ("The word " + word + " was found in the bucket " + rootBucketIndex + " at depth " + depth + " and page " + tuple.getKey());
                }

                if (tuple.getValue() == null) {
                    return ("The word " + word + " was not found in the database");
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

    public Float getOverflowPercentage() {
        return this.bucketOverflow.getOverflowPercentage();
    }

    public Float getCollisionPercentage() {
        return this.bucketOverflow.getCollisionPercentage();
    }

    public ArrayList<Tuple> getTuples(Integer limit) {
        return this.table.getTuples(limit);
    }
}
