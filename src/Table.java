import java.util.ArrayList;

public class Table {
    private final ArrayList<Page> pages = new ArrayList<>();

    public Table (Integer numberOfPages, Integer pageSize){
        for (int i = 0; i < numberOfPages; i++){
            pages.add(new Page(pageSize));
        }
    }

    public void insert(String text){
        for (Page page : pages){
            if(!page.isFull()){
                page.insert(text);
                return;
            }
        }
    }

    public void printTable(){
        for (Page page : pages){
            System.out.println("Page: " + pages.indexOf(page) + " -------------------------");
            for (Tuple tuple : page.getTuples()){
                System.out.println(tuple.toString());
            }
        }
    }
}
