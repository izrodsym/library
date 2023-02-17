package Library.items;

import Library.Person;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Thread.sleep;

public abstract class Rentable extends Item implements Runnable{

    int priceForRent;
    int timeForRent;
    private double paymentAmount = priceForRent;
    private Person person = new Person();   // person that rent an item
    private LocalDateTime startRent;
    private final TreeMap<LocalDateTime, LocalDateTime> rentDates = new TreeMap<>();

    public Rentable(int price, int time){
        priceForRent = price;
        timeForRent = time;
    }

    @Override
    public void run() {
        startRent = LocalDateTime.now();
        try {
            int seconds = 0;
            paymentAmount = priceForRent;
            rentDates.put(startRent, null);
            sleep((long)timeForRent*10);
            while(true){
                seconds ++;
                paymentAmount = priceForRent + seconds*priceForRent*0.01;
                sleep(1000);
            }
        } catch (InterruptedException e) {
            rentDates.put(startRent, LocalDateTime.now());
        }
    }

    /**
     * View all records for the rented dates of the item
     */
    public void viewRentDates() {
        for (Map.Entry<LocalDateTime, LocalDateTime> entry : rentDates.entrySet()) {
            LocalDateTime value = entry.getValue();
            LocalDateTime key = entry.getKey();

            System.out.print("Taken: " + key.getDayOfMonth() + "." + key.getMonth() + "." + key.getYear() + " - " + key.getHour() + ":" + key.getMinute() + ":" + key.getSecond() + ". ");
            System.out.println("Returned: " + value.getDayOfMonth() + "." + value.getMonth() + "." + value.getYear() + " - " + value.getHour() + ":" + value.getMinute() + ":" + value.getSecond());
        }
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public int getPriceForRent() {
        return priceForRent;
    }

    public LocalDateTime getStartRent() {
        return startRent;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
