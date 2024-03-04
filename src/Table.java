import java.util.ArrayList;

public class Table {
    private final ArrayList<Page> pages = new ArrayList<>();

    public Table (Integer numberOfPages, Integer pageSize){
        for (int i = 0; i < numberOfPages; i++){
            pages.add(new Page(pageSize));
        }
    }
}
