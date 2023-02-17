package Library.items;

public class Item {
    private static int idNum = 1;
    private final int id;
    public Item(){
        id = idNum;
        idNum ++;
    }
    public int getId() {
        return id;
    }
}
