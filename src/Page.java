import java.util.ArrayList;

public class Page {
    private final ArrayList<Tuple> tuples = new ArrayList<>();

    public Page(Integer pageSize){
        for (int i = 0; i < pageSize; i++){
            tuples.add(new Tuple(null, i));
        }
    }

}
