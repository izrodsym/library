package Library;

import Library.comparators.*;
import Library.items.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Library implements Runnable {
    private final HashMap<Integer, Thread> rentItemThreads = new HashMap<>();
    private final ArrayList<Rentable> rentItems = new ArrayList<>();
    private final TreeMap<Book.Genre, TreeSet<Book>> books = new TreeMap<>();
    private final TreeMap<Magazine.Category, TreeSet<Magazine>> magazines = new TreeMap<>();
    private final TreeMap<Schoolbook.Topic, TreeSet<Schoolbook>> schoolbooks = new TreeMap<>();

    public void addItem(Item item) {
        if (item instanceof Book) {
            Book.Genre genre = ((Book) item).getGenre();
            TreeSet<Book> existingBooks = books.get(genre);
            if (existingBooks == null) {
                // if treeSet is empty, create new one with build in comparator
                existingBooks = new TreeSet<>(new BookCompare());   //compare by date of publishing, if equal compare by ID
            }
            existingBooks.add((Book) item);
            books.put(genre, existingBooks);
        } else if (item instanceof Schoolbook) {
            Schoolbook.Topic topic = ((Schoolbook) item).getTopic();
            TreeSet<Schoolbook> existingSchoolbooks = schoolbooks.get(topic);
            if (existingSchoolbooks == null) {
                // if treeSet is empty, create new one with build in comparator
                existingSchoolbooks = new TreeSet<>(new SchoolbookCompare());   //compare by date of publishing, if equal compare by ID
            }
            existingSchoolbooks.add((Schoolbook) item);
            schoolbooks.put(topic, existingSchoolbooks);
        } else {
            Magazine.Category category = ((Magazine) item).getCategory();
            TreeSet<Magazine> existingMagazines = magazines.get(category);
            if (existingMagazines == null) {
                // if treeSet is empty, create new one with build in comparator
                existingMagazines = new TreeSet<>(new MagazineCompare());   //compare by date of publishing, if equal compare by ID
            }
            existingMagazines.add((Magazine) item);
            magazines.put(category, existingMagazines);

        }


    }

    public void rentItem(Person person, Item item) {

        if (!checkForAvailableItem(item)) { // check if item is rented already
            System.out.println("Item is not available.");
            return;
        }

        if (item instanceof Rentable) { //check if item is Rentable
            if (person.rentItem(item)) { // check if person already has a book from library

                // create new thread for the rent book
                Thread thread = new Thread((Rentable) item);
                rentItemThreads.put(item.getId(), thread);
                rentItems.add((Rentable) item);
                ((Rentable) item).setPerson(person);
                thread.start();

                // remove book from library
                if (item instanceof Book) {
                    Book.Genre genre = ((Book) item).getGenre();
                    TreeSet<Book> existingBooks = books.get(genre);
                    existingBooks.remove(item);
                    books.put(genre, existingBooks);
                } else {
                    Schoolbook.Topic topic = ((Schoolbook) item).getTopic();
                    TreeSet<Schoolbook> existingSchoolbooks = schoolbooks.get(topic);
                    existingSchoolbooks.remove(item);
                    schoolbooks.put(topic, existingSchoolbooks);
                }
            } else {
                System.out.println(person + " already taken an item from library.");
            }
        } else {
            System.out.println("Magazines cannot be rented.");
        }
    }

    public void receiveRentItem(Item item) {
        if (item == null) {
            System.out.println("No item to return.");
            return;
        }
        Thread thread = rentItemThreads.get(item.getId());
        thread.interrupt();
        rentItemThreads.remove(item.getId());
        addItem(item);
        rentItems.remove((Rentable) item);

        double moneyToPay = ((Rentable) item).getPaymentAmount();
        System.out.printf("Returning: %s. Payment amount: %.2f lv.\n", item,  moneyToPay);
    }

    public void showItems() {
        System.out.println("------------- Books -------------");
        for (Map.Entry<Book.Genre, TreeSet<Book>> genres : books.entrySet()) {
            System.out.println(genres.getKey());
            for (Book book : genres.getValue()) {
                System.out.println("    " + book);
            }
        }
        System.out.println("\n------------- Schoolbooks -------------");
        for (Map.Entry<Schoolbook.Topic, TreeSet<Schoolbook>> topics : schoolbooks.entrySet()) {
            System.out.println(topics.getKey());
            for (Schoolbook schoolbook : topics.getValue()) {
                System.out.println("    " + schoolbook);
            }
        }
        System.out.println("\n------------- Magazines -------------");
        for (Map.Entry<Magazine.Category, TreeSet<Magazine>> category : magazines.entrySet()) {
            System.out.println(category.getKey());
            for (Magazine magazine : category.getValue()) {
                System.out.println("    " + magazine + " issue: " + magazine.getIssueNumber());
            }
        }
    }

    public void revision() {
        // count every item in the library
        int itemsCount = 0;
        for (Map.Entry<Book.Genre, TreeSet<Book>> genres : books.entrySet()) {
            itemsCount += genres.getValue().size();
        }
        for (Map.Entry<Schoolbook.Topic, TreeSet<Schoolbook>> topic : schoolbooks.entrySet()) {
            itemsCount += topic.getValue().size();
        }
        for (Map.Entry<Magazine.Category, TreeSet<Magazine>> category : magazines.entrySet()) {
            itemsCount += category.getValue().size();
        }
        System.out.println("Number of available items in the library is: " + itemsCount);

        // list all rented items in taken data order and total amount of rented items. Put it into a file
        TreeMap<LocalDateTime, Rentable> rentedItemsByData = new TreeMap<>(new DataTimeCompare());
        for (Rentable rent : rentItems) {
            rentedItemsByData.put(rent.getStartRent(), rent);
        }

        File file = new File("RentedItems.txt");
        try (FileWriter fr = new FileWriter(file)) {
            for (Map.Entry<LocalDateTime, Rentable> entry : rentedItemsByData.entrySet()) {
                Rentable value = entry.getValue();
                LocalDateTime key = entry.getKey();
                String string = value + ": " + key.getDayOfMonth() + "." + key.getMonth() + "." + key.getYear() + " - " + key.getHour() + ":" + key.getMinute() + ":" + key.getSecond() + "\n";
                fr.write(string);
            }
            fr.write("Number of rented items: " + rentedItemsByData.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //write to a file all overdue items, their name, the person who borrowed them, and the amount due, also the total amount due for all the items
        file = new File("AmountDue.txt");
        ArrayList<Rentable> rentedItemsByAmountDue = new ArrayList<>();
        for (Rentable rent : rentItems) {
            if (rent.getPaymentAmount() > rent.getPriceForRent()) {
                rentedItemsByAmountDue.add(rent);
            }
        }
        rentedItemsByAmountDue.sort((d1, d2) -> -1 * Double.compare(d1.getPaymentAmount(), d2.getPaymentAmount()));
        double totalAmountDue = 0;
        try (FileWriter fr = new FileWriter(file)) {
            for (Rentable rent : rentedItemsByAmountDue) {
                double amountDue = rent.getPaymentAmount() - rent.getPriceForRent();
                String print = String.format("%s %s %.2f\n", rent, rent.getPerson(), amountDue);
                fr.write(print);
                totalAmountDue += amountDue;
            }
            fr.write(String.format("Total amount due: %.2f", totalAmountDue));
            fr.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkForAvailableItem(Item item) {
        if (item instanceof Book) {
            Book.Genre genre = ((Book) item).getGenre();
            TreeSet<Book> existingBooks = books.get(genre);
            return existingBooks.contains(item);
        } else if (item instanceof Schoolbook) {
            Schoolbook.Topic topic = ((Schoolbook) item).getTopic();
            TreeSet<Schoolbook> existingSchoolbooks = schoolbooks.get(topic);
            return existingSchoolbooks.contains(item);
        } else if (item instanceof Magazine) {
            Magazine.Category category = ((Magazine) item).getCategory();
            TreeSet<Magazine> existingMagazine = magazines.get(category);
            return existingMagazine.contains(item);
        }
        return false;
    }

    @Override
    public void run() {
        revision();
    }
}
