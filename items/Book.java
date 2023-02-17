package Library.items;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Book extends Rentable{
    public enum Genre {SCI_FI, HUMOR, FANTASY, HORROR, THRILLER, CRIMINAL, ROMANTIC, DRAMA}
    private static final int PRICE_FOR_RENT = 2;
    private static final int TIME_FOR_RENT = 300;

    private final String name;
    private String author;
    private final LocalDate dateOfPublishing;
    private String publisher;
    private final Genre genre;

    public Book(Genre genre, String name) {
        super(PRICE_FOR_RENT, TIME_FOR_RENT);

        // generate random date
        LocalDate start = LocalDate.of(1470, Month.JANUARY, 1); // first possible date
        long days = ChronoUnit.DAYS.between(start, LocalDate.now()); // days between first day and now
        dateOfPublishing = start.plusDays(new Random().nextInt((int) days + 1));
        this.genre = genre;
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }


    @Override
    public String toString() {
        return this.name;
    }

}
