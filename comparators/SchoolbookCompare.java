package Library.comparators;
import Library.items.Schoolbook;

import java.util.Comparator;

public class SchoolbookCompare implements Comparator<Schoolbook> {
    @Override
    public int compare(Schoolbook b1, Schoolbook b2) {
        int comp = b1.toString().compareTo(b2.toString());
        if (comp == 0){
            return Integer.compare(b1.getId(),b2.getId());
        }else {
            return comp;
        }
    }
}
