package Library.comparators;

import Library.items.Magazine;

import java.util.Comparator;

public class MagazineCompare implements Comparator<Magazine> {
    @Override
    public int compare(Magazine m1, Magazine m2) {
        int name = m1.toString().compareTo(m2.toString());  // compare by name
        if (name == 0) {
            int issue = Integer.compare(m1.getIssueNumber(), m2.getIssueNumber());  // compare by issue number
            if (issue == 0) {
                return Integer.compare(m1.getId(), m2.getId()); // compare by ID
            }else {
                return issue;
            }
        } else {
            return name;
        }
    }
}
