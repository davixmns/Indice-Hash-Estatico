import java.util.ArrayList;

public class Bucket {
    private final ArrayList<Tuple> tuples = new ArrayList<>();

    public Bucket(Integer bucketSize) {
        for (int i = 0; i < bucketSize; i++) {
            tuples.add(new Tuple(null, ));
        }
    }

    public void insert(Integer key, String value) {

    }
}
