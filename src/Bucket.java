import java.io.Serializable;
import java.util.ArrayList;

public class Bucket implements Serializable {
    private final ArrayList<Tuple> tuples;
    private final Integer bucketSize;
    private Bucket nextBucket;

    public Bucket(Integer bucketSize) {
        tuples = new ArrayList<>();
        this.bucketSize = bucketSize;
        for (int i = 0; i < bucketSize; i++) {
            tuples.add(new Tuple(null, null));
        }
    }

    public boolean isFull() {
        Tuple lastTuple = tuples.get(tuples.size() - 1);
        return lastTuple.getValue() != null;
    }

    public void insert(Tuple newTuple) {
        for (Tuple tuple : tuples) {
            if (tuple.getValue() == null) {
                String word = newTuple.getValue();
                Integer pageIndex = newTuple.getKey();
                tuple.setValues(word, pageIndex);
                return;
            }
        }
    }

    public void setNextBucket(Bucket nextBucket) {
        this.nextBucket = nextBucket;
    }

    public Bucket getNextBucket() {
        return nextBucket;
    }

    public ArrayList<Tuple> getTuples() {
        return tuples;
    }
}
