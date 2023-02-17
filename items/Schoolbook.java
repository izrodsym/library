package Library.items;

public class Schoolbook extends Rentable{
    public enum Topic{MATHEMATICS, BIOLOGY, CHEMISTRY, PHYSICS, MUSIC, HISTORY}
    private static final int PRICE_FOR_RENT = 3;
    private static final int TIME_FOR_RENT = 150;

    private final String name;
    private String author;
    private String publisher;
    private final Topic topic;

    public Schoolbook(Topic topic, String name){
        super(PRICE_FOR_RENT, TIME_FOR_RENT);

        this.topic = topic;
        this.name = name;
    }


    public Topic getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return name;
    }
}
