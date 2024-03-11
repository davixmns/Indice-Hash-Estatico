import java.io.Serializable;
import java.util.ArrayList;

public class Page implements Serializable {
    private final ArrayList<Tuple> tuples = new ArrayList<>();

    public Page(Integer pageSize){
        for (int i = 0; i < pageSize; i++){
            tuples.add(new Tuple(null, null));
        }
    }

    public boolean isFull(){
        Tuple lastTuple = tuples.get(tuples.size() - 1);
        return lastTuple.getValue() != null;
    }

    public void insert(String text){
        for (Tuple tuple : tuples) {
            if (tuple.getValue() == null) {
                tuple.setValues(text, tuples.indexOf(tuple));
                break;
            }
        }
    }

    public ArrayList<Tuple> getTuples(){
        return tuples;
    }
}
