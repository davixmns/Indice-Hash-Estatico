import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private final ArrayList<Page> pages = new ArrayList<>();
    private final Integer tableSize;

    public Table(Integer tableSize, Integer pageSize) {
        int extraPages = 1;
        int numberOfPages = (tableSize / pageSize) + extraPages;
        this.tableSize = tableSize;
        for (int i = 0; i < numberOfPages; i++) {
            pages.add(new Page(pageSize));
        }
    }

    public Integer insert(String text) {
        for (int i = 0; i < pages.size(); i++) {
            Page page = pages.get(i);
            if (!page.isFull()) {
                page.insert(text);
                return i;
            }
        }
        System.out.println("Table is full!");
        return null;
    }

    public void printTable() {
        for (Page page : pages) {
            System.out.println("Page: " + pages.indexOf(page) + " -------------------------");
            for (Tuple tuple : page.getTuples()) {
                System.out.println(tuple.toString());
            }
        }
    }

    public ArrayList<Tuple> getTuples(Integer limit){
        ArrayList<Tuple> tuples = new ArrayList<>();
        for (Page page : pages) {
            for (Tuple tuple : page.getTuples()) {
                if(tuple.getValue() != null){
                    tuples.add(tuple);
                }
                if(tuples.size() == limit){
                    return tuples;
                }
            }
        }
        return tuples;
    }

    public Integer getTableSize() {
        return tableSize;
    }
}
