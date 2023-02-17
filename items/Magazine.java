package Library.items;

import java.time.LocalDate;

public class Magazine extends Item{
    public enum Category{FASHION, SCIENTIFIC, AUTOMOBILE, HOME, HAIR, GARDEN}

    private final String name;
    private LocalDate dateOfPublishing;
    private String publisher;
    private final Category category;
    private final int issueNumber;

    public Magazine(Category category, String name, int issueNumber){
        this.name = name;
        this.issueNumber = issueNumber;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String toString() {
        return name;
    }
}
