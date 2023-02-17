package Library;

import Library.items.Book;
import Library.items.Magazine;
import Library.items.Schoolbook;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Book b1 = new Book(Book.Genre.SCI_FI, "Dune");
        Book b2 = new Book(Book.Genre.SCI_FI, "Frankenstein");
        Book b3 = new Book(Book.Genre.SCI_FI, "Fahrenheit 451");
        Book b4 = new Book(Book.Genre.CRIMINAL, "God Father");
        Book b5 = new Book(Book.Genre.FANTASY, "Lord of the Rings");
        Book b6 = new Book(Book.Genre.SCI_FI, "Necromancer");
        Schoolbook sb1 = new Schoolbook(Schoolbook.Topic.BIOLOGY, "Biology");
        Schoolbook sb2 = new Schoolbook(Schoolbook.Topic.MATHEMATICS, "Mathematics");
        Schoolbook sb3 = new Schoolbook(Schoolbook.Topic.MATHEMATICS, "Mathematics");
        Magazine m1 = new Magazine(Magazine.Category.AUTOMOBILE, "Top Gear", 5);
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        Library l1 = new Library();

        ScheduledExecutorService revision = Executors.newSingleThreadScheduledExecutor();
        revision.scheduleAtFixedRate(l1, 100, 10000, TimeUnit.MILLISECONDS);    //schedule a revision every 31 days  TimeUnit.DAYS

        l1.addItem(b1);
        l1.addItem(b2);
        l1.addItem(b3);
        l1.addItem(b4);
        l1.addItem(b5);
        l1.addItem(b6);
        l1.addItem(sb1);
        l1.addItem(sb2);
        l1.addItem(sb3);
        l1.addItem(m1);

        l1.showItems();

        l1.rentItem(p1, b4);
        sleep(2000);
        l1.rentItem(p4, sb3);
        l1.rentItem(p2, b2);
        sleep(4000);
        l1.rentItem(p3, sb1);

        sleep(1000);

        l1.receiveRentItem(p1.returnItem());
        l1.receiveRentItem(p2.returnItem());

        l1.rentItem(p1, b4);
        sleep(2000);
        l1.rentItem(p2, b2);
        sleep(20000);

        l1.receiveRentItem(p3.returnItem());
        l1.receiveRentItem(p4.returnItem());


        l1.rentItem(p4, sb3);
        sleep(11000);
        l1.rentItem(p3, sb1);

        l1.receiveRentItem(p1.returnItem());
        l1.receiveRentItem(p2.returnItem());
        l1.receiveRentItem(p3.returnItem());
        l1.receiveRentItem(p4.returnItem());

        revision.shutdown();
    }
}
