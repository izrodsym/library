package Library;

import Library.items.Item;

import java.util.Random;

public class Person {
    private Item item;
    private final String name;

    public Person(){
        String[] names = {"Ivan", "Peter", "Mario", "George", "Asen"};
        Random rand = new Random();
        name = names[rand.nextInt(names.length)];
    }

    public boolean rentItem(Item item) {
        if(this.item != null){
            return false;
        }
        this.item = item;
        return true;
    }

    public Item returnItem(){
        Item item = this.item;
        this.item = null;
        return item;
    }

    @Override
    public String toString() {
        return name;
    }
    public void showItem(){
        System.out.println(item);
    }
}
