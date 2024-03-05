import java.util.ArrayList;

public class Bucket {
    private final ArrayList<Tuple> tuples;

    public Bucket(Integer bucketSize) {
        tuples = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            tuples.add(new Tuple(null, null));
        }
    }

    public boolean isNotFull() {
        for (Tuple tuple : tuples) {
            if (tuple.getValue() == null) {
                return true;
            }
        }
        return false;
    }

    public void insert(Tuple newTuple) {
        if (isNotFull()) {
            for (Tuple tuple : tuples) {
                if (tuple.getValue() == null) {
                    tuple.setValues(newTuple.getValue(), newTuple.getKey());
                }
            }
        } else {
            System.out.println("Bucket is full");
        }
    }

    public ArrayList<Tuple> getTuples() {
        return tuples;
    }
}
