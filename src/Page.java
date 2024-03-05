import java.util.ArrayList;

public class Page {
    private final ArrayList<Tuple> tuples = new ArrayList<>();

    public Page(Integer pageSize){
        for (int i = 0; i < pageSize; i++){
            tuples.add(new Tuple(null, null));
        }
    }

    public boolean isFull(){
        for (Tuple tuple : tuples){
            if(tuple.getValue() == null){
                return false;
            }
        }
        return true;
    }

    public void insert(String text){
        for (int i = 0; i < tuples.size(); i++){
            if(tuples.get(i).getValue() == null){
                tuples.get(i).setValues(text, i);
                return;
            }
        }
    }

    public ArrayList<Tuple> getTuples(){
        return tuples;
    }
}
