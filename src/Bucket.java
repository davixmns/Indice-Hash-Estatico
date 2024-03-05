import java.util.ArrayList;

public class Bucket {
    private final ArrayList<Tuple> tuples;

    public Bucket(Integer bucketSize) {
        tuples = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            tuples.add(new Tuple(null, null));
        }
    }

    private boolean isFull() {
        for (Tuple tuple : tuples) {
            if (tuple.getValue() == null) {
                return false;
            }
        }
        return true;
    }

    public void insert(Integer key, String value) {
        if (!isFull()) {
            for (Tuple tuple : tuples) {
                if (tuple.getValue() == null) {
                    tuple.setValues(value, key);
                    return;
                }
            }
        } else {
            System.out.println("Bucket is full");
        }
    }
}
