import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private final ArrayList<Page> pages = new ArrayList<>();
    private final Integer tableSize;

    public Table (Integer tableSize, Integer pageSize){
        int numberOfPages = tableSize / pageSize;
        this.tableSize = tableSize;
        for (int i = 0; i < numberOfPages; i++){
            pages.add(new Page(pageSize));
        }
    }

    public Integer insert(String text){
        for (Page page : pages){
            if (!page.isFull()){
                page.insert(text);
                return pages.indexOf(page);
            }
        }
        return null;
    }

    public void printTable(){
        for (Page page : pages){
            System.out.println("Page: " + pages.indexOf(page) + " -------------------------");
            for (Tuple tuple : page.getTuples()){
                System.out.println(tuple.toString());
            }
        }
    }

    public Integer getTableSize(){
        return tableSize;
    }
}
