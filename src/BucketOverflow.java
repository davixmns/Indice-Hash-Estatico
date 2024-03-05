import java.util.ArrayList;

public class BucketOverflow {
    public final ArrayList<Bucket> buckets = new ArrayList<>();

    public void insert(Integer bucketIndex, Tuple tuple) {
        for (int i = 0; i < buckets.size(); i++) {
            if (i == bucketIndex) {
                if (buckets.get(i).isNotFull()) {
                    buckets.get(i).insert(tuple);
                    System.out.println("Inserted tuple in bucket " + tuple.getValue());
                    return;
                } else {
                    System.out.println("Bucket is full");
                }
            }
        }
    }


}
