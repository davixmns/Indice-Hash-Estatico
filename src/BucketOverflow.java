import java.io.Serializable;
import java.util.ArrayList;

public class BucketOverflow implements Serializable {
    public final ArrayList<Bucket> buckets;
    private final Integer bucketSize;
    private Integer collisionCount = 0;

    public BucketOverflow(Integer numberOfBuckets, Integer bucketSize) {
        buckets = new ArrayList<>();
        this.bucketSize = bucketSize;
        int i = 0;
        for (i = 0; i < numberOfBuckets; i++) {
            buckets.add(new Bucket(bucketSize));
        }
    }

    public void insert(Integer bucketIndex, Tuple newTuple) {
        Bucket bucket = buckets.get(bucketIndex);
        Integer depth = 0;
        insertRecursively(bucketIndex, newTuple, bucket, depth);
    }

    private void insertRecursively(Integer bucketIndex, Tuple tuple, Bucket bucketNode, Integer depth) {
        if (!bucketNode.isFull()) {
            bucketNode.insert(tuple);
        } else {
            depth++;
            if (bucketNode.getNextBucket() == null) {
//                System.out.println("OVERFLOW on bucket " + bucketIndex + " at word " + tuple.getValue());
                bucketNode.setNextBucket(new Bucket(bucketSize));
            } else {
                this.collisionCount++;
            }
            insertRecursively(bucketIndex, tuple, bucketNode.getNextBucket(), depth);
        }
    }

    public Float getOverflowPercentage() {
        int overflowCount = 0;
        for (Bucket bucket : buckets) {
            if (bucket.getNextBucket() != null) {
                overflowCount++;
            }
        }
        return (float) overflowCount / (float) buckets.size() * 100;
    }

    public Integer getCollisionPercentage() {
        return 0;
    }

    public Integer getBucketsSize() {
        return buckets.size();
    }

    public Bucket getBucket(Integer index) {
        return buckets.get(index);
    }

    public Integer getBucketIndex(Bucket bucket) {
        return buckets.indexOf(bucket);
    }
}
