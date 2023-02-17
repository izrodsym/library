package Library.comparators;

import Library.items.Book;

import java.util.Comparator;

public class BookCompare implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        if (b1.getDateOfPublishing().isEqual(b2.getDateOfPublishing())) { //compare by date of publishing, if equal compare by ID
            return Integer.compare(b1.getId(), b2.getId());
        }else{
            return -1*b1.getDateOfPublishing().compareTo(b2.getDateOfPublishing());
        }
    }
}
