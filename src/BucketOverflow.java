import java.io.Serializable;
import java.util.ArrayList;

public class BucketOverflow implements Serializable {
    public final ArrayList<Bucket> buckets;
    private final Integer bucketSize;
    private Integer overflowDepth = 0;
    public Integer insertedValues = 0;

    public BucketOverflow(Integer numberOfBuckets, Integer bucketSize) {
        buckets = new ArrayList<>();
        this.bucketSize = bucketSize;
        int i = 0;
        for (i = 0; i < numberOfBuckets; i++) {
            buckets.add(new Bucket(bucketSize));
        }
    }

    public void insert(Integer bucketIndex, Tuple tuple) {
        Bucket bucket = buckets.get(bucketIndex);
        Integer depth = 1;
        insertRecursively(bucketIndex, tuple, bucket, depth);
    }

    private void insertRecursively(Integer bucketIndex, Tuple tuple, Bucket bucket, Integer depth) {
        boolean buckerIsFull = bucket.isFull();
        if (!buckerIsFull) {
            bucket.insert(tuple);
            insertedValues++;
        } else {
//            System.out.println("BUCKET OVERFLOW no bucket " + bucketIndex + " com profundidade " + overflowDepth);
            overflowDepth++;
            Bucket newBucket = new Bucket(bucketSize);
            bucket.setNextBucket(newBucket);
            depth++;
            insertRecursively(bucketIndex, tuple, newBucket, depth);
        }
    }

    public Integer getBucketsSize(){
        return buckets.size();
    }

    public Bucket getBucket(Integer index){
        return buckets.get(index);
    }

    public Integer getBucketIndex(Bucket bucket){
        return buckets.indexOf(bucket);
    }
}
