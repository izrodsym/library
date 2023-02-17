package Library.comparators;
import java.time.LocalDateTime;
import java.util.Comparator;

public class DataTimeCompare implements Comparator<LocalDateTime> {
    @Override
    public int compare(LocalDateTime b1, LocalDateTime b2) {
        if (b1.equals(b2)){
            return 1;
        }else{
            return b1.compareTo(b2);
        }
    }

}
